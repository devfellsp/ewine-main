package br.unitins.topicos1.ewine.service.impl;

import br.unitins.topicos1.ewine.infrastructure.persistence.CarrinhoRepository;
import br.unitins.topicos1.ewine.infrastructure.persistence.ClienteRepository;
import br.unitins.topicos1.ewine.infrastructure.persistence.ProdutoRepository;
import br.unitins.topicos1.ewine.model.carrinho.Carrinho;
import br.unitins.topicos1.ewine.model.carrinho.ItemCarrinho;
import br.unitins.topicos1.ewine.model.produto.Produto;
import br.unitins.topicos1.ewine.model.usuario.cliente.Cliente;
import br.unitins.topicos1.ewine.resource.carrinho.dto.input.FinalizarCarrinhoInput;
import br.unitins.topicos1.ewine.resource.carrinho.dto.input.ItemCarrinhoInput;
import br.unitins.topicos1.ewine.resource.carrinho.dto.response.CarrinhoResponse;
import br.unitins.topicos1.ewine.resource.carrinho.dto.response.ItemCarrinhoResponse;
import br.unitins.topicos1.ewine.resource.pedido.dto.input.ItemPedidoInput;
import br.unitins.topicos1.ewine.resource.pedido.dto.input.PedidoInput;
import br.unitins.topicos1.ewine.resource.pedido.dto.response.PedidoResponse;
import br.unitins.topicos1.ewine.service.CarrinhoService;
import br.unitins.topicos1.ewine.service.PedidoService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class CarrinhoServiceImpl implements CarrinhoService {

  @Inject CarrinhoRepository carrinhoRepository;
  @Inject ClienteRepository clienteRepository;
  @Inject ProdutoRepository produtoRepository;
  @Inject PedidoService pedidoService;

  @Override
  @Transactional
  public CarrinhoResponse buscar(String login) {
    return toResponse(buscarOuCriar(login));
  }

  @Override
  @Transactional
  public CarrinhoResponse adicionarItem(String login, ItemCarrinhoInput input) {
    Carrinho carrinho = buscarOuCriar(login);
    Produto produto = produtoRepository.findById(input.produtoId());
    carrinho.adicionarProduto(produto, input.quantidade());
    return toResponse(carrinho);
  }

  @Override
  @Transactional
  public CarrinhoResponse atualizarItem(String login, Long produtoId, ItemCarrinhoInput input) {
    Carrinho carrinho = buscarOuCriar(login);
    carrinho.atualizarProduto(produtoId, input.quantidade());
    return toResponse(carrinho);
  }

  @Override
  @Transactional
  public CarrinhoResponse removerItem(String login, Long produtoId) {
    Carrinho carrinho = buscarOuCriar(login);
    carrinho.removerProduto(produtoId);
    return toResponse(carrinho);
  }

  @Override
  @Transactional
  public void limpar(String login) {
    buscarOuCriar(login).limpar();
  }

  @Override
  @Transactional
  public PedidoResponse finalizar(String login, FinalizarCarrinhoInput input) {
    Carrinho carrinho = buscarOuCriar(login);
    if (carrinho.isVazio()) {
      throw new IllegalArgumentException("Carrinho vazio");
    }

    List<ItemPedidoInput> itens =
        carrinho.getItens().stream()
            .map(item -> new ItemPedidoInput(item.getProduto().getId(), item.getQuantidade()))
            .toList();

    PedidoInput pedidoInput =
        new PedidoInput(input.parcelas(), input.formaPagamento(), input.endereco(), itens, input.codigoCupom());
    PedidoResponse response = pedidoService.cadastrar(login, pedidoInput);
    carrinho.limpar();
    return response;
  }

  private Carrinho buscarOuCriar(String login) {
    Cliente cliente = clienteRepository.findByUsuarioLogin(login);
    Carrinho carrinho = carrinhoRepository.findByCliente(cliente);
    if (carrinho == null) {
      carrinho = new Carrinho(cliente);
      carrinhoRepository.persist(carrinho);
    }
    return carrinho;
  }

  private CarrinhoResponse toResponse(Carrinho carrinho) {
    List<ItemCarrinhoResponse> itens =
        carrinho.getItens().stream().map(this::toItemResponse).toList();
    return new CarrinhoResponse(carrinho.getId(), itens, carrinho.getTotal());
  }

  private ItemCarrinhoResponse toItemResponse(ItemCarrinho item) {
    Produto produto = item.getProduto();
    return new ItemCarrinhoResponse(
        produto.getId(),
        produto.getNome(),
        produto.getImagem(),
        produto.getPreco(),
        item.getQuantidade(),
        item.getSubtotal(),
        produto.getQuantidadeEstoque());
  }
}
