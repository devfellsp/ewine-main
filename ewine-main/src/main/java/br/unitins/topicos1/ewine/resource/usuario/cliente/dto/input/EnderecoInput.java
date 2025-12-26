package br.unitins.topicos1.ewine.resource.usuario.cliente.dto.input;

import br.unitins.topicos1.ewine.resource.shared.dto.input.IdInput;

public record EnderecoInput(String cep, int numero, String complemento, String logradouro, IdInput cidade) {}
