package br.unitins.topicos1.ewine.resource. pedido. dto. input;

import br.unitins.topicos1.ewine.resource.pagamento.dto.input.FormaPagamentoInput;
import br.unitins.topicos1.ewine.resource.shared.dto.input.IdInput;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PedidoInput(
        @NotNull(message = "Parcelas é obrigatório")
        @Min(value = 1, message = "Parcelas deve ser no mínimo 1")
        Integer parcelas,

        @NotNull(message = "Forma de pagamento é obrigatória")
        FormaPagamentoInput formaPagamento,

        @NotNull(message = "Endereço é obrigatório")
        IdInput endereco,

        @NotNull(message = "Itens é obrigatório")
        List<ItemPedidoInput> itens
) {}