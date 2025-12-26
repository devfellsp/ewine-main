package br.unitins.topicos1.ewine.model.produto.vinho;

import br.unitins.topicos1.ewine.model.shared.DefaultEntity;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pais extends DefaultEntity {

  private String nome;

  private String sigla;

  public Pais(Long id) {
    super(id);
  }

  public Pais(String nome, String sigla) {
    this.setNome(nome);
    this.setSigla(sigla);
  }

  public void atualizar(String nome, String sigla) {
    this.setNome(nome);
    this.setSigla(sigla);
  }

  private void setNome(String nome) {
    if (nome == null || nome.isBlank()) {
      throw new IllegalArgumentException("Nome não pode ser nulo ou vazio.");
    }

    this.nome = nome;
  }

  private void setSigla(String sigla) {
    if (sigla == null || sigla.isBlank()) {
      throw new IllegalArgumentException("Sigla não pode ser nulo ou vazio.");
    }

    this.sigla = sigla;
  }
}
