package br.unitins.topicos1.ewine.infrastructure.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.unitins.topicos1.ewine.model.produto.vinho.Pais;
import br.unitins.topicos1.ewine.resource.produto.dto.filter.PaisFilter;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class PaisRepository implements PanacheRepository<Pais> {

  public List<Pais> filtrar(PaisFilter filtro) {
    if (filtro == null || filtro.isEmpty()) {
      return listAll();
    }

    StringBuilder jpql = new StringBuilder("true");
    Map<String, Object> params = new HashMap<>();

    if (filtro.getNome() != null && !filtro.getNome().isBlank()) {
      jpql.append(" AND LOWER(nome) LIKE LOWER(:nome)");
      params.put("nome", "%" + filtro.getNome() + "%");
    }

    if (filtro.getSigla() != null && !filtro.getSigla().isBlank()) {
      jpql.append(" AND LOWER(sigla) LIKE LOWER(:sigla)");
      params.put("sigla", "%" + filtro.getSigla() + "%");
    }

    return find(jpql.toString(), params).list();
  }

  public List<Pais> findByNome(String nome) {
    return find("UPPER(nome) LIKE ?1", "%" + nome.toUpperCase() + "%").list();
  }

  @Override
  public Pais findById(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("ID do país não pode ser nulo");
    }

    return findByIdOptional(id)
        .orElseThrow(() -> new NotFoundException("País não encontrado com ID: " + id));
  }
}
