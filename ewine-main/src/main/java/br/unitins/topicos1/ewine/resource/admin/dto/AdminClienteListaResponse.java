package br.unitins.topicos1.ewine.resource.admin.dto;

public record AdminClienteListaResponse(
    Long id,
    String nome,
    String email,
    long quantidadePedidos,
    double totalGasto,
    String ultimoPedido,
    String status) {}
