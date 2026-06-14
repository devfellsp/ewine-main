package br.unitins.topicos1.ewine.resource.carrinho.dto.input;

import br.unitins.topicos1.ewine.resource.pagamento.dto.input.FormaPagamentoInput;
import br.unitins.topicos1.ewine.resource.shared.dto.input.IdInput;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record FinalizarCarrinhoInput(
    @NotNull @Min(1) Integer parcelas,
    @NotNull FormaPagamentoInput formaPagamento,
    @NotNull IdInput endereco,
    String codigoCupom) {}
