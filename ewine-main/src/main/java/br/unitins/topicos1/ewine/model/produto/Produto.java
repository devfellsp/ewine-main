package br.unitins.topicos1.ewine.model.produto;

import br.unitins.topicos1.ewine.model.shared.DefaultEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_produto")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Produto extends DefaultEntity {

  @Column(name = "data_criacao", nullable = false, updatable = false)
  private LocalDateTime dataCriacao;

  @Column(unique = true, nullable = false)
  private String sku;

  @Column(nullable = false)
  private String nome;

  @Column(length = 200)
  private String descricao;

  @Column(length = 500)
  private String imagem;

  @Column(nullable = false)
  private boolean ativo;

  @Column(nullable = false)
  private double preco;

  @Embedded private Estoque estoque;

  protected Produto(
      String sku, String nome, String descricao, double preco, int quantidadeEstoque) {
    if (sku == null || sku.isBlank()) {
      throw new IllegalArgumentException("SKU é obrigatório");
    }

    if (nome == null || nome.isBlank()) {
      throw new IllegalArgumentException("Nome é obrigatório");
    }

    if (preco <= 0) {
      throw new IllegalArgumentException("Preço deve ser maior que zero");
    }

    this.dataCriacao = LocalDateTime.now();
    this.sku = sku.trim();
    this.nome = nome.trim();
    this.descricao = descricao;
    this.preco = preco;
    this.estoque = new Estoque(quantidadeEstoque);
    this.ativo = true;
  }

  public void atualizar(String nome, String descricao) {
    if (nome == null || nome.isBlank()) {
      throw new IllegalArgumentException("Nome é obrigatório");
    }

    this.nome = nome.trim();
    this.descricao = descricao;
  }

  public void atualizarPreco(double novoPreco) {
    if (novoPreco <= 0) {
      throw new IllegalArgumentException("Preço deve ser maior que zero");
    }

    this.preco = novoPreco;
  }

  public void atualizarEstoque(int novaQuantidade) {
    this.estoque = new Estoque(novaQuantidade);
  }

  public void setImagem(String imagem) {
    this.imagem = imagem;
  }

  public void altenarAtivo() {
    this.ativo = !this.ativo;
  }


  public boolean temEstoque(int quantidade) {
    return ativo && estoque.temDisponivel(quantidade);
  }

  public void diminuirEstoque(int quantidade) {
    this.estoque = estoque.reduzir(quantidade);
  }

  public void aumentarEstoque(int quantidade) {
    this.estoque = estoque.aumentar(quantidade);
  }

  public int getQuantidadeEstoque() {
    return estoque.getQuantidade();
  }

  public boolean disponivel() {
    return ativo && estoque != null && !estoque.isVazio();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Produto produto = (Produto) o;
    return Objects.equals(sku, produto.sku);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(sku);
  }
}
