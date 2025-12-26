package br.unitins.topicos1.ewine.infrastructure.persistence;

import br.unitins.topicos1.ewine.model.produto.Produto;
import br.unitins.topicos1.ewine.model.produto.vinho.Ocasiao;
import br.unitins.topicos1.ewine.model.produto.vinho.Vinho;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public class ProdutoRepository implements PanacheRepository<Produto> {

  public boolean skuExists(String sku) {
    return find("sku = ?1", sku).count() > 0;
  }

  public List<Produto> findAllByNome(String nome) {
    return find("LOWER(nome) LIKE LOWER(?1)", "%" + nome + "%").list();
  }

  public Produto findById(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("ID do Produto não pode ser nulo");
    }

    return findByIdOptional(id)
        .orElseThrow(() -> new NotFoundException("Produto não encontrado com ID: " + id));
  }
}
