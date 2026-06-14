package br.unitins.topicos1.ewine.resource.usuario.dto.input;

import jakarta.validation.constraints.NotBlank;

public record RedefinirSenhaInput(@NotBlank String token, @NotBlank String novaSenha) {}
