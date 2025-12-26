package br.unitins.topicos1.ewine.service;

import br.unitins.topicos1.ewine.model.usuario.Perfil;

public interface AuthService {

  String generateToken(String login, Perfil perfil);
}
