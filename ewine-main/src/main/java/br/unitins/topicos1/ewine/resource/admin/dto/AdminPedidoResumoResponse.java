package br.unitins.topicos1.ewine.resource.admin.dto;

import br.unitins.topicos1.ewine.model.pedido.PedidoStatus;

public record AdminPedidoResumoResponse(
    Long id, String cliente, PedidoStatus status, double total, String data) {}
