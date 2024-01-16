package com.unimuenster.govlearnapi.group.controller.wsto;

import com.unimuenster.govlearnapi.core.config.enums.Role;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GroupDetailsWsTo {

    private Long groupId;
    private String groupName;
    private String groupDescription;
    private Role role;
}
