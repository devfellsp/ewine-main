package br.unitins.topicos1.ewine.service;

import br.unitins.topicos1.ewine.resource.usuario.dto.input.RedefinirSenhaInput;
import br.unitins.topicos1.ewine.resource.usuario.dto.input.SolicitarRecuperacaoSenhaInput;
import br.unitins.topicos1.ewine.resource.usuario.dto.response.RecuperacaoSenhaResponse;

public interface SenhaRecuperacaoService {
  RecuperacaoSenhaResponse solicitar(SolicitarRecuperacaoSenhaInput input);
  void redefinir(RedefinirSenhaInput input);
}
