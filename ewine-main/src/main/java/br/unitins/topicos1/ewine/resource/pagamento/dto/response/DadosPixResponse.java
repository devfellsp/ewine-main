package br.unitins.topicos1.ewine.resource.pagamento.dto. response;

public record DadosPixResponse(
        String codigoPix,
        String qrCodeBase64,
        String validade
) {}