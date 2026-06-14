package br.unitins.topicos1.ewine.resource.admin.dto;

import br.unitins.topicos1.ewine.model.pedido.PedidoStatus;

public record AdminVendaResponse(
    String data,
    Long pedidoId,
    String cliente,
    double total,
    PedidoStatus status,
    String formaPagamento) {}
