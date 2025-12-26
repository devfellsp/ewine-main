package br.unitins.topicos1.ewine.resource.produto.dto.response;

public record ProdutoResponse(
    Long id,
    String sku,
    double preco,
    int quantEstoque,
    String nome,
    String descricao,
    String imagem,
    boolean ativo) {}
