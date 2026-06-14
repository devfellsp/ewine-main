package br.unitins.topicos1.ewine.model.carrinho;

import br.unitins.topicos1.ewine.model.produto.Produto;
import br.unitins.topicos1.ewine.model.shared.DefaultEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "item_carrinho")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemCarrinho extends DefaultEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  private Produto produto;

  @Column(nullable = false)
  private int quantidade;

  public ItemCarrinho(Produto produto, int quantidade) {
    if (produto == null) {
      throw new IllegalArgumentException("Produto e obrigatorio");
    }
    this.produto = produto;
    atualizarQuantidade(quantidade);
  }

  public void atualizarQuantidade(int quantidade) {
    if (quantidade <= 0) {
      throw new IllegalArgumentException("Quantidade deve ser maior que zero");
    }
    if (!produto.temEstoque(quantidade)) {
      throw new IllegalArgumentException("Estoque insuficiente para: " + produto.getNome());
    }
    this.quantidade = quantidade;
  }

  public double getSubtotal() {
    return produto.getPreco() * quantidade;
  }
}
