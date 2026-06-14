package br.unitins.topicos1.ewine.resource.admin.dto;

public record AdminVendasResumoResponse(
    double receitaTotal,
    double vendasDoMes,
    double ticketMedio,
    long produtosVendidos,
    String vinhoMaisVendido,
    double maiorVenda,
    long cuponsUtilizados,
    long quantidadePedidosPagos) {}
