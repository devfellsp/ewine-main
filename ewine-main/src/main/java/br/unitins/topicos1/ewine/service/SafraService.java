package br.unitins.topicos1.ewine.service;

import br.unitins.topicos1.ewine.resource.produto.dto.filter.SafraFilter;
import br.unitins.topicos1.ewine.resource.produto.dto.input.SafraInput;
import br.unitins.topicos1.ewine.resource.produto.dto.response.SafraResponse;
import java.util.List;

public interface SafraService {

  List<SafraResponse> filtrar(SafraFilter filtro);

  SafraResponse cadastrar(SafraInput input);

  SafraResponse atualizar(Long id, SafraInput input);

  void deletar(Long id);

  SafraResponse buscarPorId(Long id);

  List<SafraResponse> buscarTodos();
}
