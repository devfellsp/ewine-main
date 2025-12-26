package br.unitins.topicos1.ewine.service.impl;

import br.unitins.topicos1.ewine.model.usuario.Perfil;
import br.unitins.topicos1.ewine.service.AuthService;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
public class AuthServiceImpl implements AuthService {

  private static final Duration EXPIRATION_TIME = Duration.ofHours(24);

  @Override
  public String generateToken(String login, Perfil perfil) {
    Instant expiryDate = Instant.now().plus(EXPIRATION_TIME);

    Set<String> roles = new HashSet<>();
    roles.add(perfil.name());

    return Jwt.issuer("ewine-jwt").subject(login).groups(roles).expiresAt(expiryDate).sign();
  }
}
