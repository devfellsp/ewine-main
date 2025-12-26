package br.unitins.topicos1.ewine.resource.produto.dto.filter;

import jakarta.ws.rs.QueryParam;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SafraFilter {

  @QueryParam("ano")
  private String ano;

  @QueryParam("descricao")
  private String descricao;

  public SafraFilter() {}

  public boolean isEmpty() {
    return (ano == null || ano.isBlank()) && (descricao == null || descricao.isBlank());
  }
}
