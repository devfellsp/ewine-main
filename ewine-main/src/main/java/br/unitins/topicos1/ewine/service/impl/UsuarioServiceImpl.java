package br.unitins.topicos1.ewine.service.impl;

import br.unitins.topicos1.ewine.infrastructure.persistence.UsuarioRepository;
import br.unitins.topicos1.ewine.model.usuario.Usuario;
import br.unitins.topicos1.ewine.resource.auth.dto.input.LoginInput;
import br.unitins.topicos1.ewine.resource.usuario.dto.command.AtualizarSenhaCommand;
import br.unitins.topicos1.ewine.resource.usuario.dto.command.AtualizarUsuarioCommand;
import br.unitins.topicos1.ewine.resource.usuario.dto.input.UsuarioInput;
import br.unitins.topicos1.ewine.resource.usuario.dto.response.UsuarioResponse;
import br.unitins.topicos1.ewine.service.HashService;
import br.unitins.topicos1.ewine.service.UsuarioService;
import br.unitins.topicos1.ewine.service.assembler.UsuarioAssembler;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService {

  @Inject UsuarioRepository repository;
  @Inject HashService hashService;
  @Inject UsuarioAssembler assembler;

  @Override
  public List<UsuarioResponse> buscarTodos() {
    List<Usuario> usuarios = repository.findAll().list();

    return assembler.toResponse(usuarios);
  }

  @Override
  public UsuarioResponse buscarPorId(Long id) {
    Usuario usuario = repository.findById(id);

    return assembler.toResponse(usuario);
  }

  @Override
  public UsuarioResponse buscarPorLogin(String login) {
    Usuario usuario = repository.findByLogin(login);

    return assembler.toResponse(usuario);
  }

  @Override
  public UsuarioResponse buscarPorLoginESenha(LoginInput input) {
    Usuario usuario =
        repository.findByLoginSenha(input.login(), hashService.getHashSenha(input.senha()));

    return assembler.toResponse(usuario);
  }

  @Override
  @Transactional
  public UsuarioResponse cadastrar(UsuarioInput input) {
    if (input == null) {
      throw new NullPointerException("Input nulo");
    }

    validarLogin(input.login());

    Usuario usuario = assembler.toEntity(input);

    repository.persist(usuario);

    return assembler.toResponse(usuario);
  }

  @Override
  @Transactional
  public void atualizar(String login, AtualizarUsuarioCommand command) {
    Usuario usuario = repository.findByLogin(login);

    usuario.atualizar(command.nome());
  }

  @Override
  public void atualizarSenha(String login, AtualizarSenhaCommand command) {
    String senha = hashService.getHashSenha(command.senha());

    Usuario usuario = repository.findByLogin(login);

    usuario.alterarSenha(senha);
  }

  @Override
  @Transactional
  public void deletar(Long id) {
    repository.deleteById(id);
  }

  private void validarLogin(String login) {
    if (repository.loginExists(login)) {
      throw new IllegalArgumentException("login existente");
    }
  }
}
