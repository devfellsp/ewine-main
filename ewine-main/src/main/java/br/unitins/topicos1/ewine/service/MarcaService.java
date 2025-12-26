package br.unitins.topicos1.ewine.service;

import br.unitins.topicos1.ewine.resource.produto.dto.filter.MarcaFilter;
import br.unitins.topicos1.ewine.resource.produto.dto.input.MarcaInput;
import br.unitins.topicos1.ewine.resource.produto.dto.response.MarcaResponse;

import java.util.List;

public interface MarcaService {

  MarcaResponse buscarPorId(Long id);

  List<MarcaResponse> filtrar(MarcaFilter filtro);

  MarcaResponse criar(MarcaInput marca);

  MarcaResponse atualizar(Long id, MarcaInput marca);

  void deletar(Long id);
}
