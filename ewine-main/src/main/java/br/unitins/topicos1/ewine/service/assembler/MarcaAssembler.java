package br.unitins.topicos1.ewine.service.assembler;

import br.unitins.topicos1.ewine.infrastructure.persistence.MarcaRepository;
import br.unitins.topicos1.ewine.model.produto.vinho.Marca;
import br.unitins.topicos1.ewine.resource.produto.dto.input.MarcaInput;
import br.unitins.topicos1.ewine.resource.shared.dto.input.IdInput;
import br.unitins.topicos1.ewine.resource.produto.dto.response.MarcaResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class MarcaAssembler {

  @Inject MarcaRepository repository;

  public Marca toEntity(IdInput dto) {
    if (dto == null || dto.id() == null) {
      return null;
    }

    return repository.findById(dto.id());
  }

  public Marca toEntity(MarcaInput input) {
    if (input == null) {
      return null;
    }

    return new Marca(input.nome(), input.paisDeOrigem(), input.anoFundacao(), input.classificacao());
  }

  public MarcaResponse toResponse(Marca marca) {
    if (marca == null) {
      return null;
    }

    return new MarcaResponse(
        marca.getId(),
        marca.getNome(),
        marca.getPaisDeOrigem(),
        marca.getAnoFundacao(),
        marca.getClassificacao());
  }

  public List<MarcaResponse> toResponse(List<Marca> marcas) {
    return marcas.stream().map(this::toResponse).toList();
  }
}
