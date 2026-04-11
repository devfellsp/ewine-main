package br.unitins.topicos1.ewine.service;

import br.unitins.topicos1.ewine.resource.produto.dto.filter.UvaFilter;
import br.unitins.topicos1.ewine.resource.produto.dto.input.UvaInput;
import br.unitins.topicos1.ewine.resource.produto.dto.response.UvaResponse;
import br.unitins.topicos1.ewine.resource.shared.dto.response.PagedResponse;
import java.util.List;

public interface UvaService {
  
  List<UvaResponse> filtrar(UvaFilter filtro);

  PagedResponse<UvaResponse> filtrar(UvaFilter filtro, int page, int size);
  
  UvaResponse criar(UvaInput uvaInput);

  UvaResponse atualizar(Long id, UvaInput uvaInput);

  void deletar(Long id);

  UvaResponse buscarPorId(Long id);

  List<UvaResponse> buscarTodos();

  PagedResponse<UvaResponse> buscarTodos(int page, int size);
}
