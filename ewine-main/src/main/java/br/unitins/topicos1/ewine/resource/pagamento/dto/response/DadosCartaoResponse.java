package br.unitins.topicos1.ewine.resource.pagamento.dto.response;

public record DadosCartaoResponse(
        String numeroMascarado,
        String nomeTitular
) {}