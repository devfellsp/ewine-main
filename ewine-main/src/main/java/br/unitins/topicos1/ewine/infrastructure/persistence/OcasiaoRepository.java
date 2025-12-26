package br.unitins.topicos1.ewine.infrastructure.persistence;

import br.unitins.topicos1.ewine.model.produto.vinho.Ocasiao;
import br.unitins.topicos1.ewine.resource.produto.dto.filter.OcasiaoFilter;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class OcasiaoRepository implements PanacheRepository<Ocasiao> {

  public List<Ocasiao> filtrar(OcasiaoFilter filtro) {
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

  public List<Ocasiao> findByNome(String nome) {
    return find("nome like ?1", "%" + nome + "%").list();
  }

  public Ocasiao findById(int id) {
    return find("id = ?1", id).firstResult();
  }

  public boolean nomeExists(String nome) {
    return find("nome like ?1", nome).list().isEmpty();
  }

  public Ocasiao findById(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("ID do Ocasiao não pode ser nulo");
    }

    return findByIdOptional(id)
        .orElseThrow(() -> new NotFoundException("Ocasiao não encontrado com ID: " + id));
  }
}
