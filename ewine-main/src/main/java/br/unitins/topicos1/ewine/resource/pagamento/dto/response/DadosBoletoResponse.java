package br.unitins.topicos1.ewine.resource.pagamento.dto.response;

public record DadosBoletoResponse(
        String codigoBarras,
        String linhaDigitavel,
        String dataVencimento
) {}