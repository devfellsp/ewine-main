package br.unitins.topicos1.ewine.service;

import br.unitins.topicos1.ewine.resource.produto.dto.filter.MarcaFilter;
import br.unitins.topicos1.ewine.resource.produto.dto.input.MarcaInput;
import br.unitins.topicos1.ewine.resource.produto.dto.response.MarcaResponse;
import br.unitins.topicos1.ewine.resource.shared.dto.response.PagedResponse;

import java.util.List;

public interface MarcaService {

  List<MarcaResponse> buscarTodos();

  PagedResponse<MarcaResponse> buscarTodos(int page, int size);

  MarcaResponse buscarPorId(Long id);

  List<MarcaResponse> filtrar(MarcaFilter filtro);

  PagedResponse<MarcaResponse> filtrar(MarcaFilter filtro, int page, int size);

  MarcaResponse criar(MarcaInput marca);

  MarcaResponse atualizar(Long id, MarcaInput marca);

  void deletar(Long id);
}
