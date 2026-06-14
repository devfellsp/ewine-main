package br.unitins.topicos1.ewine.service.impl;

import br.unitins.topicos1.ewine.infrastructure.persistence.RecuperacaoSenhaRepository;
import br.unitins.topicos1.ewine.infrastructure.persistence.UsuarioRepository;
import br.unitins.topicos1.ewine.model.usuario.RecuperacaoSenha;
import br.unitins.topicos1.ewine.model.usuario.Usuario;
import br.unitins.topicos1.ewine.resource.usuario.dto.input.RedefinirSenhaInput;
import br.unitins.topicos1.ewine.resource.usuario.dto.input.SolicitarRecuperacaoSenhaInput;
import br.unitins.topicos1.ewine.resource.usuario.dto.response.RecuperacaoSenhaResponse;
import br.unitins.topicos1.ewine.service.HashService;
import br.unitins.topicos1.ewine.service.SenhaRecuperacaoService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class SenhaRecuperacaoServiceImpl implements SenhaRecuperacaoService {

  @Inject UsuarioRepository usuarioRepository;
  @Inject RecuperacaoSenhaRepository recuperacaoRepository;
  @Inject HashService hashService;

  @Override
  @Transactional
  public RecuperacaoSenhaResponse solicitar(SolicitarRecuperacaoSenhaInput input) {
    Usuario usuario = usuarioRepository.findByLoginOrEmail(input.loginOuEmail());
    if (usuario == null) {
      throw new NotFoundException("Usuario nao encontrado");
    }

    RecuperacaoSenha recuperacao = new RecuperacaoSenha(usuario);
    recuperacaoRepository.persist(recuperacao);
    return new RecuperacaoSenhaResponse(
        recuperacao.getToken(), recuperacao.getDataExpiracao().toString());
  }

  @Override
  @Transactional
  public void redefinir(RedefinirSenhaInput input) {
    RecuperacaoSenha recuperacao = recuperacaoRepository.findByToken(input.token());
    if (!recuperacao.isValido()) {
      throw new IllegalArgumentException("Token de recuperacao invalido ou expirado");
    }

    recuperacao.getUsuario().alterarSenha(hashService.getHashSenha(input.novaSenha()));
    recuperacao.marcarComoUsado();
  }
}
