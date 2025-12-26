package br.unitins.topicos1.ewine.resource.produto.dto.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EstiloInput(@NotBlank @Size(min = 5, max = 100) String nome) { }
