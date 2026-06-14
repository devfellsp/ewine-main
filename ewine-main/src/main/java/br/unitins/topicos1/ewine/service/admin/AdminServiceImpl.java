package br.unitins.topicos1.ewine.service.admin;

import br.unitins.topicos1.ewine.infrastructure.persistence.ClienteRepository;
import br.unitins.topicos1.ewine.infrastructure.persistence.CupomRepository;
import br.unitins.topicos1.ewine.infrastructure.persistence.PedidoRepository;
import br.unitins.topicos1.ewine.infrastructure.persistence.ProdutoRepository;
import br.unitins.topicos1.ewine.model.pedido.ItemPedido;
import br.unitins.topicos1.ewine.model.pedido.Pedido;
import br.unitins.topicos1.ewine.model.pedido.PedidoHistorico;
import br.unitins.topicos1.ewine.model.pedido.PedidoStatus;
import br.unitins.topicos1.ewine.model.pedido.pagamento.Pagamento;
import br.unitins.topicos1.ewine.model.pedido.pagamento.PagamentoStatus;
import br.unitins.topicos1.ewine.model.produto.Produto;
import br.unitins.topicos1.ewine.model.usuario.cliente.Cliente;
import br.unitins.topicos1.ewine.model.usuario.cliente.Endereco;
import br.unitins.topicos1.ewine.resource.admin.dto.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@ApplicationScoped
public class AdminServiceImpl implements AdminService {

  @Inject PedidoRepository pedidoRepository;
  @Inject ProdutoRepository produtoRepository;
  @Inject ClienteRepository clienteRepository;
  @Inject CupomRepository cupomRepository;

  @Override
  public AdminDashboardResponse dashboard() {
    List<Produto> produtos = produtoRepository.listAll();
    List<Pedido> pedidos = pedidoRepository.listAll();
    List<Cliente> clientes = clienteRepository.listAll();

    long totalProdutos = produtos.size();
    long estoqueTotal = produtos.stream().mapToLong(Produto::getQuantidadeEstoque).sum();
    double precoMedio = media(produtos.stream().mapToDouble(Produto::getPreco).sum(), totalProdutos);
    long cuponsAtivos = cupomRepository.listAll().stream().filter(cupom -> cupom.isAtivo()).count();
    long pedidosPendentes =
        pedidos.stream().filter(pedido -> pedido.getStatus() == PedidoStatus.AGUARDANDO_PAGAMENTO).count();
    double vendasDoMes = pedidosPagosDoMes(pedidos).stream().mapToDouble(Pedido::getTotal).sum();
    long pedidosPagos = pedidos.stream().filter(this::isPedidoPago).count();
    double receitaTotal = pedidos.stream().filter(this::isPedidoPago).mapToDouble(Pedido::getTotal).sum();

    List<AdminPedidoResumoResponse> pedidosRecentes =
        pedidos.stream()
            .sorted(Comparator.comparing(Pedido::getDataCriacao).reversed())
            .limit(5)
            .map(this::toPedidoResumo)
            .toList();

    return new AdminDashboardResponse(
        totalProdutos,
        estoqueTotal,
        precoMedio,
        cuponsAtivos,
        pedidosPendentes,
        vendasDoMes,
        media(receitaTotal, pedidosPagos),
        clientes.size(),
        pedidosRecentes,
        new AdminResumoVendasResponse(
            receitaTotal, produtoMaisVendido(pedidos), cuponsUtilizados(pedidos), pedidosPagos));
  }

  @Override
  public AdminPagedResponse<AdminPedidoListaResponse> listarPedidos(
      String busca, String status, String dataInicio, String dataFim, int page, int size) {
    PedidoStatus statusFiltro = parseStatusOpcional(status);
    LocalDate inicio = parseDataOpcional(dataInicio);
    LocalDate fim = parseDataOpcional(dataFim);

    List<Pedido> filtrados =
        pedidoRepository.listAll().stream()
            .filter(pedido -> statusFiltro == null || pedido.getStatus() == statusFiltro)
            .filter(pedido -> dentroPeriodo(pedido.getDataCriacao(), inicio, fim))
            .filter(pedido -> filtraPedidoPorBusca(pedido, busca))
            .sorted(Comparator.comparing(Pedido::getDataCriacao).reversed())
            .toList();

    return paginar(filtrados.stream().map(this::toPedidoLista).toList(), filtrados.size(), page, size);
  }

  @Override
  public AdminPedidoDetalheResponse buscarPedido(Long id) {
    return toPedidoDetalhe(findPedido(id));
  }

