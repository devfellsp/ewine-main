package br.unitins.topicos1.ewine.resource.carrinho.dto.response;

public record ItemCarrinhoResponse(
    Long produtoId,
    String nome,
    String imagem,
    double precoUnitario,
    int quantidade,
    double subtotal,
    int estoqueDisponivel) {}
