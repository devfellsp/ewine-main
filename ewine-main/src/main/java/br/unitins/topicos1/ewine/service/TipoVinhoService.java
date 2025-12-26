package br.unitins.topicos1.ewine.service;

import br.unitins.topicos1.ewine.resource.produto.dto.filter.TipoVinhoFilter;
import br.unitins.topicos1.ewine.resource.produto.dto.input.TipoVinhoInput;
import br.unitins.topicos1.ewine.resource.produto.dto.response.TipoVinhoResponse;

import java.util.List;

public interface TipoVinhoService {

  List<TipoVinhoResponse> filtrar(TipoVinhoFilter filtro);

  TipoVinhoResponse cadastrar(TipoVinhoInput input);

  TipoVinhoResponse atualizar(Long id, TipoVinhoInput input);

  void deletar(Long id);

  List<TipoVinhoResponse> listarTodos();

  TipoVinhoResponse buscarPorId(Long id);
}
