package br.unitins.topicos1.ewine.service.assembler;

import br.unitins.topicos1.ewine.infrastructure.persistence.UsuarioRepository;
import br.unitins.topicos1.ewine.model.usuario.Perfil;
import br.unitins.topicos1.ewine.model.usuario.Usuario;
import br.unitins.topicos1.ewine.resource.usuario.dto.input.UsuarioInput;
import br.unitins.topicos1.ewine.resource.usuario.dto.response.UsuarioResponse;
import br.unitins.topicos1.ewine.service.HashService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class UsuarioAssembler {

  @Inject UsuarioRepository usuarioRepository;
  @Inject HashService hashService;

  public Usuario toEntity(UsuarioInput input) {
    String senha = hashService.getHashSenha(input.senha());
    Perfil perfil = Perfil.valueOf(input.perfil());

    return new Usuario(input.nome(), input.login(), senha, perfil);
  }

  public UsuarioResponse toResponse(Usuario usuario) {
    return new UsuarioResponse(usuario.getId(), usuario.getNome(), usuario.getLogin(), usuario.getPerfil());
  }

  public List<UsuarioResponse> toResponse(List<Usuario> usuarios) {
    if (usuarios == null || usuarios.isEmpty()) {
      return List.of();
    }

    return usuarios.stream().map(this::toResponse).toList();
  }
}
