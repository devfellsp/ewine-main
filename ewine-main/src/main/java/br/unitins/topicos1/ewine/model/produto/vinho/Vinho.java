package br.unitins.topicos1.ewine.model.produto.vinho;

import br.unitins.topicos1.ewine.model.produto.Produto;
import jakarta.persistence.*;

import java.util.List;
import lombok.Getter;

@Entity
@Getter
@DiscriminatorValue("VINHO")
public class Vinho extends Produto {

  @Column(name = "teor_alcoolico", nullable = false)
  private double teorAlcoolico;

  @Column(name = "volume", nullable = false)
  private int volume;

  @ManyToOne
  @JoinColumn(name = "pais_id")
  private Pais paisDeOrigem;

  @ManyToOne
  @JoinColumn(name = "tipo_vinho_id")
  private TipoVinho tipoVinho;

  @ManyToOne
  @JoinColumn(name = "marca_id")
  private Marca marca;

  @ManyToOne
  @JoinColumn(name = "safra_id")
  private Safra safra;

  @ManyToMany
  @JoinTable(
      name = "vinho_uva",
      joinColumns = @JoinColumn(name = "vinho_id"),
      inverseJoinColumns = @JoinColumn(name = "uva_id"))
  private List<Uva> uvas;

  @ManyToOne
  @JoinColumn(name = "estilo_id")
  private Estilo estilo;

  @ManyToOne
  @JoinColumn(name = "ocasiao_id")
  private Ocasiao ocasiao;

  protected Vinho() {}

  public Vinho(
      String sku,
      double preco,
      int estoque,
      String nome,
      String descricao,
      double teorAlcoolico,
      int volume,
      Pais pais,
      TipoVinho tipoVinho,
      Marca marca,
      Safra safra,
      List<Uva> uvas) {
    super(sku, nome, descricao, preco, estoque);

    this.setTeorAlcoolico(teorAlcoolico);
    this.setVolume(volume);
    this.setPaisDeOrigem(pais);
    this.setTipoVinho(tipoVinho);
    this.setMarca(marca);
    this.setSafra(safra);
    this.setUvas(uvas);
  }

  public void atualizar(
      String nome,
      String descricao,
      double teorAlcoolico,
      int volume,
      Pais pais,
      TipoVinho tipoVinho,
      Marca marca,
      Safra safra,
      List<Uva> uvas,
      Estilo estilo,
      Ocasiao ocasiao) {
    super.atualizar(nome, descricao);

    this.setTeorAlcoolico(teorAlcoolico);
    this.setVolume(volume);
    this.setPaisDeOrigem(pais);
    this.setTipoVinho(tipoVinho);
    this.setMarca(marca);
    this.setSafra(safra);
    this.setUvas(uvas);
    this.setEstilo(estilo);
    this.setOcasiao(ocasiao);
  }

  public void setEstilo(Estilo estilo) {
    if (estilo == null) {
      return;
    }

    this.estilo = estilo;
  }

  public void setOcasiao(Ocasiao ocasiao) {
    if (ocasiao == null) {
      return;
    }

    this.ocasiao = ocasiao;
  }

  private void setTeorAlcoolico(double teorAlcoolico) {
    if (teorAlcoolico <= 0) {
      throw new IllegalArgumentException("Teor alcoólico deve ser maior que zero");
    }

    this.teorAlcoolico = teorAlcoolico;
  }

  private void setVolume(int volume) {
    if (volume <= 0) {
      throw new IllegalArgumentException("Volume deve ser maior que zero");
    }

    this.volume = volume;
  }

  private void setPaisDeOrigem(Pais paisDeOrigem) {
    if (paisDeOrigem == null) {
      throw new IllegalArgumentException("País de origem é obrigatório");
    }

    this.paisDeOrigem = paisDeOrigem;
  }

  private void setTipoVinho(TipoVinho tipoVinho) {
    if (tipoVinho == null) {
      throw new IllegalArgumentException("Tipo de vinho é obrigatório");
    }

    this.tipoVinho = tipoVinho;
  }

  private void setMarca(Marca marca) {
    if (marca == null) {
      throw new IllegalArgumentException("Marca é obrigatória");
    }

    this.marca = marca;
  }

  private void setSafra(Safra safra) {
    if (safra == null) {
      throw new IllegalArgumentException("Safra é obrigatória");
    }

    this.safra = safra;
  }

  private void setUvas(List<Uva> uvas) {
    if (uvas == null || uvas.isEmpty()) {
      throw new IllegalArgumentException("Lista de uvas é obrigatória");
    }

    this.uvas = uvas;
  }
}
