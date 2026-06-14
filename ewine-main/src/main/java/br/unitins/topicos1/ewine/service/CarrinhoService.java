package br.unitins.topicos1.ewine.service;

import br.unitins.topicos1.ewine.resource.carrinho.dto.input.FinalizarCarrinhoInput;
import br.unitins.topicos1.ewine.resource.carrinho.dto.input.ItemCarrinhoInput;
import br.unitins.topicos1.ewine.resource.carrinho.dto.response.CarrinhoResponse;
import br.unitins.topicos1.ewine.resource.pedido.dto.response.PedidoResponse;

public interface CarrinhoService {
  CarrinhoResponse buscar(String login);
  CarrinhoResponse adicionarItem(String login, ItemCarrinhoInput input);
  CarrinhoResponse atualizarItem(String login, Long produtoId, ItemCarrinhoInput input);
  CarrinhoResponse removerItem(String login, Long produtoId);
  void limpar(String login);
  PedidoResponse finalizar(String login, FinalizarCarrinhoInput input);
}
