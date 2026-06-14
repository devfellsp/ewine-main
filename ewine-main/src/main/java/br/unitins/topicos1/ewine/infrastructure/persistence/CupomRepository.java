package br.unitins.topicos1.ewine.infrastructure.persistence;

import br.unitins.topicos1.ewine.model.cupom.Cupom;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class CupomRepository implements PanacheRepository<Cupom> {

  public Cupom findByCodigo(String codigo) {
    if (codigo == null || codigo.isBlank()) {
      throw new IllegalArgumentException("Codigo do cupom e obrigatorio");
    }

    return find("codigo", codigo.trim().toUpperCase())
        .firstResultOptional()
        .orElseThrow(() -> new NotFoundException("Cupom nao encontrado"));
  }

  public boolean existsByCodigo(String codigo) {
    if (codigo == null || codigo.isBlank()) {
      return false;
    }
    return count("codigo", codigo.trim().toUpperCase()) > 0;
  }
}
