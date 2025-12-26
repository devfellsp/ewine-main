package br.unitins.topicos1.ewine.service.impl;

import br.unitins.topicos1.ewine.infrastructure.persistence.PaisRepository;
import br.unitins.topicos1.ewine.model.produto.vinho.Pais;
import br.unitins.topicos1.ewine.resource.produto.dto.filter.PaisFilter;
import br.unitins.topicos1.ewine.resource.produto.dto.input.PaisInput;
import br.unitins.topicos1.ewine.resource.produto.dto.response.PaisResponse;
import br.unitins.topicos1.ewine.service.PaisService;
import br.unitins.topicos1.ewine.service.assembler.PaisAssembler;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class PaisServiceImpl implements PaisService {

  @Inject PaisRepository paisRepository;
  @Inject PaisAssembler assembler;

  @Override
  public List<PaisResponse> filtrar(PaisFilter filtro) {
    return assembler.toResponse(paisRepository.filtrar(filtro));
  }

  @Override
  @Transactional
  public PaisResponse create(PaisInput input) {
    if (input == null) {
      throw new IllegalArgumentException("Input não pode ser nulo");
    }

    Pais pais = assembler.toEntity(input);

    paisRepository.persist(pais);

    return assembler.toResponse(pais);
  }

  @Override
  @Transactional
  public PaisResponse update(Long id, PaisInput input) {
    if (input == null) {
      throw new IllegalArgumentException("Input não pode ser nulo");
    }

    Pais pais = paisRepository.findById(id);

    pais.atualizar(input.nome(), input.sigla());

    return assembler.toResponse(pais);
  }

  @Override
  @Transactional
  public void delete(Long id) {
    Pais pais = paisRepository.findById(id);

    paisRepository.delete(pais);
  }

  @Override
  public PaisResponse findById(Long id) {
    Pais pais = paisRepository.findById(id);

    return assembler.toResponse(pais);
  }

  @Override
  public List<PaisResponse> findAll() {
    return assembler.toResponse(paisRepository.listAll());
  }
}
