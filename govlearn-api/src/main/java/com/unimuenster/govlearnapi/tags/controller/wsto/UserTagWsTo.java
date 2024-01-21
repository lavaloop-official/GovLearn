package com.unimuenster.govlearnapi.tags.controller.wsto;

import lombok.Builder;
@Builder
public record UserTagWsTo(Long id, String name, int rating ,Long categoryID, String category) {
}

