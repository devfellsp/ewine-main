package br.unitins.topicos1.ewine.resource.pedido.dto.response;

import br.unitins.topicos1.ewine.model.pedido.pagamento.PagamentoStatus;

public record PagamentoResponse(
    double valorTotal,
    int parcelas,
    PagamentoStatus status,
    String transacaoExternaId,
    FormaPagamentoResponse formaPagamento) {}
