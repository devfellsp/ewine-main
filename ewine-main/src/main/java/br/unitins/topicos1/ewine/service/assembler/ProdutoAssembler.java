package br.unitins.topicos1.ewine.service.assembler;

import br.unitins.topicos1.ewine.infrastructure.persistence.ProdutoRepository;
import br.unitins.topicos1.ewine.model.produto.Produto;
import br.unitins.topicos1.ewine.model.produto.vinho.*;
import br.unitins.topicos1.ewine.resource.produto.dto.input.VinhoInput;
import br.unitins.topicos1.ewine.resource.produto.dto.response.ProdutoResponse;
import br.unitins.topicos1.ewine.resource.produto.dto.response.VinhoResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class ProdutoAssembler {

  @Inject ProdutoRepository repository;
  @Inject PaisAssembler paisAssembler;
  @Inject TipoVinhoAssembler tipoVinhoAssembler;
  @Inject MarcaAssembler marcaAssembler;
  @Inject SafraAssembler safraAssembler;
  @Inject UvaAssembler uvaAssembler;
  @Inject EstiloAssembler estiloAssembler;
  @Inject OcasiaoAssembler ocasiaoAssembler;

  public Vinho toEntity(VinhoInput dto) {
    Vinho vinho =
        new Vinho(
            dto.sku(),
            dto.preco(),
            dto.quantEstoque(),
            dto.nome(),
            dto.descricao(),
            dto.teorAlcoolico(),
            dto.volume(),
            paisAssembler.toEntity(dto.pais()),
            tipoVinhoAssembler.toEntity(dto.tipoVinho()),
            marcaAssembler.toEntity(dto.marca()),
            safraAssembler.toEntity(dto.safra()),
            uvaAssembler.toEntity(dto.uvas()));

    if (dto.estilo() != null) {
      vinho.setEstilo(estiloAssembler.toEntity(dto.estilo()));
    }

    if (dto.ocasiao() != null) {
      vinho.setOcasiao(ocasiaoAssembler.toEntity(dto.ocasiao()));
    }

    return vinho;
  }

  public Object toResponse(Produto produto) {
    if (produto == null) {
      return null;
    }

    if (produto instanceof Vinho vinho) {
      return toResponse(vinho);
    }

    return toProdutoResponse(produto);
  }

  public VinhoResponse toResponse(Vinho vinho) {
    if (vinho == null) {
      return null;
    }

    return new VinhoResponse(
        vinho.getId(),
        vinho.getSku(),
        vinho.getPreco(),
        vinho.getEstoque().getQuantidade(),
        vinho.getNome(),
        vinho.getDescricao(),
        vinho.getImagem(),
        vinho.getTeorAlcoolico(),
        vinho.getVolume(),
        paisAssembler.toResponse(vinho.getPaisDeOrigem()),
        tipoVinhoAssembler.toResponse(vinho.getTipoVinho()),
        marcaAssembler.toResponse(vinho.getMarca()),
        safraAssembler.toResponse(vinho.getSafra()),
        estiloAssembler.toResponse(vinho.getEstilo()),
        ocasiaoAssembler.toResponse(vinho.getOcasiao()),
        uvaAssembler.toResponse(vinho.getUvas()));
  }

  public ProdutoResponse toProdutoResponse(Produto produto) {
    if (produto == null) {
      return null;
    }

    return new ProdutoResponse(
        produto.getId(),
        produto.getSku(),
        produto.getPreco(),
        produto.getEstoque().getQuantidade(),
        produto.getNome(),
        produto.getDescricao(),
        produto.getImagem(),
        produto.isAtivo());
  }

  public List<ProdutoResponse> toResponse(List<Produto> produtos) {
    return produtos.stream().map(this::toProdutoResponse).toList();
  }
}
