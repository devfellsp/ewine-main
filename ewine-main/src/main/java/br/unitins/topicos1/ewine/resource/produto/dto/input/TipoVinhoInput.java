package br.unitins.topicos1.ewine.resource.produto.dto.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TipoVinhoInput(
    @NotBlank
    @Size(min = 2, max = 100)
    String nome) {}
