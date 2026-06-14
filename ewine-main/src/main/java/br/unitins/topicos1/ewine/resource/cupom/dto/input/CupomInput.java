package br.unitins.topicos1.ewine.resource.cupom.dto.input;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

public record CupomInput(
    @NotBlank String codigo,
    @DecimalMin(value = "0.01") @DecimalMax(value = "100.0") double percentualDesconto,
    LocalDate dataValidade,
    Integer quantidadeMaximaUsos) {}
