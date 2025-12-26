package br.unitins.topicos1.ewine.service.assembler;

import br.unitins.topicos1.ewine.infrastructure.persistence.ClienteRepository;
import br.unitins.topicos1.ewine.infrastructure.persistence.FormaPagamentoRepository;
import br.unitins.topicos1.ewine.infrastructure.persistence.ProdutoRepository;
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

  @Inject ClienteAssembler clienteAssembler;
  @Inject FormaPagamentoAssembler formaPagamentoAssembler;

    public Pedido toEntity(String login, PedidoInput input) {
        List<ItemPedido> itens = new ArrayList<>();

        for (ItemPedidoInput itemInput :  input.itens()) {
            Produto produto = produtoRepository. findById(itemInput.produtoId());

            if (produto == null) {
                throw new jakarta.ws.rs.NotFoundException("Produto com ID " + itemInput.produtoId() + " não encontrado");
            }

            if (!produto.isAtivo()) {
                throw new IllegalArgumentException("Produto " + produto.getNome() + " não está disponível");
            }

            ItemPedido itemPedido = new ItemPedido(produto, itemInput.quantidade());
            itens.add(itemPedido);
        }

        FormaPagamento formaPagamento = formaPagamentoAssembler.toEntity(input.formaPagamento());
        Cliente cliente = clienteRepository.findByUsuarioLogin(login);

        if (cliente == null) {
            throw new jakarta.ws. rs.NotFoundException("Cliente não encontrado");
        }

        Endereco endereco = cliente.getEndereco(input. endereco().id());

        return new Pedido(input.parcelas(), formaPagamento, cliente, endereco, itens);
    }

    public PedidoResponse toResponse(Pedido pedido) {
        if (pedido == null) {
            return null;
        }

        return new PedidoResponse(
                pedido.getId(),  // ← ADICIONE ESTA LINHA
                pedido.getDataCriacao().toString(),
                pedido.getStatus(),
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