  @Override
  @Transactional
  public AdminPedidoDetalheResponse atualizarStatusPedido(Long id, AdminAtualizarStatusPedidoInput input) {
    Pedido pedido = findPedido(id);
    pedido.alterarStatusAdministrativo(parseStatusObrigatorio(input.status()));
    return toPedidoDetalhe(pedido);
  }

  @Override
  @Transactional
  public AdminPedidoDetalheResponse cancelarPedido(Long id) {
    Pedido pedido = findPedido(id);
    if (pedido.getStatus() == PedidoStatus.ENTREGUE) {
      throw new IllegalArgumentException("Pedido entregue nao pode ser cancelado");
    }
    pedido.cancelar();
    return toPedidoDetalhe(pedido);
  }

  @Override
  public AdminPagedResponse<AdminVendaResponse> listarVendas(
      String dataInicio, String dataFim, String statusPagamento, int page, int size) {
    LocalDate inicio = parseDataOpcional(dataInicio);
    LocalDate fim = parseDataOpcional(dataFim);
    PagamentoStatus pagamentoStatus = parsePagamentoStatusOpcional(statusPagamento);

    List<Pedido> filtrados =
        pedidoRepository.listAll().stream()
            .filter(pedido -> pedido.getPagamento() != null)
            .filter(pedido -> pagamentoStatus == null || pedido.getPagamento().getStatus() == pagamentoStatus)
            .filter(pedido -> dentroPeriodo(pedido.getDataCriacao(), inicio, fim))
            .sorted(Comparator.comparing(Pedido::getDataCriacao).reversed())
            .toList();

    return paginar(filtrados.stream().map(this::toVenda).toList(), filtrados.size(), page, size);
  }

  @Override
  public AdminVendasResumoResponse resumoVendas() {
    List<Pedido> pagos = pedidoRepository.listAll().stream().filter(this::isPedidoPago).toList();
    double receitaTotal = pagos.stream().mapToDouble(Pedido::getTotal).sum();

    return new AdminVendasResumoResponse(
        receitaTotal,
        pedidosPagosDoMes(pagos).stream().mapToDouble(Pedido::getTotal).sum(),
        media(receitaTotal, pagos.size()),
        pagos.stream().flatMap(pedido -> pedido.getItens().stream()).mapToLong(ItemPedido::getQuantidade).sum(),
        produtoMaisVendido(pagos),
        pagos.stream().mapToDouble(Pedido::getTotal).max().orElse(0),
        cuponsUtilizados(pagos),
        pagos.size());
  }

  @Override
  public AdminPagedResponse<AdminClienteListaResponse> listarClientes(
      String busca, String status, int page, int size) {
    List<Pedido> pedidos = pedidoRepository.listAll();
    List<Cliente> filtrados =
        clienteRepository.listAll().stream()
            .filter(cliente -> filtraClientePorBusca(cliente, busca))
            .filter(cliente -> filtraClientePorStatus(cliente, status))
            .sorted(Comparator.comparing(cliente -> cliente.getUsuario().getNome()))
            .toList();

    return paginar(
        filtrados.stream().map(cliente -> toClienteLista(cliente, pedidos)).toList(),
        filtrados.size(),
        page,
        size);
  }

  @Override
  public AdminClienteDetalheResponse buscarCliente(Long id) {
    Cliente cliente =
        clienteRepository
            .findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Cliente nao encontrado"));
    List<Pedido> pedidos = pedidosDoCliente(cliente, pedidoRepository.listAll());

    return new AdminClienteDetalheResponse(
        cliente.getId(),
        cliente.getUsuario().getNome(),
        cliente.getEmail(),
        null,
        cliente.getCpf(),
        cliente.getEnderecos() == null ? List.of() : cliente.getEnderecos().stream().map(this::toEndereco).toList(),
        pedidos.size(),
        totalGasto(pedidos),
        ultimoPedido(pedidos),
        cliente.getUsuario().isAtivo() ? "ATIVO" : "INATIVO",
        pedidos.stream()
            .sorted(Comparator.comparing(Pedido::getDataCriacao).reversed())
            .limit(5)
            .map(this::toPedidoResumo)
            .toList());
  }

  private Pedido findPedido(Long id) {
    return pedidoRepository
        .findByIdOptional(id)
        .orElseThrow(() -> new NotFoundException("Pedido nao encontrado"));
  }

  private AdminPedidoListaResponse toPedidoLista(Pedido pedido) {
    Pagamento pagamento = pedido.getPagamento();
    return new AdminPedidoListaResponse(
        pedido.getId(),
        pedido.getCliente().getUsuario().getNome(),
        pedido.getCliente().getEmail(),
        pedido.getDataCriacao().toString(),
        pedido.getStatus(),
        pedido.getTotal(),
        formaPagamento(pagamento),
        pagamento == null ? null : pagamento.getStatus(),
        entrega(pedido.getStatus()),
        enderecoResumo(pedido.getEnderecoEntrega()));
  }

