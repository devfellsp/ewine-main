package br.unitins.topicos1.ewine.resource.produto.dto.input;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SafraInput(
    @NotNull
    @Min(1900)
    @Max(2100)
    Integer ano,

    @NotBlank
    String descricao) {}
