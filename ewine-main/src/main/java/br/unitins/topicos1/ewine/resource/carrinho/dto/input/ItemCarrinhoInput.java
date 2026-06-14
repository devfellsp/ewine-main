package br.unitins.topicos1.ewine.resource.carrinho.dto.input;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ItemCarrinhoInput(
    @NotNull Long produtoId, @Min(value = 1, message = "Quantidade deve ser maior que zero") int quantidade) {}
