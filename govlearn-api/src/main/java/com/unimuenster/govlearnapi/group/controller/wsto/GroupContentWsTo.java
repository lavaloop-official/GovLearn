package com.unimuenster.govlearnapi.group.controller.wsto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GroupContentWsTo {

    private List<Long> courseIds;
    private Long groupId;
}
