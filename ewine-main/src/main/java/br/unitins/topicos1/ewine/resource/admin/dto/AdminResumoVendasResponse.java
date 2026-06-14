package br.unitins.topicos1.ewine.resource.admin.dto;

public record AdminResumoVendasResponse(
    double receitaTotal,
    String produtoMaisVendido,
    long cuponsUtilizados,
    long pedidosPagos) {}
