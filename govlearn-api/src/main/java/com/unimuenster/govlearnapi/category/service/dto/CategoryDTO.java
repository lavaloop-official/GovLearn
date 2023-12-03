package com.unimuenster.govlearnapi.category.service.dto;

import lombok.Builder;

@Builder
public record CategoryDTO(Long id, String name) {
}
