package com.unimuenster.govlearnapi.group.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GroupDTO {

    List<Long> members;
    List<Long> courses;
    Long creatorId;
    String name;
}
