package br.unitins.topicos1.ewine.resource.produto.dto.input;

import br.unitins.topicos1.ewine.resource.shared.dto.input.IdInput;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.List;

public record VinhoInput(

        @NotBlank
        String sku,

        @NotNull
        double preco,

        @NotNull
        int quantEstoque,

        @NotBlank(message = "Nome do vinho é obrigatório")
        String nome,

        @Size(max = 1000, message = "Descrição não pode exceder 1000 caracteres")
        String descricao,

        @Positive(message = "Teor alcoólico deve ser positivo")
        Double teorAlcoolico,

        @Positive(message = "Volume deve ser positivo")
        Integer volume,

        @NotNull(message = "Tipo de vinho é obrigatório")
        IdInput tipoVinho,

        @NotNull(message = "País de origem é obrigatório")
        IdInput pais,

        @NotNull
        IdInput safra,

        @NotNull(message = "Marca é obrigatória")
        IdInput marca,

        IdInput estilo,

        IdInput ocasiao,

        @NotNull(message = "uvas é obrigatória")
        List<IdInput> uvas) {}
