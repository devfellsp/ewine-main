package br.unitins.topicos1.ewine.model.produto;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class Estoque {

  @Column(name = "estoque_quantidade", nullable = false)
  private int quantidade;

  protected Estoque() {
    this.quantidade = 0;
  }

  public Estoque(int quantidade) {
    if (quantidade < 0) {
      throw new IllegalArgumentException("Quantidade não pode ser negativa");
    }

    this.quantidade = quantidade;
  }

  public boolean temDisponivel(int quantidadeSolicitada) {
    return quantidadeSolicitada > 0 && quantidadeSolicitada <= this.quantidade;
  }

  public Estoque reduzir(int quantidadeReducao) {
    if (quantidadeReducao <= 0) {
      throw new IllegalArgumentException("Quantidade para redução deve ser maior que zero");
    }

    if (quantidadeReducao > this.quantidade) {
      throw new IllegalArgumentException("Quantidade para redução excede o estoque disponível");
    }

    return new Estoque(this.quantidade - quantidadeReducao);
  }

  public Estoque aumentar(int quantidadeAumento) {
    if (quantidadeAumento <= 0) {
      throw new IllegalArgumentException("Quantidade para aumento deve ser maior que zero");
    }

    return new Estoque(this.quantidade + quantidadeAumento);
  }

  public boolean isVazio() {
    return quantidade == 0;
  }
}