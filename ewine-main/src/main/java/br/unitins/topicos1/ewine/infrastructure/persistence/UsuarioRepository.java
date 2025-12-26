package br.unitins.topicos1.ewine.infrastructure.persistence;

import br.unitins.topicos1.ewine.model.usuario.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;
import java.util.List;

@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<Usuario> {

  public Usuario findByLogin(String login) {
    return find("login = ?1", login).firstResult();
  }

  public Usuario findByLoginSenha(String login, String senha) {
    return find("login = ?1 AND senha = ?2 ", login, senha).firstResult();
  }

  public List<Usuario> listAllPaged(int page, int size) {
    return findAll().page(Page.of(page, size)).list();
  }

  public boolean loginExists(String login) {
    return !find("login = ?1", login).list().isEmpty();
  }

  public Usuario findById(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("ID do usuario não pode ser nulo");
    }

    return findByIdOptional(id)
        .orElseThrow(() -> new NotFoundException("Usuario não encontrado com ID: " + id));
  }
}
