package br.unitins.topicos1.ewine.infrastructure.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.unitins.topicos1.ewine.model.produto.vinho.Estilo;
import br.unitins.topicos1.ewine.model.produto.vinho.TipoVinho;
import br.unitins.topicos1.ewine.resource.produto.dto.filter.EstiloFilter;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class EstiloRepository implements PanacheRepository<Estilo> {

  public List<Estilo> filtrar(EstiloFilter filtro) {
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


  public List<Estilo> findByNome(String nome) {
    return find("nome like ?1", "%" + nome + "%").list();
  }

  public Estilo findById(int id) {
    return find("id == ?1", id).firstResult();
  }

  public boolean nomeExists(String nome) {
    return find("nome like ?1", nome).list().isEmpty();
  }

  public Estilo findById(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("ID do Estilo não pode ser nulo");
    }

    return findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Estilo não encontrado com ID: " + id));
  }

}
