package com.unimuenster.govlearnapi.group.controller.wsto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import com.unimuenster.govlearnapi.course.controller.wsto.CourseWsTo;

@Data
@Builder
@Getter
@Setter
public class GroupContentWsTo {

    private List<CourseWsTo> courses;
    private Long userId;
    private Long groupId;
}
