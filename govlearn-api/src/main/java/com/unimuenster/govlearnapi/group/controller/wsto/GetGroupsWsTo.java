package com.unimuenster.govlearnapi.group.controller.wsto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class GetGroupsWsTo {

    List<GroupDetailsWsTo> adminGroups;
    List<GroupDetailsWsTo> memberGroups;
}
