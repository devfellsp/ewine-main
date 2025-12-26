package br.unitins.topicos1.ewine.service;

import br.unitins.topicos1.ewine.resource.usuario.cliente.dto.command.AtualizarEmailCommand;
import br.unitins.topicos1.ewine.resource.usuario.cliente.dto.input.ClienteCadastroInput;
import br.unitins.topicos1.ewine.resource.usuario.cliente.dto.input.ClienteInput;
import br.unitins.topicos1.ewine.resource.usuario.cliente.dto.input.EnderecoInput;
import br.unitins.topicos1.ewine.resource.usuario.cliente.dto.response.ClienteResponse;

import java.util.List;

public interface ClienteService {
  ClienteResponse cadastrar(ClienteCadastroInput input);

  ClienteResponse cadastrarEndereco(String login, EnderecoInput Input);

  ClienteResponse atualizarEmail(String login, AtualizarEmailCommand emailCommand);

  List<ClienteResponse> listarTodos();
}
