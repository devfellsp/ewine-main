package br.unitins.topicos1.ewine.service.assembler;

import br.unitins.topicos1.ewine.infrastructure.persistence.EstiloRepository;
import br.unitins.topicos1.ewine.model.produto.vinho.Estilo;
import br.unitins.topicos1.ewine.resource.produto.dto.input.EstiloInput;
import br.unitins.topicos1.ewine.resource.shared.dto.input.IdInput;
import br.unitins.topicos1.ewine.resource.produto.dto.response.EstiloResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class EstiloAssembler {

  @Inject EstiloRepository repository;

  public Estilo toEntity(IdInput dto) {
    if (dto == null || dto.id() == null) {
      return null;
    }

    return repository.findById(dto.id());
  }
  public Estilo toEntity(EstiloInput input) {
      if (input == null) {
          return null;
      }
      return new  Estilo(input.nome());
  }

  public EstiloResponse toResponse(Estilo estilo) {
    if (estilo == null) {
      return null;
    }

    return new EstiloResponse(estilo.getId(), estilo.getNome());
  }

  public List<EstiloResponse> toResponse(List<Estilo> estilos) {
      if (estilos == null|| estilos.isEmpty()) {
        return new ArrayList<>();
      }
      return estilos.stream().map(this::toResponse).toList();
  }
}
