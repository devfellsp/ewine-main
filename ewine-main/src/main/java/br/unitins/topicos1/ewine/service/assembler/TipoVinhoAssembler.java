package br.unitins.topicos1.ewine.service.assembler;

import br.unitins.topicos1.ewine.infrastructure.persistence.TipoVinhoRepository;
import br.unitins.topicos1.ewine.model.produto.vinho.TipoVinho;
import br.unitins.topicos1.ewine.resource.shared.dto.input.IdInput;
import br.unitins.topicos1.ewine.resource.produto.dto.input.TipoVinhoInput;
import br.unitins.topicos1.ewine.resource.produto.dto.response.TipoVinhoResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class TipoVinhoAssembler {

  @Inject TipoVinhoRepository repository;

  public TipoVinho toEntity(TipoVinhoInput input) {
    return new TipoVinho(input.nome());
  }

  public TipoVinho toEntity(IdInput dto) {
    if (dto == null) {
      return null;
    }

    return repository.findById(dto.id());
  }

  public TipoVinhoResponse toResponse(TipoVinho tipoVinho) {
    if (tipoVinho == null) {
      return null;
    }

    return new TipoVinhoResponse(tipoVinho.getId(), tipoVinho.getNome());
  }

  public List<TipoVinhoResponse> toResponse(List<TipoVinho> tipoVinhos) {
    if (tipoVinhos == null || tipoVinhos.isEmpty()) {
      return List.of();
    }
    return tipoVinhos.stream().map(this::toResponse).collect(Collectors.toList());
  }
}
