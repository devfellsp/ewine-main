package br.unitins.topicos1.ewine.resource.admin.dto;

import java.util.List;

public record AdminClienteDetalheResponse(
    Long id,
    String nome,
    String email,
    String telefone,
    String cpf,
    List<AdminEnderecoResponse> enderecos,
    long quantidadePedidos,
    double totalGasto,
    String ultimoPedido,
    String status,
    List<AdminPedidoResumoResponse> pedidosRecentes) {}
