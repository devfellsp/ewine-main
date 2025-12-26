package br.unitins.topicos1.ewine.model.produto.vinho;

import br.unitins.topicos1.ewine.model.shared.DefaultEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
@Entity
public class Marca extends DefaultEntity {

  @Column(name = "nome")
  private String nome;

  @Column(name = "pais_de_origem")
  private String paisDeOrigem;

  @Column(name = "ano_fundacao")
  private String anoFundacao;

  @Column(name = "classificacao")
  private String classificacao;

  public Marca() {}

  public Marca(Long id) {
    super(id);
  }

  public Marca(String nome, String paisDeOrigem, String anoFundacao, String classificacao) {
    this.setNome(nome);
    this.setPaisDeOrigem(paisDeOrigem);
    this.setAnoFundacao(anoFundacao);
    this.setClassificacao(classificacao);
  }

  public void atualizar(
      String nome, String paisDeOrigem, String anofundacao, String classificacao) {
    this.setNome(nome);
    this.setPaisDeOrigem(paisDeOrigem);
    this.setAnoFundacao(anofundacao);
    this.setClassificacao(classificacao);
  }

  private void setNome(String nome) {
    if (nome == null || nome.isBlank()) {
      throw new IllegalArgumentException("Nome da marca não pode ser nulo ou vazio");
    }

    this.nome = nome;
  }

  private void setPaisDeOrigem(String paisDeOrigem) {
    if (paisDeOrigem == null || paisDeOrigem.isBlank()) {
      throw new IllegalArgumentException("País de origem da marca não pode ser nulo ou vazio");
    }

    this.paisDeOrigem = paisDeOrigem;
  }

  private void setAnoFundacao(String anofundacao) {
    if (anofundacao == null || anofundacao.isBlank()) {
      throw new IllegalArgumentException("Ano de fundação da marca não pode ser nulo ou vazio");
    }

    this.anoFundacao = anofundacao;
  }

  private void setClassificacao(String classificacao) {
    if (classificacao == null || classificacao.isBlank()) {
      throw new IllegalArgumentException("Classificação da marca não pode ser nulo ou vazio");
    }

    this.classificacao = classificacao;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;

    Marca marca = (Marca) o;
    return Objects.equals(nome, marca.nome)
        && Objects.equals(paisDeOrigem, marca.paisDeOrigem)
        && Objects.equals(anoFundacao, marca.anoFundacao)
        && Objects.equals(classificacao, marca.classificacao);
  }

  @Override
  public int hashCode() {
    int result = Objects.hashCode(nome);
    result = 31 * result + Objects.hashCode(paisDeOrigem);
    result = 31 * result + Objects.hashCode(anoFundacao);
    result = 31 * result + Objects.hashCode(classificacao);
    return result;
  }
}
