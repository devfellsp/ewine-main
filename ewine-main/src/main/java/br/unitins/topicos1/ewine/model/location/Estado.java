package br.unitins.topicos1.ewine.model.location;

import java.util.List;

import br.unitins.topicos1.ewine.model.shared.DefaultEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Estado extends DefaultEntity {

  private String nome;

  private String sigla;

  private Regiao regiao;

  @OneToMany(mappedBy = "estado", orphanRemoval = true)
  private List<Cidade> cidades;
}
