package br.unitins.topicos1.ewine.resource.shared.dto.input;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record IdInput(
    @NotNull(message = "ID e obrigatorio")
    @Positive(message = "ID deve ser positivo")
    Long id) {}
