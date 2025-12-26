package br.unitins.topicos1.ewine.resource.produto.dto.response;

import java.util.List;

public record VinhoResponse(
    Long id,
    String sku,
    double preco,
    int quantEstoque,
    String nome,
    String descricao,
    String imagem,
    Double teorAlcoolico,
    Integer volume,
    PaisResponse paisDeOrigem,
    TipoVinhoResponse tipoVinho,
    MarcaResponse marca,
    SafraResponse safra,
    EstiloResponse estilo,
    OcasiaoResponse ocasiao,
    List<UvaResponse> uvas) {}
