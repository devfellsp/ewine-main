package br.unitins.topicos1.ewine.infrastructure.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.unitins.topicos1.ewine.model.produto.vinho.Safra;
import br.unitins.topicos1.ewine.model.produto.vinho.TipoVinho;
import br.unitins.topicos1.ewine.resource.produto.dto.filter.TipoVinhoFilter;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class TipoVinhoRepository implements PanacheRepository<TipoVinho> {

  public List<TipoVinho> filtrar(TipoVinhoFilter filtro) {
    if (filtro == null || filtro.isEmpty()) {
      return listAll();
    }

    StringBuilder jpql = new StringBuilder("true");
    Map<String, Object> params = new HashMap<>();

    if (filtro.getNome() != null && !filtro.getNome().isBlank()) {
      jpql.append(" AND LOWER(nome) LIKE LOWER(:nome)");
      params.put("nome", "%" + filtro.getNome() + "%");
    }

    return find(jpql.toString(), params).list();
  }


  public List<TipoVinho> findByNome(String nome) {
    return find("nome like ?1", "%" + nome + "%").list();
  }

  public boolean nomeExits(String nome) {
    return !find("nome like ?1").list().isEmpty();
  }

  public TipoVinho findById(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("ID do TipoVinho não pode ser nulo");
    }

    return findByIdOptional(id)
        .orElseThrow(() -> new NotFoundException("TipoVinho não encontrado com ID: " + id));
  }
}
