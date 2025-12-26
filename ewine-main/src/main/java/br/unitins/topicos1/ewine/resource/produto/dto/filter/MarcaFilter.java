package br.unitins.topicos1.ewine.resource.produto.dto.filter;

import jakarta.ws.rs.QueryParam;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MarcaFilter {

  @QueryParam("nome")
  private String nome;

  @QueryParam("pais")
  private String paisDeOrigem;

  @QueryParam("ano")
  private String anofundacao;

  @QueryParam("classificacao")
  private String classificacao;

  public MarcaFilter() {}

  public boolean isEmpty() {
    return (nome == null || nome.isBlank())
        && (paisDeOrigem == null || paisDeOrigem.isBlank())
        && (anofundacao == null || anofundacao.isBlank())
        && (classificacao == null || classificacao.isBlank());
  }
}
