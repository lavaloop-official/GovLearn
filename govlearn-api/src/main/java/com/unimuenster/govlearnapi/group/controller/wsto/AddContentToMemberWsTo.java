package com.unimuenster.govlearnapi.group.controller.wsto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AddContentToMemberWsTo {
    private Long memberId;
    private Long courseId;
}
