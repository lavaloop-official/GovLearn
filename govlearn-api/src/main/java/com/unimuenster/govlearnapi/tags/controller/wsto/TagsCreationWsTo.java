package com.unimuenster.govlearnapi.tags.controller.wsto;

import lombok.Builder;
import lombok.Data;

@Builder
public record TagsCreationWsTo(String category, String name) {
}
