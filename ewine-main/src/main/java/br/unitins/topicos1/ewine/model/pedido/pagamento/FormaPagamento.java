package br.unitins.topicos1.ewine.model.pedido.pagamento;

import br.unitins.topicos1.ewine.model.shared.DefaultEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "forma_pagamento")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FormaPagamento extends DefaultEntity {

  @Column(nullable = false)
  private String nome;

  @Column(name = "permite_parcelamento", nullable = false)
  private boolean permiteParcelamento;

  @Column(nullable = false)
  private boolean ativo;

  public FormaPagamento(String nome, boolean permiteParcelamento) {
    if (nome == null || nome.isBlank()) {
      throw new IllegalArgumentException("Nome não pode ser vazio");
    }

    this.nome = nome.trim();
    this.permiteParcelamento = permiteParcelamento;
    this.ativo = true;
  }

  public void ativar() {
    this.ativo = true;
  }

  public void desativar() {
    this.ativo = false;
  }
}