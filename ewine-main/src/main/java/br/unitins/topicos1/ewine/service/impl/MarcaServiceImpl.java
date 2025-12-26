package br.unitins.topicos1.ewine.service.impl;

import br.unitins.topicos1.ewine.infrastructure.persistence.MarcaRepository;
import br.unitins.topicos1.ewine.model.produto.vinho.Marca;
import br.unitins.topicos1.ewine.resource.produto.dto.filter.MarcaFilter;
import br.unitins.topicos1.ewine.resource.produto.dto.input.MarcaInput;
import br.unitins.topicos1.ewine.resource.produto.dto.response.MarcaResponse;
import br.unitins.topicos1.ewine.service.MarcaService;
import br.unitins.topicos1.ewine.service.assembler.MarcaAssembler;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class MarcaServiceImpl implements MarcaService {

  @Inject MarcaRepository repository;
  @Inject MarcaAssembler assembler;

  @Override
  public MarcaResponse buscarPorId(Long id) {
    Marca marca = repository.findById(id);

    return assembler.toResponse(marca);
  }

  @Override
  public List<MarcaResponse> filtrar(MarcaFilter filtro) {
    List<Marca> marcas = repository.filtrar(filtro);

    return assembler.toResponse(marcas);
  }

  @Override
  @Transactional
  public MarcaResponse criar(MarcaInput input) {
    Marca marca = assembler.toEntity(input);

    repository.persist(marca);

    return assembler.toResponse(marca);
  }

  @Override
  @Transactional
  public MarcaResponse atualizar(Long id, MarcaInput input) {
    Marca marca = repository.findById(id);

    marca.atualizar(input.nome(), input.paisDeOrigem(), input.anoFundacao(), input.classificacao());

    return assembler.toResponse(marca);
  }

  @Override
  @Transactional
  public void deletar(Long id) {
    Marca marca = repository.findById(id);

    repository.delete(marca);
  }
}
