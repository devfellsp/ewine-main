package br.unitins.topicos1.ewine.infrastructure.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.unitins.topicos1.ewine.model.produto.vinho.Marca;
import br.unitins.topicos1.ewine.model.produto.vinho.Safra;
import br.unitins.topicos1.ewine.resource.produto.dto.filter.MarcaFilter;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class MarcaRepository implements PanacheRepository<Marca> {

  public List<Marca> findByNome(String nome) {
    return find("nome like ?1", "%" + nome + "%").list();
  }

  public List<Marca> filtrar(MarcaFilter filtro) {
    if (filtro == null || filtro.isEmpty()) {
      return listAll();
    }

    StringBuilder jpql = new StringBuilder("true");
    Map<String, Object> params = new HashMap<>();

    if (filtro.getNome() != null && !filtro.getNome().isBlank()) {
      jpql.append(" AND LOWER(nome) LIKE LOWER(:nome)");
      params.put("nome", "%" + filtro.getNome() + "%");
    }

    if (filtro.getPaisDeOrigem() != null && !filtro.getPaisDeOrigem().isBlank()) {
      jpql.append(" AND LOWER(paisDeOrigem) LIKE LOWER(:pais)");
      params.put("pais", "%" + filtro.getPaisDeOrigem() + "%");
    }

    if (filtro.getAnofundacao() != null && !filtro.getAnofundacao().isBlank()) {
      jpql.append(" AND LOWER(anoFundacao) LIKE LOWER(:ano)");
      params.put("ano", "%" + filtro.getAnofundacao() + "%");
    }

    if (filtro.getClassificacao() != null && !filtro.getClassificacao().isBlank()) {
      jpql.append(" AND LOWER(classificacao) LIKE LOWER(:classificacao)");
      params.put("classificacao", "%" + filtro.getClassificacao() + "%");
    }

    return find(jpql.toString(), params).list();
  }

  public Marca findById(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("ID da Marca não pode ser nulo");
    }

    return findByIdOptional(id)
        .orElseThrow(() -> new NotFoundException("Marca não encontrada com ID: " + id));
  }
}
