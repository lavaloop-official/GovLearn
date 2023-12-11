package com.unimuenster.govlearnapi.tags.controller.wsto;

import lombok.Builder;

@Builder
public record TagWsTo(Long id, String name, Long categoryID, String category) {
}
