package br.unitins.topicos1.ewine.service;

import br.unitins.topicos1.ewine.resource. pagamento.dto.input.*;
import br.unitins.topicos1.ewine.resource. pagamento.dto.response. ProcessarPagamentoResponse;

public interface PagamentoService {
    ProcessarPagamentoResponse processarPagamentoPix(Long pedidoId);
    ProcessarPagamentoResponse processarPagamentoBoleto(Long pedidoId);
    ProcessarPagamentoResponse processarPagamentoCartao(Long pedidoId, ProcessarPagamentoCartaoInput input);
}