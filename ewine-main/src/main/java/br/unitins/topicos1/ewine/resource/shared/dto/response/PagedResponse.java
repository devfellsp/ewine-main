package br.unitins.topicos1.ewine.resource.shared.dto.response;

import java.util.List;

public record PagedResponse<T>(List<T> content, long totalElements, int page, int size) {}
