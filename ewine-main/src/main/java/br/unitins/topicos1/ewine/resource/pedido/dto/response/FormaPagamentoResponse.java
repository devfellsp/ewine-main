package br.unitins.topicos1.ewine.resource.pedido.dto.response;

public record FormaPagamentoResponse(
    Long id, String nome, boolean permiteParcelamento, boolean ativo) {}
