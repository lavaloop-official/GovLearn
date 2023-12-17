package com.unimuenster.govlearnapi.group.controller.wsto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddMemberWsTo {

    private Long userId;
    private Long groupId;
}
