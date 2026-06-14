package br.unitins.topicos1.ewine.infrastructure.persistence;

import br.unitins.topicos1.ewine.model.usuario.RecuperacaoSenha;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class RecuperacaoSenhaRepository implements PanacheRepository<RecuperacaoSenha> {

  public RecuperacaoSenha findByToken(String token) {
    return find("token", token)
        .firstResultOptional()
        .orElseThrow(() -> new NotFoundException("Token de recuperacao nao encontrado"));
  }
}
