package br.unitins.topicos1.ewine.service.impl;

import br.unitins.topicos1.ewine.infrastructure.persistence.ClienteRepository;
import br.unitins.topicos1.ewine.infrastructure.persistence.ProdutoRepository;
import br.unitins.topicos1.ewine.model.produto.Produto;
import br.unitins.topicos1.ewine.model.usuario.cliente.Cliente;
import br.unitins.topicos1.ewine.resource.desejo.dto.ListaDesejosResponse;
import br.unitins.topicos1.ewine.service.ListaDesejosService;
import br.unitins.topicos1.ewine.service.assembler.ProdutoAssembler;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ListaDesejosServiceImpl implements ListaDesejosService {

  @Inject ClienteRepository clienteRepository;
  @Inject ProdutoRepository produtoRepository;
  @Inject ProdutoAssembler produtoAssembler;

  @Override
  public ListaDesejosResponse listar(String login) {
    Cliente cliente = clienteRepository.findByUsuarioLogin(login);
    return new ListaDesejosResponse(produtoAssembler.toResponse(cliente.getListaDesejos()));
  }

  @Override
  @Transactional
  public ListaDesejosResponse adicionar(String login, Long produtoId) {
    Cliente cliente = clienteRepository.findByUsuarioLogin(login);
    Produto produto = produtoRepository.findById(produtoId);
    cliente.adicionarDesejo(produto);
    return listar(login);
  }

  @Override
  @Transactional
  public ListaDesejosResponse remover(String login, Long produtoId) {
    Cliente cliente = clienteRepository.findByUsuarioLogin(login);
    cliente.removerDesejo(produtoId);
    return listar(login);
  }
}
