package br.unitins.topicos1.ewine.model.produto.vinho;

import br.unitins.topicos1.ewine.model.shared.DefaultEntity;
import jakarta.persistence.Entity;
import java.util.Objects;
import lombok.Getter;

@Getter
@Entity
public class Estilo extends DefaultEntity {

  private String nome;

  protected Estilo() {}

  public Estilo(Long id) {
    super(id);
  }

  public Estilo(String nome) {
    this.setNome(nome);
  }

  public void atualizar(String nome) {
    this.setNome(nome);
  }

  private void setNome(String nome) {
    if (nome == null || nome.isBlank()) {
      throw new IllegalArgumentException("Nome do estilo não pode ser nulo ou vazio");
    }

    this.nome = nome;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;

    Estilo estilo = (Estilo) o;
    return Objects.equals(nome, estilo.nome);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(nome);
  }
}
