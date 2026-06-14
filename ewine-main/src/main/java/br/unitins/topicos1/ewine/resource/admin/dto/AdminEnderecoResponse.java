package br.unitins.topicos1.ewine.resource.admin.dto;

public record AdminEnderecoResponse(
    Long id,
    String cep,
    String logradouro,
    int numero,
    String complemento,
    String cidade,
    String estado) {}
