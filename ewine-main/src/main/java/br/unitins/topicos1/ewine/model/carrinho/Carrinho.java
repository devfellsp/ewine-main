package br.unitins.topicos1.ewine.model.carrinho;

import br.unitins.topicos1.ewine.model.produto.Produto;
import br.unitins.topicos1.ewine.model.shared.DefaultEntity;
import br.unitins.topicos1.ewine.model.usuario.cliente.Cliente;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "carrinho")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Carrinho extends DefaultEntity {

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "cliente_id", nullable = false, unique = true)
  private Cliente cliente;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  @JoinColumn(name = "carrinho_id")
  private List<ItemCarrinho> itens = new ArrayList<>();

  public Carrinho(Cliente cliente) {
    if (cliente == null) {
      throw new IllegalArgumentException("Cliente e obrigatorio");
    }
    this.cliente = cliente;
  }

  public void adicionarProduto(Produto produto, int quantidade) {
    ItemCarrinho itemExistente = buscarItem(produto.getId());
    if (itemExistente == null) {
      itens.add(new ItemCarrinho(produto, quantidade));
      return;
    }
    itemExistente.atualizarQuantidade(itemExistente.getQuantidade() + quantidade);
  }

  public void atualizarProduto(Long produtoId, int quantidade) {
    ItemCarrinho item = buscarItemObrigatorio(produtoId);
    item.atualizarQuantidade(quantidade);
  }

  public void removerProduto(Long produtoId) {
    itens.remove(buscarItemObrigatorio(produtoId));
  }

  public void limpar() {
    itens.clear();
  }

  public boolean isVazio() {
    return itens == null || itens.isEmpty();
  }

  public double getTotal() {
    return itens.stream().mapToDouble(ItemCarrinho::getSubtotal).sum();
  }

  public List<ItemCarrinho> getItens() {
    return Collections.unmodifiableList(itens);
  }

  private ItemCarrinho buscarItem(Long produtoId) {
    return itens.stream()
        .filter(item -> item.getProduto().getId().equals(produtoId))
        .findFirst()
        .orElse(null);
  }

  private ItemCarrinho buscarItemObrigatorio(Long produtoId) {
    ItemCarrinho item = buscarItem(produtoId);
    if (item == null) {
      throw new IllegalArgumentException("Produto nao encontrado no carrinho");
    }
    return item;
  }
}
