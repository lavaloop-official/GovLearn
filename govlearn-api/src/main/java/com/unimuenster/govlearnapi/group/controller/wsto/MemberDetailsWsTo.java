package com.unimuenster.govlearnapi.group.controller.wsto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDetailsWsTo {

    private Long memberId;
    private String email;
    private String name;
    private String memberSince;
}
