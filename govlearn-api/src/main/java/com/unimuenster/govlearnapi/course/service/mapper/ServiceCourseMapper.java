package com.unimuenster.govlearnapi.course.service.mapper;

import com.unimuenster.govlearnapi.course.entity.Course;
import com.unimuenster.govlearnapi.course.service.dto.CourseDTO;
import org.springframework.stereotype.Service;

@Service
public class ServiceCourseMapper {

    public CourseDTO map(Course course) {
        return new CourseDTO(
                course.getId(),
                course.getName(),
                course.getImage(),
                course.getDescription(),
                course.getCreatedAt(),
                course.getProvider(),
                course.getInstructor(),
                course.getCertificate(),
                course.getSkilllevel(),
                course.getDuration(),
                course.getFormat(),
                course.getStartDate(),
                course.getCostFree(),
                course.getDomainSpecific(),
                course.getLink()
        );
    }

    public Course map(CourseDTO courseDTO) {
        return Course
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
                .duration(courseDTO.durationInHours())
                .format(courseDTO.format())
                .startDate(courseDTO.startDate())
                .costFree(courseDTO.costFree())
                .domainSpecific(courseDTO.domainSpecific())
                .link(courseDTO.link())
                .build();
    }
}
