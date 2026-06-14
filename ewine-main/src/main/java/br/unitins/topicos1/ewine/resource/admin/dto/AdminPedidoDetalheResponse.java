package br.unitins.topicos1.ewine.resource.admin.dto;

import br.unitins.topicos1.ewine.model.pedido.PedidoStatus;
import br.unitins.topicos1.ewine.model.pedido.pagamento.PagamentoStatus;
import java.util.List;

public record AdminPedidoDetalheResponse(
    Long id,
    PedidoStatus status,
    String cliente,
    String email,
    String telefone,
    AdminEnderecoResponse enderecoEntrega,
    List<AdminPedidoItemResponse> itens,
    double subtotal,
    double desconto,
    double total,
    String formaPagamento,
    PagamentoStatus statusPagamento,
    String dataCriacao,
    List<AdminPedidoLinhaTempoResponse> linhaTempo) {}
