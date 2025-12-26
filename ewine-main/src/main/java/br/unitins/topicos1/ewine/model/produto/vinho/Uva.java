package br.unitins.topicos1.ewine.model.produto.vinho;

import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import br.unitins.topicos1.ewine.model.shared.DefaultEntity;
import jakarta.persistence.Entity;

import java.util.Objects;


@Getter
@Entity
public class Uva extends DefaultEntity {

  private String nome;

  protected Uva() {}

  public Uva(Long id) {
    super(id);
  }

  public Uva(String nome) {
    this.setNome(nome);
  }

  public void atualizar(String nome) {
    this.setNome(nome);
  }

  private void setNome(String nome) {
    if (nome == null || nome.isBlank()) {
      throw new IllegalArgumentException("Nome da uva não pode ser nulo ou vazio");
    }

    this.nome = nome;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;

    Uva uva = (Uva) o;
    return Objects.equals(nome, uva.nome);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(nome);
  }
}
