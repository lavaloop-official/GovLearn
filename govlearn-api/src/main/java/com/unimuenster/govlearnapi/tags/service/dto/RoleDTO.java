package com.unimuenster.govlearnapi.tags.service.dto;

import java.util.List;

import com.unimuenster.govlearnapi.core.config.enums.Verantwortungsbereich;

public record RoleDTO(Long id, String name, String description, Verantwortungsbereich verantwortungsbereich, List<RoleTagDTO> roleTagDTOs) {
}

