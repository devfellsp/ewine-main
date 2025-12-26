package br.unitins.topicos1.ewine.resource.pedido.dto.response;

import br.unitins.topicos1.ewine.model.pedido.PedidoStatus;
import br.unitins.topicos1.ewine.resource.usuario.cliente.dto.response.ClienteResponse;

import java.util.List;

public record PedidoResponse(
    Long id,
    String dataCriacao,
    PedidoStatus status,
    ClienteResponse cliente,
    PagamentoResponse pagamento,
    List<ItemPedidoResponse> itens) {}
