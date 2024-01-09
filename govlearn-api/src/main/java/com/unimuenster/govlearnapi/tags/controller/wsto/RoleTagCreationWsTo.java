package com.unimuenster.govlearnapi.tags.controller.wsto;

import lombok.Builder;

@Builder
public record RoleTagCreationWsTo(Long tagID, int rating) {}
