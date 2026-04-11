package br.unitins.topicos1.ewine.service;

import br.unitins.topicos1.ewine.resource.produto.dto.filter.SafraFilter;
import br.unitins.topicos1.ewine.resource.produto.dto.input.SafraInput;
import br.unitins.topicos1.ewine.resource.produto.dto.response.SafraResponse;
import br.unitins.topicos1.ewine.resource.shared.dto.response.PagedResponse;
import java.util.List;

public interface SafraService {

  List<SafraResponse> filtrar(SafraFilter filtro);

  PagedResponse<SafraResponse> filtrar(SafraFilter filtro, int page, int size);

  SafraResponse cadastrar(SafraInput input);

  SafraResponse atualizar(Long id, SafraInput input);

  void deletar(Long id);

  SafraResponse buscarPorId(Long id);

  List<SafraResponse> buscarTodos();

  PagedResponse<SafraResponse> buscarTodos(int page, int size);
}
