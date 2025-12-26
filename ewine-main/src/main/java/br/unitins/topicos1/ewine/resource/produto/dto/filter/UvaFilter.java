package br.unitins.topicos1.ewine.resource.produto.dto.filter;

import jakarta.ws.rs.QueryParam;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UvaFilter {

    @QueryParam("nome")
    private String nome;

    public UvaFilter() {}

    public boolean isEmpty() {
        return (nome == null || nome.isBlank());
    }
}