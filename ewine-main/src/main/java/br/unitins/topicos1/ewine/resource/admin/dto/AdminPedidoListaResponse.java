package br.unitins.topicos1.ewine.resource.admin.dto;

import br.unitins.topicos1.ewine.model.pedido.PedidoStatus;
import br.unitins.topicos1.ewine.model.pedido.pagamento.PagamentoStatus;

public record AdminPedidoListaResponse(
    Long id,
    String cliente,
    String email,
    String data,
    PedidoStatus status,
    double total,
    String formaPagamento,
    PagamentoStatus statusPagamento,
    String entrega,
    String enderecoResumo) {}
