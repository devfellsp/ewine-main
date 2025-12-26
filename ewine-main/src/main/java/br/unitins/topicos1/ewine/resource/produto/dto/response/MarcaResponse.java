package br.unitins.topicos1.ewine.resource.produto.dto.response;

public record MarcaResponse(
    Long id, String nome, String paisDeOrigem, String anofundacao, String classificacao) {}