  private AdminPedidoDetalheResponse toPedidoDetalhe(Pedido pedido) {
    Pagamento pagamento = pedido.getPagamento();
    List<AdminPedidoLinhaTempoResponse> linhaTempo =
        pedido.getHistorico().isEmpty()
            ? List.of(new AdminPedidoLinhaTempoResponse(pedido.getStatus(), pedido.getDataCriacao().toString(), "Pedido criado"))
            : pedido.getHistorico().stream()
                .sorted(Comparator.comparing(PedidoHistorico::getData))
                .map(historico -> new AdminPedidoLinhaTempoResponse(
                    historico.getStatus(), historico.getData().toString(), historico.getDescricao()))
                .toList();

    return new AdminPedidoDetalheResponse(
        pedido.getId(),
        pedido.getStatus(),
        pedido.getCliente().getUsuario().getNome(),
        pedido.getCliente().getEmail(),
        null,
        toEndereco(pedido.getEnderecoEntrega()),
        pedido.getItens().stream().map(this::toItem).toList(),
        pedido.getSubtotal(),
        pedido.getDesconto(),
        pedido.getTotal(),
        formaPagamento(pagamento),
        pagamento == null ? null : pagamento.getStatus(),
        pedido.getDataCriacao().toString(),
        linhaTempo);
  }

  private AdminPedidoItemResponse toItem(ItemPedido item) {
    return new AdminPedidoItemResponse(
        item.getProduto().getId(),
        item.getNomeProduto(),
        item.getQuantidade(),
        item.getPrecoUnitario(),
        item.getSubtotal());
  }

  private AdminEnderecoResponse toEndereco(Endereco endereco) {
    if (endereco == null) {
      return null;
    }
    String cidade = endereco.getCidade() == null ? null : endereco.getCidade().getNome();
    String estado =
        endereco.getCidade() == null || endereco.getCidade().getEstado() == null
            ? null
            : endereco.getCidade().getEstado().getSigla();

    return new AdminEnderecoResponse(
        endereco.getId(),
        endereco.getCEP(),
        endereco.getLogradouro(),
        endereco.getNumero(),
        endereco.getComplemento(),
        cidade,
        estado);
  }

  private AdminVendaResponse toVenda(Pedido pedido) {
    return new AdminVendaResponse(
        pedido.getDataCriacao().toString(),
        pedido.getId(),
        pedido.getCliente().getUsuario().getNome(),
        pedido.getTotal(),
        pedido.getStatus(),
        formaPagamento(pedido.getPagamento()));
  }

  private AdminClienteListaResponse toClienteLista(Cliente cliente, List<Pedido> pedidosTodos) {
    List<Pedido> pedidos = pedidosDoCliente(cliente, pedidosTodos);
    return new AdminClienteListaResponse(
        cliente.getId(),
        cliente.getUsuario().getNome(),
        cliente.getEmail(),
        pedidos.size(),
        totalGasto(pedidos),
        ultimoPedido(pedidos),
        cliente.getUsuario().isAtivo() ? "ATIVO" : "INATIVO");
  }

  private AdminPedidoResumoResponse toPedidoResumo(Pedido pedido) {
    return new AdminPedidoResumoResponse(
        pedido.getId(),
        pedido.getCliente().getUsuario().getNome(),
        pedido.getStatus(),
        pedido.getTotal(),
        pedido.getDataCriacao().toString());
  }

  private boolean filtraPedidoPorBusca(Pedido pedido, String busca) {
    if (busca == null || busca.isBlank()) {
      return true;
    }
    String termo = busca.toLowerCase(Locale.ROOT).trim();
    return pedido.getId().toString().contains(termo)
        || contem(pedido.getCliente().getUsuario().getNome(), termo)
        || contem(pedido.getCliente().getEmail(), termo);
  }

  private boolean filtraClientePorBusca(Cliente cliente, String busca) {
    if (busca == null || busca.isBlank()) {
      return true;
    }
    String termo = busca.toLowerCase(Locale.ROOT).trim();
    return contem(cliente.getUsuario().getNome(), termo)
        || contem(cliente.getEmail(), termo)
        || contem(cliente.getCpf(), termo);
  }

  private boolean filtraClientePorStatus(Cliente cliente, String status) {
    if (status == null || status.isBlank()) {
      return true;
    }
    boolean ativo = cliente.getUsuario().isAtivo();
    return status.equalsIgnoreCase(ativo ? "ATIVO" : "INATIVO");
  }

