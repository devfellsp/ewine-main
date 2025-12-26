package br.unitins.topicos1.ewine.resource.shared.util;

import br.unitins.topicos1.ewine.model.usuario.Usuario;
import br.unitins.topicos1.ewine.infrastructure.persistence.UsuarioRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.jwt.JsonWebToken;

@RequestScoped
public class UsuarioLogado {

  @Inject JsonWebToken jwt;

  @Inject UsuarioRepository usuarioRepository;

  public String getLogin() {
    return jwt.getSubject();
  }

  public Usuario getUsuario() {
    if (getLogin() == null) {
      throw new IllegalStateException("Usuário não autenticado");
    }

    Usuario usuario = usuarioRepository.findByLogin(getLogin());

    if (usuario == null) {
      throw new IllegalArgumentException("Usuario nao esta autenticado");
    }

    return usuario;
  }
}
