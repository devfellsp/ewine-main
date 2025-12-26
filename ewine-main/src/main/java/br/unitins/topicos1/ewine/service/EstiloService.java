package br.unitins.topicos1.ewine.service;

import br.unitins.topicos1.ewine.resource.produto.dto.filter.EstiloFilter;
import br.unitins.topicos1.ewine.resource.produto.dto.input.EstiloInput;
import br.unitins.topicos1.ewine.resource.produto.dto.response.EstiloResponse;

import java.util.List;

public interface EstiloService {

    List<EstiloResponse> filtrar(EstiloFilter filtro);

    EstiloResponse cadastrar(EstiloInput estiloInput);

    EstiloResponse atualizar(Long id, EstiloInput EstiloInput);

    EstiloResponse buscarPorId(Long id);

    void deletar(Long id);

    List<EstiloResponse> buscarTodos();
}
