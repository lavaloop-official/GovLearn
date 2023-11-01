package com.unimuenster.govlearnapi.tags.controller.wsto;

import lombok.Builder;
import lombok.Data;

@Builder
public record TagWsTo(String name, String category) {
}
