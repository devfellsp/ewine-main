package br.unitins.topicos1.ewine.resource.produto.dto.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MarcaInput(
    @NotBlank
    @Size(min = 2, max = 100)
    String nome,

    @NotBlank
    @Size(min = 2, max = 100)
    String paisDeOrigem,

    @NotBlank
    String anoFundacao,

    @NotBlank
    @Size(min = 2, max = 100)
    String classificacao) {}
