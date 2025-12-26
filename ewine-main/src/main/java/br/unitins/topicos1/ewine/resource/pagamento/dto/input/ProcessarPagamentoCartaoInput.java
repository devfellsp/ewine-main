package br.unitins.topicos1.ewine.resource.pagamento.dto.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ProcessarPagamentoCartaoInput(
        @NotBlank(message = "Número do cartão é obrigatório")
        @Pattern(regexp = "\\d{16}", message = "Número do cartão deve ter 16 dígitos")
        String numeroCartao,

        @NotBlank(message = "Nome do titular é obrigatório")
        String nomeTitular,

        @NotBlank(message = "Validade é obrigatória")
        @Pattern(regexp = "\\d{2}/\\d{4}", message = "Validade deve estar no formato MM/AAAA")
        String validade,

        @NotBlank(message = "CVV é obrigatório")
        @Pattern(regexp = "\\d{3,4}", message = "CVV deve ter 3 ou 4 dígitos")
        String cvv
) {}