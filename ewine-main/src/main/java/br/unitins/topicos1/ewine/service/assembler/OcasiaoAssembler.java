package br.unitins.topicos1.ewine.service.assembler;

import br.unitins.topicos1.ewine.infrastructure.persistence.OcasiaoRepository;
import br.unitins.topicos1.ewine.model.produto.vinho.Ocasiao;
import br.unitins.topicos1.ewine.resource.produto.dto.input.OcasiaoInput;
import br.unitins.topicos1.ewine.resource.shared.dto.input.IdInput;
import br.unitins.topicos1.ewine.resource.produto.dto.response.OcasiaoResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class OcasiaoAssembler {

  @Inject OcasiaoRepository repository;

  public Ocasiao toEntity(IdInput dto) {
    if (dto == null || dto.id() == null) {
      return null;
    }

    return repository.findById(dto.id());
  }

  public Ocasiao toEntity(OcasiaoInput input) {
    if (input == null) {
      return null;
    }
    return new Ocasiao(input.nome());
  }

  public OcasiaoResponse toResponse(Ocasiao ocasiao) {
    if (ocasiao == null) {
      return null;
    }

    return new OcasiaoResponse(ocasiao.getId(), ocasiao.getNome());
  }

  public List<OcasiaoResponse> toResponse(List<Ocasiao> ocasioes) {
    if (ocasioes == null || ocasioes.isEmpty()) {
      return List.of();
    }
    return ocasioes.stream().map(this::toResponse).collect(Collectors.toList());
  }

}
