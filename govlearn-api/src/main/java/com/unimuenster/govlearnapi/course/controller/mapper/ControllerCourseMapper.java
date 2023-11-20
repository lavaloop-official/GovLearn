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
        return new CourseCreationDTO(
                courseCreationWsTo.name(),
                courseCreationWsTo.image(),
                courseCreationWsTo.description(),
                courseCreationWsTo.createdAt(),
                courseCreationWsTo.provider(),
                courseCreationWsTo.instructor(),
                courseCreationWsTo.certificate(),
                courseCreationWsTo.skilllevel(),
                courseCreationWsTo.durationInHours(),
                courseCreationWsTo.format(),
                courseCreationWsTo.startDate(),
                courseCreationWsTo.costFree(),
                courseCreationWsTo.domainSpecific()
        );
    }

    public CourseWsTo map(CourseDTO courseDTO) {
        return CourseWsTo
                .builder()
                .id(courseDTO.id())
                .name(courseDTO.name())
                .image(courseDTO.image())
                .description(courseDTO.description())
                .createdAt(courseDTO.createdAt())
                .provider(courseDTO.provider())
                .instructor(courseDTO.instructor())
                .certificate(courseDTO.certificate())
                .skilllevel(courseDTO.skilllevel())
                .durationInHours(courseDTO.durationInHours())
                .format(courseDTO.format())
                .startDate(courseDTO.startDate())
                .costFree(courseDTO.costFree())
                .domainSpecific(courseDTO.domainSpecific())

                .build();
    }

    public List<CourseWsTo> mapList(List<CourseDTO> courseDTO) {
        return courseDTO
                .stream()
                .map(courseDTO1 -> map(courseDTO1))
                .collect(Collectors.toList());
    }
}
