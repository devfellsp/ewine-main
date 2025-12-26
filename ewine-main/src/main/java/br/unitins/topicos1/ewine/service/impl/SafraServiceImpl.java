package br.unitins.topicos1.ewine.service.impl;

import br.unitins.topicos1.ewine.infrastructure.persistence.SafraRepository;
import br.unitins.topicos1.ewine.model.produto.vinho.Safra;
import br.unitins.topicos1.ewine.resource.produto.dto.filter.SafraFilter;
import br.unitins.topicos1.ewine.resource.produto.dto.input.SafraInput;
import br.unitins.topicos1.ewine.resource.produto.dto.response.SafraResponse;
import br.unitins.topicos1.ewine.service.SafraService;
import br.unitins.topicos1.ewine.service.assembler.SafraAssembler;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class SafraServiceImpl implements SafraService {

  @Inject SafraRepository safraRepository;
  @Inject SafraAssembler assembler;

  @Override
  public List<SafraResponse> filtrar(SafraFilter filtro) {
    return assembler.toResponse(safraRepository.filtrar(filtro));
  }

  @Override
  @Transactional
  public SafraResponse cadastrar(SafraInput input) {
    if (input == null) {
      throw new NullPointerException("Input não pode ser nulo");
    }

    validarAnoEDescricao(input.ano(), input.descricao());

    Safra safra = assembler.toEntity(input);

    safraRepository.persist(safra);

    return assembler.toResponse(safra);
  }

  @Override
  @Transactional
  public SafraResponse atualizar(Long id, SafraInput input) {
    if (input == null) {
      throw new NullPointerException("Input não pode ser nulo");
    }

    Safra safra = safraRepository.findById(id);

    if (!input.ano().equals(safra.getAno()) || !input.descricao().equals(safra.getDescricao())) {
      validarAnoEDescricao(safra.getAno(), safra.getDescricao());
    }

    safra.atualizar(input.ano(), input.descricao());

    return assembler.toResponse(safra);
  }

  @Override
  @Transactional
  public void deletar(Long id) {
    Safra safra = safraRepository.findById(id);

    safraRepository.delete(safra);
  }

  @Override
  public SafraResponse buscarPorId(Long id) {
    Safra safra = safraRepository.findById(id);

    return assembler.toResponse(safra);
  }

  @Override
  public List<SafraResponse> buscarTodos() {
    return assembler.toResponse(safraRepository.listAll());
  }

  private void validarAnoEDescricao(int ano, String descricao) {
    if (safraRepository.anoAndDescricaoExists(ano, descricao)) {
      throw new IllegalArgumentException("Ano e descriçao ja existente.");
    }
  }
}
