package com.unimuenster.govlearnapi.tags.service.dto;

import com.unimuenster.govlearnapi.core.config.enums.Verantwortungsbereich;

public record RoleCreationDTO(String name, String description, Verantwortungsbereich verantwortungsbereich) {}