package br.unitins.topicos1.ewine.model.cupom;

import br.unitins.topicos1.ewine.model.shared.DefaultEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "cupom")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cupom extends DefaultEntity {

  @Column(unique = true, nullable = false)
  private String codigo;

  @Column(name = "percentual_desconto", nullable = false)
  private double percentualDesconto;

  @Column(name = "data_validade")
  private LocalDate dataValidade;

  @Column(name = "quantidade_maxima_usos")
  private Integer quantidadeMaximaUsos;

  @Column(name = "quantidade_usada", nullable = false)
  private int quantidadeUsada;

  @Column(nullable = false)
  private boolean ativo;

  public Cupom(
      String codigo, double percentualDesconto, LocalDate dataValidade, Integer quantidadeMaximaUsos) {
    atualizar(codigo, percentualDesconto, dataValidade, quantidadeMaximaUsos);
    this.ativo = true;
    this.quantidadeUsada = 0;
  }

  public void atualizar(
      String codigo, double percentualDesconto, LocalDate dataValidade, Integer quantidadeMaximaUsos) {
    if (codigo == null || codigo.isBlank()) {
      throw new IllegalArgumentException("Codigo do cupom e obrigatorio");
    }
    if (percentualDesconto <= 0 || percentualDesconto > 100) {
      throw new IllegalArgumentException("Percentual do cupom deve estar entre 0 e 100");
    }
    if (quantidadeMaximaUsos != null && quantidadeMaximaUsos <= 0) {
      throw new IllegalArgumentException("Quantidade maxima de usos deve ser maior que zero");
    }

    this.codigo = codigo.trim().toUpperCase();
    this.percentualDesconto = percentualDesconto;
    this.dataValidade = dataValidade;
    this.quantidadeMaximaUsos = quantidadeMaximaUsos;
  }

  public void ativar() {
    this.ativo = true;
  }

  public void desativar() {
    this.ativo = false;
  }

  public boolean isValido() {
    boolean dentroDaValidade = dataValidade == null || !dataValidade.isBefore(LocalDate.now());
    boolean possuiUsoDisponivel =
        quantidadeMaximaUsos == null || quantidadeUsada < quantidadeMaximaUsos;
    return ativo && dentroDaValidade && possuiUsoDisponivel;
  }

  public double calcularDesconto(double valor) {
    if (!isValido()) {
      throw new IllegalArgumentException("Cupom invalido ou expirado");
    }
    return valor * (percentualDesconto / 100);
  }

  public void registrarUso() {
    if (!isValido()) {
      throw new IllegalArgumentException("Cupom invalido ou expirado");
    }
    this.quantidadeUsada++;
  }
}
