package br.unitins.topicos1.ewine.service.assembler;

import br.unitins.topicos1.ewine.infrastructure.persistence.SafraRepository;

import br.unitins.topicos1.ewine.model.produto.vinho.Safra;
import br.unitins.topicos1.ewine.resource.produto.dto.input.SafraInput;

import br.unitins.topicos1.ewine.resource.shared.dto.input.IdInput;
import br.unitins.topicos1.ewine.resource.produto.dto.response.SafraResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class SafraAssembler {

  @Inject SafraRepository repository;

  public Safra toEntity(IdInput dto) {
    if (dto == null || dto.id() == null) {
      return null;
    }

    return repository.findById(dto.id());
  }

  public Safra toEntity(SafraInput input) {
    if (input == null) {
      return null;
    }

    return new Safra(input.ano(), input.descricao());
  }

  public SafraResponse toResponse(Safra safra) {
    if (safra == null) {
      return null;
    }

    return new SafraResponse(safra.getId(), safra.getAno(), safra.getDescricao());
  }

  public List<SafraResponse> toResponse(List<Safra> safras) {
    if (safras == null || safras.isEmpty()) {
      return new ArrayList<>();
    }

    return safras.stream().map(this::toResponse).toList();
  }
}
