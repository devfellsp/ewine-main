package br.unitins.topicos1.ewine.resource.usuario.cliente.dto.response;

import br.unitins.topicos1.ewine.resource.usuario.dto.response.UsuarioResponse;

import java.util.List;

public record ClienteResponse (String cpf, String email, UsuarioResponse usuario, List<EnderecoResponse> endereco) {}
