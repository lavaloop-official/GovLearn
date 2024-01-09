package com.unimuenster.govlearnapi.tags.controller.wsto;

import java.util.List;

import com.unimuenster.govlearnapi.core.config.enums.Verantwortungsbereich;

import lombok.Builder;

@Builder
public record RoleWsTo(Long id, String name, String description, Verantwortungsbereich verantwortungsbereich, List<RoleTagWsTo> roleTagWsTos) {

}
