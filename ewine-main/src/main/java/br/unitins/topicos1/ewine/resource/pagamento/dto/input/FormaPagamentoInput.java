package br.unitins.topicos1.ewine.resource.pagamento.dto.input;

import jakarta.validation.constraints.NotBlank;

public record FormaPagamentoInput(
        @NotBlank(message = "Tipo de pagamento é obrigatório")
        String tipo  // "PIX", "BOLETO", "CARTAO_CREDITO"
) {}