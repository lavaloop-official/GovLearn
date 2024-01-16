package com.unimuenster.govlearnapi.group.controller.wsto;

import com.unimuenster.govlearnapi.core.config.enums.Role;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MemberDetailsWsTo {

    private Long memberId;
    private String email;
    private String name;
    private String memberSince;
    private Role role;
}
