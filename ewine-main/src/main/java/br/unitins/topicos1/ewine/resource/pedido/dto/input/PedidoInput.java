package br.unitins.topicos1.ewine.resource.pedido.dto.input;

import br.unitins.topicos1.ewine.resource.pagamento.dto.input.FormaPagamentoInput;
import br.unitins.topicos1.ewine.resource.shared.dto.input.IdInput;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record PedidoInput(
    @NotNull(message = "Parcelas e obrigatorio")
        @Min(value = 1, message = "Parcelas deve ser no minimo 1")
        Integer parcelas,
    @NotNull(message = "Forma de pagamento e obrigatoria") FormaPagamentoInput formaPagamento,
    @NotNull(message = "Endereco e obrigatorio") IdInput endereco,
    @NotNull(message = "Itens e obrigatorio") List<ItemPedidoInput> itens,
    String codigoCupom) {}
