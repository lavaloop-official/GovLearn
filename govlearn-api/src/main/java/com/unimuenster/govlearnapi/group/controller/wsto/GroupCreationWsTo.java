package com.unimuenster.govlearnapi.group.controller.wsto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GroupCreationWsTo {

    private List<Long> userIds;
    private String name;
    private List<Long> courseIds;
}
