package br.unitins.topicos1.ewine.service.impl;

import br.unitins.topicos1.ewine.model.produto.Produto;
import br.unitins.topicos1.ewine.model.produto.vinho.*;
import br.unitins.topicos1.ewine.infrastructure.persistence.ProdutoRepository;
import br.unitins.topicos1.ewine.resource.produto.dto.input.VinhoInput;
import br.unitins.topicos1.ewine.resource.produto.dto.response.ProdutoResponse;
import br.unitins.topicos1.ewine.resource.produto.dto.response.VinhoResponse;
import br.unitins.topicos1.ewine.service.ProdutoService;
import br.unitins.topicos1.ewine.service.assembler.ProdutoAssembler;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.jboss.logging.Logger;
import java.util.List;

@ApplicationScoped
public class ProdutoServiceImpl implements ProdutoService {

  private static final Logger LOG = Logger.getLogger(ProdutoServiceImpl.class);

  @Inject ProdutoRepository produtoRepository;
  @Inject ProdutoAssembler assembler;

  @Override
  @Transactional
  public ProdutoResponse alterarEstoque(Long id, Integer quantidade) {
    LOG.info("===== ALTERANDO ESTOQUE =====");
    LOG.info("Produto ID:  " + id);
    LOG.info("Nova quantidade: " + quantidade);

    try {
      Produto produto = produtoRepository.findById(id);

      if (produto == null) {
        LOG.error("Produto ID " + id + " não encontrado");
        throw new NotFoundException("Produto não encontrado");
      }

      // ✅ CORRIGIDO
      LOG.debug("Estoque anterior: " + produto.getEstoque().getQuantidade());

      produto.atualizarEstoque(quantidade);

      LOG.info("✅ Estoque atualizado com sucesso!");
      // ✅ CORRIGIDO
      LOG.info("Estoque atual: " + produto.getEstoque().getQuantidade());
      LOG.info("=============================");

      return assembler.toProdutoResponse(produto);

    } catch (Exception e) {
      LOG.error("❌ Erro ao alterar estoque do produto ID " + id + ":  " + e.getMessage());
      throw e;
    }
  }

  @Override
  @Transactional
  public ProdutoResponse atualizarPreco(Long id, Double novoPreco) {
    LOG.info("===== ATUALIZANDO PREÇO =====");
    LOG.info("Produto ID: " + id);
    LOG.info("Novo preço: R$ " + novoPreco);

    try {
      Produto produto = produtoRepository.findById(id);

      if (produto == null) {
        LOG.error("Produto ID " + id + " não encontrado");
        throw new NotFoundException("Produto não encontrado");
      }

      LOG.debug("Preço anterior: R$ " + produto.getPreco());

      produto.atualizarPreco(novoPreco);

      LOG.info("✅ Preço atualizado com sucesso!");
      LOG.info("Preço atual: R$ " + produto.getPreco());
      LOG.info("=============================");

      return assembler.toProdutoResponse(produto);

    } catch (Exception e) {
      LOG.error("❌ Erro ao atualizar preço do produto ID " + id + ":  " + e.getMessage());
      throw e;
    }
  }

  @Override
  @Transactional
  public ProdutoResponse alternarAtivo(Long id) {
    LOG.info("Alternando status ativo/inativo do produto ID: " + id);

    try {
      Produto produto = produtoRepository.findById(id);

      if (produto == null) {
        LOG.error("Produto ID " + id + " não encontrado");
        throw new NotFoundException("Produto não encontrado");
      }

      boolean statusAnterior = produto.isAtivo();

      produto.altenarAtivo();

      LOG.info("✅ Status alterado:  " + (statusAnterior ? "ATIVO → INATIVO" : "INATIVO → ATIVO"));

      return assembler.toProdutoResponse(produto);

    } catch (Exception e) {
      LOG.error("❌ Erro ao alternar status do produto ID " + id + ": " + e.getMessage());
      throw e;
    }
  }

