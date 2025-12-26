package br.unitins.topicos1.ewine.resource.auth.dto.reponse;

import br.unitins.topicos1.ewine.model.usuario.Perfil;
import br.unitins.topicos1.ewine.model.usuario.Usuario;

public record LoginDTOResponse(String token, UsuarioLogado usuario, String expiresIn) {

  public static record UsuarioLogado(Long id, String nome, String login, Perfil perfil) {}

  public static LoginDTOResponse valueOf(String token, Usuario usuario) {
    return new LoginDTOResponse(
        token,
        new UsuarioLogado(
            usuario.getId(), usuario.getNome(), usuario.getLogin(), usuario.getPerfil()),
        "24h");
  }
}
