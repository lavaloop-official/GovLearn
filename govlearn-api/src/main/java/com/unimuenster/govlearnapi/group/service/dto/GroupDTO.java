package com.unimuenster.govlearnapi.group.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GroupDTO {

    List<Integer> members;
    List<Integer> courses;
    Integer creatorId;
    String name;
}
