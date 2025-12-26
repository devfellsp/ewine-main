package br.unitins.topicos1.ewine.model.location;

import br.unitins.topicos1.ewine.model.shared.DefaultEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Cidade extends DefaultEntity {

  private String nome;

  @ManyToOne
  @JoinColumn(name = "estado_id")
  private Estado estado;

    public Cidade() {}

    public Cidade(Long id) {
        super(id);
    }
}
