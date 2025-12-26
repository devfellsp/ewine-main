package br.unitins.topicos1.ewine.resource.location.dto.response;

import br.unitins.topicos1.ewine.model.location.Estado;

public record EstadoDTOResponse(
    Long id,
    String nome,
    String sigla
) {
    public static EstadoDTOResponse valueOf(Estado estado) {
        return new EstadoDTOResponse(
            estado.getId(),
            estado.getNome(),
            estado.getSigla()
        );
    }
}