  @Override
  @Transactional
  public VinhoResponse cadastrar(VinhoInput input) {
    LOG.info("===== CADASTRANDO NOVO VINHO =====");
    LOG.info("Nome: " + input.nome());
    LOG.info("SKU:  " + input.sku());
    LOG.info("Preço: R$ " + input.preco());
    // ✅ CORRIGIDO
    LOG.info("Estoque inicial: " + input.quantEstoque());

    try {
      LOG.debug("Validando SKU único...");
      validarSkuUnico(input.sku());

      LOG.debug("Montando entidade Vinho.. .");
      Vinho novoVinho = assembler.toEntity(input);

      LOG.debug("Persistindo no banco...");
      produtoRepository.persist(novoVinho);

      LOG.info("✅ Vinho cadastrado com sucesso!");
      LOG.info("ID do produto: " + novoVinho.getId());
      LOG.info("==================================");

      return assembler.toResponse(novoVinho);

    } catch (IllegalArgumentException e) {
      LOG.error("❌ Erro de validação: " + e.getMessage());
      throw e;
    } catch (Exception e) {
      LOG.error("❌ Erro ao cadastrar vinho: " + e.getMessage());
      throw e;
    }
  }

  @Override
  public VinhoResponse atualizar(Long id, VinhoInput input) {
    LOG.info("===== ATUALIZANDO VINHO =====");
    LOG.info("Produto ID: " + id);
    LOG.info("Novo nome: " + input.nome());

    try {
      Vinho vinho = (Vinho) produtoRepository.findById(id);

      if (vinho == null) {
        LOG.error("Vinho ID " + id + " não encontrado");
        throw new NotFoundException("Vinho não encontrado");
      }

      Vinho atualizado = assembler.toEntity(input);

      vinho.atualizar(
          atualizado.getNome(),
          atualizado.getDescricao(),
          atualizado.getTeorAlcoolico(),
          atualizado.getVolume(),
          atualizado.getPaisDeOrigem(),
          atualizado.getTipoVinho(),
          atualizado.getMarca(),
          atualizado.getSafra(),
          atualizado.getUvas(),
          atualizado.getEstilo(),
          atualizado.getOcasiao());

      LOG.info("✅ Vinho atualizado com sucesso!");
      LOG.info("=============================");

      return assembler.toResponse(atualizado);

    } catch (Exception e) {
      LOG.error("❌ Erro ao atualizar vinho ID " + id + ": " + e.getMessage());
      throw e;
    }
  }

  @Override
  public Object buscarPorId(Long id) {
    LOG.info("Buscando produto por ID: " + id);

    try {
      Produto produto = produtoExistente(id);

      LOG.info("✅ Produto encontrado: " + produto.getNome());

      return assembler.toResponse(produto);

    } catch (NotFoundException e) {
      LOG.error("❌ Produto ID " + id + " não encontrado");
      throw e;
    }
  }

  @Override
  @Transactional
  public List<ProdutoResponse> buscarTodos() {
    LOG.info("Listando todos os produtos");

    List<Produto> lista = produtoRepository.findAll().list();

    LOG.info("Total de produtos encontrados: " + lista.size());

    return assembler.toResponse(lista);
  }

  @Override
  public List<ProdutoResponse> buscarPorNome(String nome) {
    LOG.info("Buscando produtos por nome: " + nome);

    List<Produto> lista = produtoRepository.findAllByNome(nome);

    LOG.info("Produtos encontrados: " + lista.size());

    return assembler.toResponse(lista);
  }

  private void validarSkuUnico(String sku) {
    if (produtoRepository.skuExists(sku)) {
      LOG.warn("⚠️ Tentativa de cadastro com SKU duplicado: " + sku);
      throw new IllegalArgumentException("SKU já existe:  " + sku);
    }
    LOG.debug("SKU " + sku + " está disponível");
  }

  private Produto produtoExistente(Long id) {
    return produtoRepository
        .findByIdOptional(id)
        .orElseThrow(
            () -> {
              LOG.error("Produto com ID " + id + " não existe");
              return new NotFoundException("Produto não encontrado com ID:  " + id);
            });
  }
}
