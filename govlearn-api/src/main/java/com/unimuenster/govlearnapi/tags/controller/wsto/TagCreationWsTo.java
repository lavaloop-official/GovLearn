package com.unimuenster.govlearnapi.tags.controller.wsto;

import lombok.Builder;

@Builder
public record TagCreationWsTo(String name, String category) {
}
