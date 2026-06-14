package br.unitins.topicos1.ewine.resource.carrinho.dto.response;

import java.util.List;

public record CarrinhoResponse(Long id, List<ItemCarrinhoResponse> itens, double total) {}
