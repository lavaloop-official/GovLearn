package com.unimuenster.govlearnapi.group.controller.wsto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class GroupAdminWsTo {
    private List<Long> userIds;
    private String name;
    private List<Long> courseIds;
}
