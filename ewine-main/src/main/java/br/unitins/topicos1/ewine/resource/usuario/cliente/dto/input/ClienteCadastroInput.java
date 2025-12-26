package br.unitins.topicos1.ewine.resource.usuario.cliente.dto.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ClienteCadastroInput(
        String nome,      // Sem @NotBlank temporariamente
        String login,
        String senha,
        String cpf,
        String email
) {}