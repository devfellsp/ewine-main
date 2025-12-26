package br.unitins.topicos1.ewine.resource.location.dto.input;


public record EstadoDTO(
    String nome,
    String sigla,
    Long idPais,
    Long idRegiao
) {}