package br.unitins.topicos1.ewine.resource.admin.dto;

import java.util.List;

public record AdminPagedResponse<T>(List<T> itens, long total, int pagina, int tamanho) {}
