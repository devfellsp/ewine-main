package br.unitins.topicos1.ewine.service;

import br.unitins.topicos1.ewine.resource.pedido.dto.input.PedidoInput;
import br.unitins.topicos1.ewine.resource.pedido.dto.response.PedidoResponse;
import java.util.List;

public interface PedidoService {

  List<PedidoResponse> listarTodos();

  List<PedidoResponse> listarPedidosPorCliente(String login);

  PedidoResponse cadastrar(String login, PedidoInput input);

  PedidoResponse confirmarPagamento(Long pedidoId, String transacaoExternaId);

  PedidoResponse recusarPagamento(Long pedidoId);

  PedidoResponse enviar(Long pedidoId);

  PedidoResponse confirmarEntrega(Long pedidoId);

  PedidoResponse cancelar(Long pedidoId);
}
