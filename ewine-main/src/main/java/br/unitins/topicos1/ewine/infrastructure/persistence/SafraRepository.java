package br.unitins.topicos1.ewine.infrastructure.persistence;

import br.unitins.topicos1.ewine.model.produto.vinho.Safra;
import br.unitins.topicos1.ewine.resource.produto.dto.filter.SafraFilter;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class SafraRepository implements PanacheRepository<Safra> {

  public List<Safra> filtrar(SafraFilter filtro) {
    if (filtro == null || filtro.isEmpty()) {
      return listAll();
    }

    StringBuilder jpql = new StringBuilder("true");
    Map<String, Object> params = new HashMap<>();

    if (filtro.getAno() != null && !filtro.getAno().isBlank()) {
      jpql.append(" AND LOWER(CAST(ano AS String)) LIKE LOWER(:ano)");
      params.put("ano", "%" + filtro.getAno() + "%");
    }

    if (filtro.getDescricao() != null && !filtro.getDescricao().isBlank()) {
      jpql.append(" AND LOWER(descricao) LIKE LOWER(:descricao)");
      params.put("descricao", "%" + filtro.getDescricao() + "%");
    }

    return find(jpql.toString(), params).list();
  }

  public Safra findByAno(Integer ano) {
    return find("ano = ?1", ano).firstResult();
  }

  public List<Safra> findByAnoLike(String ano) {
    return find("CAST(ano AS string) like ?1", "%" + ano + "%").list();
  }

  public Safra findById(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("ID da safra não pode ser nulo");
    }

    return findByIdOptional(id)
        .orElseThrow(() -> new NotFoundException("Safra não encontrada com ID: " + id));
  }

  public boolean anoAndDescricaoExists(int ano, String descricao) {
    return find("ano = ?1 and descricao like ?2", ano, descricao).list().isEmpty();
  }
}
