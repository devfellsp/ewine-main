package br.unitins.topicos1.ewine.resource.admin.dto;

public record AdminPedidoItemResponse(
    Long produtoId,
    String nomeProduto,
    int quantidade,
    double precoUnitario,
    double subtotal) {}
