package br.unitins.topicos1.ewine.resource.produto.dto.input;

import br.unitins.topicos1.ewine.resource.shared.dto.input.IdInput;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.util.List;

public record VinhoInput(
    @NotBlank
    @Size(min = 2, max = 100)
    String sku,

    @Positive
    double preco,

    @PositiveOrZero
    int quantEstoque,

    @NotBlank(message = "Nome do vinho e obrigatorio")
    @Size(min = 2, max = 100)
    String nome,

    @NotBlank
    @Size(min = 10, max = 1000, message = "Descricao deve ter entre 10 e 1000 caracteres")
    String descricao,

    @Positive(message = "Teor alcoolico deve ser positivo")
    Double teorAlcoolico,

    @Positive(message = "Volume deve ser positivo")
    Integer volume,

    @NotNull(message = "Tipo de vinho e obrigatorio")
    @Valid
    IdInput tipoVinho,

    @NotNull(message = "Pais de origem e obrigatorio")
    @Valid
    IdInput pais,

    @NotNull
    @Valid
    IdInput safra,

    @NotNull(message = "Marca e obrigatoria")
    @Valid
    IdInput marca,

    @Valid
    IdInput estilo,

    @Valid
    IdInput ocasiao,

    @NotNull(message = "Uvas sao obrigatorias")
    List<@Valid IdInput> uvas) {}
