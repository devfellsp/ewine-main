package br.unitins.topicos1.ewine.resource.produto.dto.input;

import br.unitins.topicos1.ewine.resource.shared.dto.input.IdInput;
import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
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

    @NotNull(message = "Preco e obrigatorio")
    @Positive
    Double preco,

    @JsonAlias("estoqueQuantidade")
    @NotNull(message = "Quantidade em estoque e obrigatoria")
    @PositiveOrZero
    Integer quantEstoque,

    @NotBlank(message = "Nome do vinho e obrigatorio")
    @Size(min = 2, max = 100)
    String nome,

    @NotBlank
    @Size(min = 10, max = 1000, message = "Descricao deve ter entre 10 e 1000 caracteres")
    String descricao,

    @NotNull(message = "Teor alcoolico e obrigatorio")
    @Positive(message = "Teor alcoolico deve ser positivo")
    Double teorAlcoolico,

    @NotNull(message = "Volume e obrigatorio")
    @Positive(message = "Volume deve ser positivo")
    Integer volume,

    @NotNull(message = "Tipo de vinho e obrigatorio")
    @Valid
    IdInput tipoVinho,

    @JsonAlias("paisDeOrigem")
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
    @NotEmpty(message = "Informe ao menos uma uva")
    List<@Valid @NotNull(message = "Uva e obrigatoria") IdInput> uvas) {}
