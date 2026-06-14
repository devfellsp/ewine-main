package br.unitins.topicos1.ewine.resource.desejo.dto;

import br.unitins.topicos1.ewine.resource.produto.dto.response.ProdutoResponse;
import java.util.List;

public record ListaDesejosResponse(List<ProdutoResponse> produtos) {}
