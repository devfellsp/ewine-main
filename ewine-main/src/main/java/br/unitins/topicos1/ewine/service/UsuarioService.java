package br.unitins.topicos1.ewine.service;

import br.unitins.topicos1.ewine.resource.auth.dto.input.LoginInput;
import br.unitins.topicos1.ewine.resource.usuario.dto.command.AtualizarSenhaCommand;
import br.unitins.topicos1.ewine.resource.usuario.dto.command.AtualizarUsuarioCommand;
import br.unitins.topicos1.ewine.resource.usuario.dto.input.UsuarioInput;
import br.unitins.topicos1.ewine.resource.usuario.dto.response.UsuarioResponse;
import java.util.List;

public interface UsuarioService {
  List<UsuarioResponse> buscarTodos();

  UsuarioResponse buscarPorId(Long id);

  UsuarioResponse buscarPorLogin(String login);

  UsuarioResponse buscarPorLoginESenha(LoginInput input);

  UsuarioResponse cadastrar(UsuarioInput dto);

  void atualizar(String login, AtualizarUsuarioCommand command);

  void atualizarSenha(String login, AtualizarSenhaCommand command);

  void deletar(Long id);
}
