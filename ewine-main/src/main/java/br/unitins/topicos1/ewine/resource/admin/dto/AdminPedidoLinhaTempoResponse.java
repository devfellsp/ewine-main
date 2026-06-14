package br.unitins.topicos1.ewine.resource.admin.dto;

import br.unitins.topicos1.ewine.model.pedido.PedidoStatus;

public record AdminPedidoLinhaTempoResponse(PedidoStatus status, String data, String descricao) {}
