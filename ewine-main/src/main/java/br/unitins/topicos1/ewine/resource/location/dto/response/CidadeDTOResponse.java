package br.unitins.topicos1.ewine.resource.location.dto.response;
import br.unitins.topicos1.ewine.model.location.Cidade;

public record CidadeDTOResponse(
    Long id,
    String nome,
    Long idEstado
) {
    public static CidadeDTOResponse valueOf (Cidade cidade) {
        return new CidadeDTOResponse(
            cidade.getId(),
            cidade.getNome(),
            cidade.getEstado().getId()
        );
    }
}