package br.unitins.topicos1.ewine.resource.pagamento.dto.response;

import br.unitins.topicos1.ewine.model.pedido.PedidoStatus;
import br.unitins.topicos1.ewine.model.pedido.pagamento.PagamentoStatus;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProcessarPagamentoResponse(
        Long pedidoId,
        PedidoStatus statusPedido,
        PagamentoStatus statusPagamento,
        String transacaoId,
        DadosPixResponse pix,
        DadosBoletoResponse boleto,
        DadosCartaoResponse cartao
) {}