package br.unitins.topicos1.ewine.service.assembler;

import br.unitins.topicos1.ewine.infrastructure.persistence.PaisRepository;
import br.unitins.topicos1.ewine.model.produto.vinho.Pais;
import br.unitins.topicos1.ewine.resource.produto.dto.input.PaisInput;
import br.unitins.topicos1.ewine.resource.shared.dto.input.IdInput;
import br.unitins.topicos1.ewine.resource.produto.dto.response.PaisResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class PaisAssembler {

  @Inject PaisRepository paisRepository;

  public Pais toEntity(IdInput dto) {
    if (dto == null || dto.id() == null) {
      return null;
    }

    return paisRepository.findById(dto.id());
  }

  public Pais toEntity(PaisInput input) {
    if (input == null) {
      return null;
    }

    return new Pais(input.nome(), input.sigla());
  }

  public PaisResponse toResponse(Pais pais) {
    if (pais == null) {
      return null;
    }

    return new PaisResponse(pais.getId(), pais.getNome());
  }

  public List<PaisResponse> toResponse(List<Pais> paises) {
    if (paises == null || paises.isEmpty()) {
      return new ArrayList<>();
    }

    return paises.stream().map(this::toResponse).toList();
  }
}
