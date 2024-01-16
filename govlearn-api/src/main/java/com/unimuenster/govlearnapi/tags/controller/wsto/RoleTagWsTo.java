package com.unimuenster.govlearnapi.tags.controller.wsto;

import lombok.Builder;

@Builder
public record RoleTagWsTo(Long ID, Long tagID, int rating, String tagName) {}
