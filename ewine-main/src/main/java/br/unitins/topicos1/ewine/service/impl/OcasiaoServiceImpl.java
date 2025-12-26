package br.unitins.topicos1.ewine.service.impl;

import br.unitins.topicos1.ewine.infrastructure.persistence.OcasiaoRepository;
import br.unitins.topicos1.ewine.model.produto.vinho.Ocasiao;
import br.unitins.topicos1.ewine.resource.produto.dto.filter.OcasiaoFilter;
import br.unitins.topicos1.ewine.resource.produto.dto.input.OcasiaoInput;
import br.unitins.topicos1.ewine.resource.produto.dto.response.OcasiaoResponse;
import br.unitins.topicos1.ewine.service.OcasiaoService;
import br.unitins.topicos1.ewine.service.assembler.OcasiaoAssembler;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class OcasiaoServiceImpl implements OcasiaoService {

  @Inject OcasiaoRepository ocasiaoRepository;
  @Inject OcasiaoAssembler assembler;

  @Override
  public List<OcasiaoResponse> filtrar(OcasiaoFilter filtro) {
    return assembler.toResponse(ocasiaoRepository.filtrar(filtro));
  }

  @Override
  @Transactional
  public OcasiaoResponse criar(OcasiaoInput input) {
    if (input == null) {
      throw new IllegalArgumentException("input nao pode ser nulo.");
    }

    validarNomeUnico(input.nome());

    Ocasiao ocasiao = assembler.toEntity(input);

    ocasiaoRepository.persist(ocasiao);

    return assembler.toResponse(ocasiao);
  }

  @Override
  @Transactional
  public OcasiaoResponse atualizar(Long id, OcasiaoInput input) {
    if (input == null) {
      throw new IllegalArgumentException("input nao pode ser nulo.");
    }

    Ocasiao ocasiao = ocasiaoRepository.findById(id);

    if (!ocasiao.getNome().equals(input.nome())) {
      validarNomeUnico(input.nome());
    }

    ocasiao.atualizar(input.nome());

    return assembler.toResponse(ocasiao);
  }

  @Override
  @Transactional
  public void deletar(Long id) {
    Ocasiao ocasiao = ocasiaoRepository.findById(id);

    ocasiaoRepository.delete(ocasiao);
  }

  @Override
  public OcasiaoResponse buscarPorId(Long id) {
    Ocasiao ocasiao = ocasiaoRepository.findById(id);

    return assembler.toResponse(ocasiao);
  }


  public List<OcasiaoResponse> listarTodos() {
    List<Ocasiao> ocasioes = ocasiaoRepository.listAll();

    return assembler.toResponse(ocasioes);
  }

  private void validarNomeUnico(String nome) {
    if (ocasiaoRepository.nomeExists(nome)) {
      throw new IllegalArgumentException("Nome ja cadastrado.");
    }
  }
}
