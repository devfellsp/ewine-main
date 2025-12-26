package br.unitins.topicos1.ewine.model.produto.vinho;

import br.unitins.topicos1.ewine.model.shared.DefaultEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;

import java.util.Objects;

/** TODO: TipoVinho deve ser unico, e para isso deve ser feito uma validação na camada de serviço */
@Getter
@Entity
@Table(name = "tipo_vinho")
public class TipoVinho extends DefaultEntity {

  private String nome;


  public TipoVinho() {}

  public TipoVinho(Long id) {
    super(id);
  }

  public TipoVinho(String nome) {
    this.setNome(nome);
  }

  public void atualizar(String nome) {
    this.setNome(nome);
  }

  private void setNome(String nome) {
    if (nome == null || nome.isBlank()) {
      throw new IllegalArgumentException("Nome do tipo de vinho não pode ser nulo ou vazio");
    }

    this.nome = nome;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;

    TipoVinho tipoVinho = (TipoVinho) o;
    return Objects.equals(nome, tipoVinho.nome);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(nome);
  }
}
