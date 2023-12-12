package com.unimuenster.govlearnapi.group.service.dto;

import com.unimuenster.govlearnapi.course.service.dto.CourseDTO;
import com.unimuenster.govlearnapi.user.service.dto.UserDTO;
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
