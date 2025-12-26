package br.unitins.topicos1.ewine.infrastructure.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.unitins.topicos1.ewine.model.produto.vinho.Pais;
import br.unitins.topicos1.ewine.model.produto.vinho.Uva;
import br.unitins.topicos1.ewine.resource.produto.dto.filter.UvaFilter;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class UvaRepository implements PanacheRepository<Uva> {

  public List<Uva> filtrar(UvaFilter filtro) {
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

  public List<Uva> findByNome(String nome) {
    return find("nome like ?1", "%" + nome + "%").list();
  }

  public boolean nomeExists(String nome) {
    return find("nome like ?1", nome).list().isEmpty();
  }

  public Uva findById(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("ID da uva não pode ser nulo");
    }

    return findByIdOptional(id)
        .orElseThrow(() -> new NotFoundException("uva não encontrada com ID: " + id));
  }
}
