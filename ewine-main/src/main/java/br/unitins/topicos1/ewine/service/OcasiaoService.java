package br.unitins.topicos1.ewine.service;

import br.unitins.topicos1.ewine.resource.produto.dto.filter.OcasiaoFilter;
import br.unitins.topicos1.ewine.resource.produto.dto.input.OcasiaoInput;
import br.unitins.topicos1.ewine.resource.produto.dto.response.OcasiaoResponse;
import java.util.List;

public interface OcasiaoService {

  List<OcasiaoResponse> filtrar(OcasiaoFilter filtro);

  OcasiaoResponse criar(OcasiaoInput input);

  OcasiaoResponse atualizar(Long id, OcasiaoInput input);

  void deletar(Long id);

  List<OcasiaoResponse> listarTodos();

  OcasiaoResponse buscarPorId(Long id);
}
