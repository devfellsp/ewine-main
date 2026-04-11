package br.unitins.topicos1.ewine.service;

import br.unitins.topicos1.ewine.resource.produto.dto.filter.TipoVinhoFilter;
import br.unitins.topicos1.ewine.resource.produto.dto.input.TipoVinhoInput;
import br.unitins.topicos1.ewine.resource.produto.dto.response.TipoVinhoResponse;
import br.unitins.topicos1.ewine.resource.shared.dto.response.PagedResponse;

import java.util.List;

public interface TipoVinhoService {

  List<TipoVinhoResponse> filtrar(TipoVinhoFilter filtro);

  PagedResponse<TipoVinhoResponse> filtrar(TipoVinhoFilter filtro, int page, int size);

  TipoVinhoResponse cadastrar(TipoVinhoInput input);

  TipoVinhoResponse atualizar(Long id, TipoVinhoInput input);

  void deletar(Long id);

  List<TipoVinhoResponse> listarTodos();

  PagedResponse<TipoVinhoResponse> listarTodos(int page, int size);

  TipoVinhoResponse buscarPorId(Long id);
}