  private boolean contem(String valor, String termo) {
    return valor != null && valor.toLowerCase(Locale.ROOT).contains(termo);
  }

  private List<Pedido> pedidosDoCliente(Cliente cliente, List<Pedido> pedidos) {
    return pedidos.stream()
        .filter(pedido -> Objects.equals(pedido.getCliente().getId(), cliente.getId()))
        .sorted(Comparator.comparing(Pedido::getDataCriacao).reversed())
        .toList();
  }

  private double totalGasto(List<Pedido> pedidos) {
    return pedidos.stream().filter(this::isPedidoPago).mapToDouble(Pedido::getTotal).sum();
  }

  private String ultimoPedido(List<Pedido> pedidos) {
    return pedidos.stream().map(Pedido::getDataCriacao).max(LocalDateTime::compareTo).map(LocalDateTime::toString).orElse(null);
  }

  private boolean isPedidoPago(Pedido pedido) {
    return pedido.getPagamento() != null && pedido.getPagamento().getStatus() == PagamentoStatus.CONFIRMADO;
  }

  private List<Pedido> pedidosPagosDoMes(List<Pedido> pedidos) {
    YearMonth mesAtual = YearMonth.now();
    return pedidos.stream()
        .filter(this::isPedidoPago)
        .filter(pedido -> YearMonth.from(pedido.getDataCriacao()).equals(mesAtual))
        .toList();
  }

  private String produtoMaisVendido(List<Pedido> pedidos) {
    return pedidos.stream()
        .flatMap(pedido -> pedido.getItens().stream())
        .collect(Collectors.groupingBy(ItemPedido::getNomeProduto, Collectors.summingInt(ItemPedido::getQuantidade)))
        .entrySet()
        .stream()
        .max(Map.Entry.comparingByValue())
        .map(Map.Entry::getKey)
        .orElse(null);
  }

  private long cuponsUtilizados(List<Pedido> pedidos) {
    return pedidos.stream().filter(pedido -> pedido.getCupom() != null).count();
  }

  private String formaPagamento(Pagamento pagamento) {
    return pagamento == null || pagamento.getFormaPagamento() == null ? null : pagamento.getFormaPagamento().getNome();
  }

  private String enderecoResumo(Endereco endereco) {
    if (endereco == null) {
      return null;
    }
    return endereco.getLogradouro() + ", " + endereco.getNumero();
  }

  private String entrega(PedidoStatus status) {
    return switch (status) {
      case AGUARDANDO_PAGAMENTO, PAGO, EM_SEPARACAO -> "PENDENTE";
      case ENVIADO, SAIU_PARA_ENTREGA -> "EM_TRANSITO";
      case ENTREGUE -> "ENTREGUE";
      case CANCELADO -> "CANCELADO";
    };
  }

  private boolean dentroPeriodo(LocalDateTime data, LocalDate inicio, LocalDate fim) {
    LocalDate dia = data.toLocalDate();
    return (inicio == null || !dia.isBefore(inicio)) && (fim == null || !dia.isAfter(fim));
  }

  private LocalDate parseDataOpcional(String valor) {
    return valor == null || valor.isBlank() ? null : LocalDate.parse(valor);
  }

  private PedidoStatus parseStatusOpcional(String valor) {
    return valor == null || valor.isBlank() ? null : parseStatusObrigatorio(valor);
  }

  private PedidoStatus parseStatusObrigatorio(String valor) {
    try {
      return PedidoStatus.valueOf(valor.trim().toUpperCase(Locale.ROOT));
    } catch (RuntimeException e) {
      throw new IllegalArgumentException("Status de pedido invalido: " + valor);
    }
  }

  private PagamentoStatus parsePagamentoStatusOpcional(String valor) {
    if (valor == null || valor.isBlank()) {
      return null;
    }
    try {
      return PagamentoStatus.valueOf(valor.trim().toUpperCase(Locale.ROOT));
    } catch (RuntimeException e) {
      throw new IllegalArgumentException("Status de pagamento invalido: " + valor);
    }
  }

  private <T> AdminPagedResponse<T> paginar(List<T> itens, long total, int page, int size) {
    int pagina = Math.max(0, page);
    int tamanho = size <= 0 ? 10 : size;
    int inicio = Math.min(pagina * tamanho, itens.size());
    int fim = Math.min(inicio + tamanho, itens.size());
    return new AdminPagedResponse<>(itens.subList(inicio, fim), total, pagina, tamanho);
  }

  private double media(double total, long quantidade) {
    return quantidade == 0 ? 0 : total / quantidade;
  }
}
