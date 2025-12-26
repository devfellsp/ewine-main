package br.unitins.topicos1.ewine.model.produto.vinho;

import br.unitins.topicos1.ewine.model.shared.DefaultEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Entity
public class Safra extends DefaultEntity {

  @Column(name = "ano")
  private Integer ano;

  @Column(name = "descricao")
  private String descricao;

  protected Safra() {}

  public Safra(Integer ano, String descricao) {
    this.setAno(ano);
    this.setDescricao(descricao);
  }

  public void atualizar(Integer ano, String descricao) {
    this.setAno(ano);
    this.setDescricao(descricao);
  }

  private void setAno(Integer ano) {
    if (ano == null || ano < 1800 || ano <= LocalDate.now().getYear()) {
      throw new IllegalArgumentException("Ano inválido");
    }

    this.ano = ano;
  }

  private void setDescricao(String descricao) {
    if (descricao == null || descricao.isBlank()) {
      throw new IllegalArgumentException("Descrição não pode ser nula");
    }

    this.descricao = descricao;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;

    Safra safra = (Safra) o;
    return Objects.equals(ano, safra.ano) && Objects.equals(descricao, safra.descricao);
  }

  @Override
  public int hashCode() {
    int result = Objects.hashCode(ano);
    result = 31 * result + Objects.hashCode(descricao);
    return result;
  }
}
