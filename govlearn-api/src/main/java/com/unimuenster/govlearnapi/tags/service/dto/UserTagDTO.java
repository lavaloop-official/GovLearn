package com.unimuenster.govlearnapi.tags.service.dto;

import lombok.Builder;

@Builder
public record UserTagDTO(Long id, String name, int rating ,Long categoryID, String category) {
}
