package br.unitins.topicos1.ewine.service;

import br.unitins.topicos1.ewine.resource.produto.dto.filter.PaisFilter;
import br.unitins.topicos1.ewine.resource.produto.dto.input.PaisInput;
import br.unitins.topicos1.ewine.resource.produto.dto.response.PaisResponse;
import java.util.List;

public interface PaisService {

  List<PaisResponse> filtrar(PaisFilter filtro);
  
  PaisResponse create(PaisInput paisInput);

  PaisResponse update(Long id, PaisInput paisInput);

  PaisResponse findById(Long id);

  void delete(Long id);

  List<PaisResponse> findAll();
}
