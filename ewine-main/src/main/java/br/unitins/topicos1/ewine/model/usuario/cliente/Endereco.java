package br.unitins.topicos1.ewine.model.usuario.cliente;

import br.unitins.topicos1.ewine.model.shared.DefaultEntity;
import jakarta.persistence.*;

import br.unitins.topicos1.ewine.model.location.Cidade;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Endereco extends DefaultEntity {

  @Column(nullable = false)
  @NotNull
  private String CEP;

  @Column(nullable = false)
  @NotNull
  private String logradouro;

  @Column(nullable = false)
  @NotNull
  private int numero;

  private String complemento;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private Cidade cidade;

  public Endereco() {}

  public Endereco(Long id) {
    super(id);
  }

}
