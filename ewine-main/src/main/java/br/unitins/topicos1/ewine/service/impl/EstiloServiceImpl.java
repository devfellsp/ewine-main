package br.unitins.topicos1.ewine.service.impl;

import br.unitins.topicos1.ewine.infrastructure.persistence.EstiloRepository;
import br.unitins.topicos1.ewine.model.produto.vinho.Estilo;
import br.unitins.topicos1.ewine.resource.produto.dto.filter.EstiloFilter;
import br.unitins.topicos1.ewine.resource.produto.dto.input.EstiloInput;
import br.unitins.topicos1.ewine.resource.produto.dto.response.EstiloResponse;
import br.unitins.topicos1.ewine.service.EstiloService;
import br.unitins.topicos1.ewine.service.assembler.EstiloAssembler;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class EstiloServiceImpl implements EstiloService {

  @Inject EstiloRepository estiloRepository;

  @Inject EstiloAssembler assembler;

  @Override
  public List<EstiloResponse> filtrar(EstiloFilter filtro) {
    return assembler.toResponse(estiloRepository.filtrar(filtro));
  }

  @Override
  @Transactional
  public EstiloResponse cadastrar(EstiloInput input) {
    if (input == null) {
      throw new IllegalArgumentException("Input nao pode ser nulo.");
    }

    validarNomeUnico(input.nome());

    Estilo estilo = assembler.toEntity(input);

    estiloRepository.persist(estilo);

    return assembler.toResponse(estilo);
  }

  @Override
  @Transactional
  public EstiloResponse atualizar(Long id, EstiloInput input) {
    if (input == null) {
      throw new IllegalArgumentException("Input nao pode ser nulo.");
    }

    Estilo estilo = estiloRepository.findById(id);

    if (!input.nome().equals(estilo.getNome())) {
      validarNomeUnico(input.nome());
    }

    estilo.atualizar(input.nome());

    return assembler.toResponse(estilo);
  }

  @Override
  @Transactional
  public void deletar(Long id) {
    Estilo estilo = estiloRepository.findById(id);

    estiloRepository.delete(estilo);
  }

  @Override
  public List<EstiloResponse> buscarTodos() {
    List<Estilo> estilos = estiloRepository.listAll();

    return assembler.toResponse(estilos);
  }

  @Override
  public EstiloResponse buscarPorId(Long id) {
    Estilo estilo = estiloRepository.findById(id);

    return assembler.toResponse(estilo);
  }

  public List<EstiloResponse> findByName(String nome) {
    List<Estilo> estilos = estiloRepository.findByNome(nome);

    return assembler.toResponse(estilos);
  }

  private void validarNomeUnico(String nome) {
    if (estiloRepository.nomeExists(nome)) {
      throw new IllegalArgumentException("Nome já existente.");
    }
  }
}
