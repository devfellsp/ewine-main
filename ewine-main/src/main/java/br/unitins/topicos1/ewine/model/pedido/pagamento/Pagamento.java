package br.unitins.topicos1.ewine.model.pedido.pagamento;

import br.unitins.topicos1.ewine.model.shared.DefaultEntity;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "pagamento")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pagamento extends DefaultEntity {

  @Column(name = "valor_total", nullable = false, updatable = false)
  private double valorTotal;

  @Column(nullable = false, updatable = false)
  private int parcelas;

  @Column(name = "valor_parcela", nullable = false, updatable = false)
  private double valorParcela;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "forma_pagamento_id", nullable = false, updatable = false)
  private FormaPagamento formaPagamento;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private PagamentoStatus status;

  @Column(name = "transacao_externa_id")
  private String transacaoExternaId;

  @Column(name = "data_criacao", nullable = false, updatable = false)
  private LocalDateTime dataCriacao;

  @Column(name = "data_confirmacao")
  private LocalDateTime dataConfirmacao;

  private Pagamento(double valorTotal, int parcelas, FormaPagamento formaPagamento) {
    if (valorTotal <= 0) {
      throw new IllegalArgumentException("Valor deve ser maior que zero");
    }

    if (formaPagamento == null || !formaPagamento.isAtivo()) {
      throw new IllegalArgumentException("Forma de pagamento inválida ou inativa");
    }

    this.valorTotal = valorTotal;
    this.parcelas = parcelas;
    this.valorParcela = valorTotal / parcelas;
    this.formaPagamento = formaPagamento;
    this.status = PagamentoStatus.PENDENTE;
    this.dataCriacao = LocalDateTime.now();
  }

  public static Pagamento aVista(double valorTotal, FormaPagamento formaPagamento) {
    return new Pagamento(valorTotal, 1, formaPagamento);
  }

  public static Pagamento parcelado(
      double valorTotal, int parcelas, FormaPagamento formaPagamento) {
    if (parcelas < 2 || parcelas > 12) {
      throw new IllegalArgumentException("Parcelas deve ser entre 2 e 12");
    }

    if (!formaPagamento.isPermiteParcelamento()) {
      throw new IllegalArgumentException("Forma de pagamento não permite parcelamento");
    }

    return new Pagamento(valorTotal, parcelas, formaPagamento);
  }

  public void confirmar(String transacaoExternaId) {
    if (this.status != PagamentoStatus.PENDENTE) {
      throw new IllegalStateException("Pagamento não está pendente");
    }

    this.transacaoExternaId = transacaoExternaId;
    this.status = PagamentoStatus.CONFIRMADO;
    this.dataConfirmacao = LocalDateTime.now();
  }

  public void recusar() {
    if (this.status != PagamentoStatus.PENDENTE) {
      throw new IllegalStateException("Pagamento não está pendente");
    }

    this.status = PagamentoStatus.RECUSADO;
  }

  public boolean isConfirmado() {
    return status == PagamentoStatus.CONFIRMADO;
  }

  public boolean isPendente() {
    return status == PagamentoStatus.PENDENTE;
  }

  public boolean isParcelado() {
    return parcelas > 1;
  }
}
