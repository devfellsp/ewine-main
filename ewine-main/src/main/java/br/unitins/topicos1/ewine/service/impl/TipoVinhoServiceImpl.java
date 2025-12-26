package br.unitins.topicos1.ewine.service.impl;

import br.unitins.topicos1.ewine.model.produto.vinho.TipoVinho;
import br.unitins.topicos1.ewine.infrastructure.persistence.TipoVinhoRepository;
import br.unitins.topicos1.ewine.resource.produto.dto.filter.TipoVinhoFilter;
import br.unitins.topicos1.ewine.resource.produto.dto.input.TipoVinhoInput;
import br.unitins.topicos1.ewine.resource.produto.dto.response.TipoVinhoResponse;
import br.unitins.topicos1.ewine.service.TipoVinhoService;
import br.unitins.topicos1.ewine.service.assembler.TipoVinhoAssembler;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public class TipoVinhoServiceImpl implements TipoVinhoService {

  @Inject TipoVinhoRepository tipoVinhoRepository;
  @Inject TipoVinhoAssembler assembler;

  @Override
  public List<TipoVinhoResponse> filtrar(TipoVinhoFilter filtro) {
    return assembler.toResponse(tipoVinhoRepository.filtrar(filtro));
  }

  @Transactional
  public TipoVinhoResponse cadastrar(TipoVinhoInput input) {
    TipoVinho tipo = assembler.toEntity(input);

    validarNome(tipo.getNome());

    tipoVinhoRepository.persist(tipo);

    return assembler.toResponse(tipo);
  }

  @Transactional
  public TipoVinhoResponse atualizar(Long id, TipoVinhoInput input) {
    if (input == null) {
      throw new IllegalArgumentException("TipoVinhoInput é obrigatório");
    }

    TipoVinho tipoVinho = tipoVinhoRepository.findById(id);

    if (!input.nome().equals(tipoVinho.getNome())) {
      validarNome(tipoVinho.getNome());
    }

    tipoVinho.atualizar(input.nome());

    return assembler.toResponse(tipoVinho);
  }

  @Transactional
  public void deletar(Long id) {
    TipoVinho tipoVinho = tipoVinhoRepository.findById(id);

    tipoVinhoRepository.delete(tipoVinho);
  }

  public List<TipoVinhoResponse> listarTodos() {
    List<TipoVinho> tipos = tipoVinhoRepository.listAll();

    return assembler.toResponse(tipos);
  }

  public TipoVinhoResponse buscarPorId(Long id) {
    TipoVinho tipo = tipoVinhoRepository.findById(id);

    return assembler.toResponse(tipo);
  }

  private void validarNome(String nome) {
    if (tipoVinhoRepository.nomeExits(nome)) {
      throw new IllegalArgumentException("nome já existente");
    }
  }
}
