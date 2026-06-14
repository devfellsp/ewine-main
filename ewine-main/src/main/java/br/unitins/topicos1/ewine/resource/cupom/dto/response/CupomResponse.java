package br.unitins.topicos1.ewine.resource.cupom.dto.response;

import java.time.LocalDate;

public record CupomResponse(
    Long id,
    String codigo,
    double percentualDesconto,
    LocalDate dataValidade,
    Integer quantidadeMaximaUsos,
    int quantidadeUsada,
    boolean ativo,
    boolean valido) {}
