package br.unitins.topicos1.ewine.resource.cupom.dto.response;

public record CupomValidacaoResponse(
    String codigo, boolean valido, double percentualDesconto, double valorDesconto, double valorFinal) {}
