package br.unitins.topicos1.ewine.service.assembler;

import br.unitins.topicos1.ewine.infrastructure.persistence.UvaRepository;
import br.unitins.topicos1.ewine.model.produto.vinho.Uva;
import br.unitins.topicos1.ewine.resource.produto.dto.input.UvaInput;
import br.unitins.topicos1.ewine.resource.produto.dto.response.UvaResponse;
import br.unitins.topicos1.ewine.resource.shared.dto.input.IdInput;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class UvaAssembler {

  @Inject UvaRepository repository;

  public Uva toEntity(IdInput dto) {
    if (dto == null || dto.id() == null) {
      return null;
    }

    return repository.findById(dto.id());
  }

  public  Uva ToEntity(UvaInput input) {
    if (input == null ) {
      return null;
    }
    return new  Uva(input.nome());

  }
  public List<Uva> toEntity(List<IdInput> dto) {
    if (dto == null) {
      return null;
    }

    return dto.stream().map(this::toEntity).toList();
  }

  public UvaResponse toResponse(Uva uva) {
    if (uva == null) {
      return null;
    }

    return new UvaResponse(uva.getId(), uva.getNome());
  }

  public List<UvaResponse> toResponse(List<Uva> uvas) {
    if (uvas == null || uvas.isEmpty()) {
      return null;
    }

    return uvas.stream().map(this::toResponse).toList();
  }
}
