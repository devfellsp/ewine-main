package br.unitins.topicos1.ewine.resource.admin.dto;

import jakarta.validation.constraints.NotBlank;

public record AdminAtualizarStatusPedidoInput(@NotBlank String status) {}
