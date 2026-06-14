package br.unitins.topicos1.ewine.resource.admin.dto;

import java.util.List;

public record AdminDashboardResponse(
    long totalProdutos,
    long estoqueTotal,
    double precoMedio,
    long cuponsAtivos,
    long pedidosPendentes,
    double vendasDoMes,
    double ticketMedio,
    long clientesCadastrados,
    List<AdminPedidoResumoResponse> pedidosRecentes,
    AdminResumoVendasResponse resumoVendas) {}
