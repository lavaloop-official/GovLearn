package com.unimuenster.govlearnapi.course.controller.mapper;

import com.unimuenster.govlearnapi.course.controller.wsto.CourseCreationWsTo;
import com.unimuenster.govlearnapi.course.controller.wsto.CourseWsTo;
import com.unimuenster.govlearnapi.course.service.dto.CourseCreationDTO;
import com.unimuenster.govlearnapi.course.service.dto.CourseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ControllerCourseMapper {

    public CourseCreationDTO map(CourseCreationWsTo courseCreationWsTo){
        return new CourseCreationDTO(courseCreationWsTo.description());
    }

    public CourseWsTo map(CourseDTO courseDTO) {
        return CourseWsTo
                .builder()
                .id(courseDTO.id())
                .description(courseDTO.description())
                .build();
    }

    public List<CourseWsTo> mapList(List<CourseDTO> courseDTO) {
        return courseDTO
                .stream()
                .map(courseDTO1 -> map(courseDTO1))
                .collect(Collectors.toList());
    }
}
