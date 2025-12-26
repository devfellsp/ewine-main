package br.unitins.topicos1.ewine.resource.produto.dto.filter;

import jakarta.ws.rs.QueryParam;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PaisFilter {

  @QueryParam("nome")
  private String nome;

  @QueryParam("sigla")
  private String sigla;

  public PaisFilter() {}

  public boolean isEmpty() {
    return (nome == null || nome.isBlank()) && (sigla == null || sigla.isBlank());
  }
}
