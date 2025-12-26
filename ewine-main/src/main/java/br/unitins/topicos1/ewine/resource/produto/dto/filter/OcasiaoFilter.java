package br.unitins.topicos1.ewine.resource.produto.dto.filter;

import jakarta.ws.rs.QueryParam;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OcasiaoFilter {

  @QueryParam("nome")
  private String nome;

  public OcasiaoFilter() {}

  public boolean isEmpty() {
    return (nome == null || nome.isBlank());
  }
}
