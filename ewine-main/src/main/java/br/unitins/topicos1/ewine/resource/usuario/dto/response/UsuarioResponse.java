package br.unitins.topicos1.ewine.resource.usuario.dto.response;

import br.unitins.topicos1.ewine.model.usuario.Perfil;

public record UsuarioResponse(Long id, String nome, String login, Perfil perfil) {}
