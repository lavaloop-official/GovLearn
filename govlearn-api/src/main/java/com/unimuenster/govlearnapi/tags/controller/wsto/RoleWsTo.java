package com.unimuenster.govlearnapi.tags.controller.wsto;

import com.unimuenster.govlearnapi.core.config.enums.Verantwortungsbereich;

import lombok.Builder;

@Builder
public record RoleWsTo(Long id, String name, String description, Verantwortungsbereich verantwortungsbereich) {

}
