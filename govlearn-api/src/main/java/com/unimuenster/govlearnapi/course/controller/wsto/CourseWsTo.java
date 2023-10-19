package com.unimuenster.govlearnapi.course.controller.wsto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CourseWsTo {

    Long id;
    String description;
}
