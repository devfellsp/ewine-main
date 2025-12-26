package br.unitins.topicos1.ewine.model.produto.vinho;

import br.unitins.topicos1.ewine.model.shared.DefaultEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;

import java.util.Objects;

@Getter
@Entity
public class Ocasiao extends DefaultEntity {

  @Column(name = "nome")
  private String nome;

  protected Ocasiao() {}

  public Ocasiao(Long id) {
    super(id);
  }

  public Ocasiao(String nome) {
    this.setNome(nome);
  }

  public void atualizar(String nome) {
    this.setNome(nome);
  }

  private void setNome(String nome) {
    if (nome == null || nome.isBlank()) {
      throw new IllegalArgumentException("Nome da ocasião não pode ser nulo ou vazio");
    }

    this.nome = nome;
  }


  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;

    Ocasiao ocasiao = (Ocasiao) o;
    return Objects.equals(nome, ocasiao.nome);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(nome);
  }
}
