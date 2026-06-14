package br.unitins.topicos1.ewine.service;

import br.unitins.topicos1.ewine.resource.desejo.dto.ListaDesejosResponse;

public interface ListaDesejosService {
  ListaDesejosResponse listar(String login);
  ListaDesejosResponse adicionar(String login, Long produtoId);
  ListaDesejosResponse remover(String login, Long produtoId);
}
