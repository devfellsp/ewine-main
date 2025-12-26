package br.unitins.topicos1.ewine.service.impl;

import br.unitins.topicos1.ewine.infrastructure.persistence.EstiloRepository;
import br.unitins.topicos1.ewine.infrastructure.persistence.UvaRepository;
import br.unitins.topicos1.ewine.model.produto.vinho.Uva;
import br.unitins.topicos1.ewine.resource.produto.dto.filter.UvaFilter;
import br.unitins.topicos1.ewine.resource.produto.dto.input.UvaInput;
import br.unitins.topicos1.ewine.resource.produto.dto.response.UvaResponse;
import br.unitins.topicos1.ewine.resource.shared.dto.input.IdInput;
import br.unitins.topicos1.ewine.service.UvaService;
import br.unitins.topicos1.ewine.service.assembler.UvaAssembler;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class UvaServiceImpl implements UvaService {
  @Inject UvaRepository uvaRepository;
  @Inject UvaAssembler assembler;

  @Override
  public List<UvaResponse> filtrar(UvaFilter filtro) {
    return assembler.toResponse(uvaRepository.filtrar(filtro));
  }

  @Override
  @Transactional
  public UvaResponse criar(UvaInput input) {
    if (input == null) {
      throw new NullPointerException("input nao pode ser nulo.");
    }

    validarNomeUnico(input.nome());

    Uva uva = assembler.ToEntity(input);

    uvaRepository.persist(uva);

    return assembler.toResponse(uva);
  }

  public UvaResponse atualizar(Long id, UvaInput input) {
    if (input == null) {
      throw new NullPointerException("input nao pode ser nulo.");
    }

    Uva uva = uvaRepository.findById(id);

    if (!input.nome().equals(uva.getNome())) {
      validarNomeUnico(input.nome());
    }

    uva.atualizar(input.nome());

    return assembler.toResponse(uva);
  }

  public void deletar(Long id) {
    if (id == null) {
      throw new NullPointerException("id nao pode ser nulo.");
    }

    uvaRepository.deleteById(id);
  }

  public UvaResponse buscarPorId(Long id) {
    if (id == null) {
      throw new NullPointerException("id nao pode ser nulo.");
    }

    Uva uva = uvaRepository.findById(id);

    return assembler.toResponse(uva);
  }

  public List<UvaResponse> buscarTodos() {
    List<Uva> uvas = uvaRepository.findAll().list();

    return assembler.toResponse(uvas);
  }

  private void validarNomeUnico(String nome) {
    if (uvaRepository.nomeExists(nome)) {
      throw new IllegalArgumentException("Nome já existente.");
    }
  }
}
