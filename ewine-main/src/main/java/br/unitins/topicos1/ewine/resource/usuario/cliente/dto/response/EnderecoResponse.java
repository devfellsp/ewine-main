package br.unitins.topicos1.ewine.resource.usuario.cliente.dto.response;

import br.unitins.topicos1.ewine.resource.shared.dto.input.IdInput;

public record EnderecoResponse(Long id,String cep, int numero, String complemento, String logradouro, String cidade) {}
