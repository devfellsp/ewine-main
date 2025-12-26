package br.unitins.topicos1.ewine.model.pedido;

import br.unitins.topicos1.ewine.model.shared.DefaultEntity;
import br.unitins.topicos1.ewine.model.produto.Produto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "item_pedido")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemPedido extends DefaultEntity {

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  @JoinColumn(name = "produto_id", nullable = false)
  private Produto produto;

  @Column(nullable = false, updatable = false)
  private int quantidade;

  @Column(name = "preco_unitario", nullable = false, updatable = false)
  private double precoUnitario;

  @Column(name = "nome_produto", nullable = false, updatable = false)
  private String nomeProduto;

  public ItemPedido(Produto produto, int quantidade) {
    if (produto == null) {
      throw new IllegalArgumentException("Produto não pode ser nulo");
    }

    if (quantidade <= 0) {
      throw new IllegalArgumentException("Quantidade deve ser maior que zero");
    }

    if (!produto.temEstoque(quantidade)) {
      throw new IllegalArgumentException("Estoque insuficiente para: " + produto.getNome());
    }

    this.produto = produto;
    this.quantidade = quantidade;
    this.precoUnitario = produto.getPreco();
    this.nomeProduto = produto.getNome();
  }

  public double getSubtotal() {
    return precoUnitario * quantidade;
  }
}
