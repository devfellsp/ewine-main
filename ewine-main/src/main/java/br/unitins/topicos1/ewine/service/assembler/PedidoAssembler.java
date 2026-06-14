package br.unitins.topicos1.ewine.service.assembler;

import br.unitins.topicos1.ewine.infrastructure.persistence.ClienteRepository;
import br.unitins.topicos1.ewine.infrastructure.persistence.CupomRepository;
import br.unitins.topicos1.ewine.infrastructure.persistence.ProdutoRepository;
import br.unitins.topicos1.ewine.model.cupom.Cupom;
import br.unitins.topicos1.ewine.model.pedido.ItemPedido;
import br.unitins.topicos1.ewine.model.pedido.Pedido;
import br.unitins.topicos1.ewine.model.pedido.pagamento.FormaPagamento;
import br.unitins.topicos1.ewine.model.pedido.pagamento.Pagamento;
import br.unitins.topicos1.ewine.model.produto.Produto;
import br.unitins.topicos1.ewine.model.usuario.cliente.Cliente;
import br.unitins.topicos1.ewine.model.usuario.cliente.Endereco;
import br.unitins.topicos1.ewine.resource.pedido.dto.input.ItemPedidoInput;
import br.unitins.topicos1.ewine.resource.pedido.dto.input.PedidoInput;
import br.unitins.topicos1.ewine.resource.pedido.dto.response.FormaPagamentoResponse;
import br.unitins.topicos1.ewine.resource.pedido.dto.response.ItemPedidoResponse;
import br.unitins.topicos1.ewine.resource.pedido.dto.response.PagamentoResponse;
import br.unitins.topicos1.ewine.resource.pedido.dto.response.PedidoResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class PedidoAssembler {

  @Inject ProdutoRepository produtoRepository;
  @Inject ClienteRepository clienteRepository;
  @Inject CupomRepository cupomRepository;
  @Inject ClienteAssembler clienteAssembler;
  @Inject FormaPagamentoAssembler formaPagamentoAssembler;

  public Pedido toEntity(String login, PedidoInput input) {
    List<ItemPedido> itens = new ArrayList<>();

    for (ItemPedidoInput itemInput : input.itens()) {
      Produto produto = produtoRepository.findById(itemInput.produtoId());

      if (!produto.isAtivo()) {
        throw new IllegalArgumentException("Produto " + produto.getNome() + " nao esta disponivel");
      }

      itens.add(new ItemPedido(produto, itemInput.quantidade()));
    }

    FormaPagamento formaPagamento = formaPagamentoAssembler.toEntity(input.formaPagamento());
    Cliente cliente = clienteRepository.findByUsuarioLogin(login);
    Endereco endereco = cliente.getEndereco(input.endereco().id());

    Cupom cupom = null;
    if (input.codigoCupom() != null && !input.codigoCupom().isBlank()) {
      cupom = cupomRepository.findByCodigo(input.codigoCupom());
    }

    Pedido pedido = new Pedido(input.parcelas(), formaPagamento, cliente, endereco, itens, cupom);
    if (cupom != null) {
      cupom.registrarUso();
    }

    return pedido;
  }

  public PedidoResponse toResponse(Pedido pedido) {
    if (pedido == null) {
      return null;
    }

    return new PedidoResponse(
        pedido.getId(),
        pedido.getDataCriacao().toString(),
        pedido.getStatus(),
        pedido.getSubtotal(),
        pedido.getDesconto(),
        pedido.getTotal(),
        pedido.getCupom() == null ? null : pedido.getCupom().getCodigo(),
        clienteAssembler.toResponse(pedido.getCliente()),
        pedido.getPagamento() == null ? null : toResponse(pedido.getPagamento()),
        toItemPedidoResponse(pedido.getItens()));
  }

  public List<PedidoResponse> toResponse(List<Pedido> pedidos) {
    return pedidos.stream().map(this::toResponse).toList();
  }

  private PagamentoResponse toResponse(Pagamento pagamento) {
    FormaPagamento formaPagamento = pagamento.getFormaPagamento();

    return new PagamentoResponse(
        pagamento.getValorTotal(),
        pagamento.getParcelas(),
        pagamento.getStatus(),
        pagamento.getTransacaoExternaId(),
        formaPagamento == null
            ? null
            : new FormaPagamentoResponse(
                formaPagamento.getId(),
                formaPagamento.getNome(),
                formaPagamento.isPermiteParcelamento(),
                formaPagamento.isAtivo()));
  }

  private List<ItemPedidoResponse> toItemPedidoResponse(List<ItemPedido> itens) {
    return itens.stream()
        .map(
            item ->
                new ItemPedidoResponse(
                    item.getQuantidade(), item.getPrecoUnitario(), item.getProduto().getNome()))
        .toList();
  }
}
