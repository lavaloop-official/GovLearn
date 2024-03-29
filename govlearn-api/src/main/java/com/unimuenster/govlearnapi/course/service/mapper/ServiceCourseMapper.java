package com.unimuenster.govlearnapi.course.service.mapper;

import com.unimuenster.govlearnapi.course.controller.wsto.CourseUpdateWsTo;
import com.unimuenster.govlearnapi.course.controller.wsto.CourseWsTo;
import com.unimuenster.govlearnapi.course.entity.Course;
import com.unimuenster.govlearnapi.course.service.dto.CourseCreationDTO;
import com.unimuenster.govlearnapi.course.service.dto.CourseDTO;
import com.unimuenster.govlearnapi.course.service.dto.CourseUpdateDTO;
import com.unimuenster.govlearnapi.course.controller.mapper.ControllerCourseMapper;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.unimuenster.govlearnapi.user.entity.UserEntity;
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
                course.getDurationInMinutes(),
                course.getFormat(),
                course.getStartDate(),
                course.getCostFree(),
                course.getDomainSpecific(),
                course.getLink()
        );
    }

    public CourseDTO map(CourseWsTo course) {
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
                ControllerCourseMapper.convertToMinutes(course.getDurationInHours()),
                course.getFormat(),
                course.getStartDate(),
                course.getCostFree(),
                course.getDomainSpecific(),
                course.getLink()
        );
    }

    public List<CourseDTO> mapListCourse(List<Course> courses) {
        return courses.stream().map(course -> map(course)).collect(Collectors.toList());
    }

    public List<CourseDTO> mapListCourseWsTo(List<CourseWsTo> courses) {
        return courses.stream().map(course -> map(course)).collect(Collectors.toList());
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
                .durationInMinutes(courseDTO.durationInMinutes())
                .format(courseDTO.format())
                .startDate(courseDTO.startDate())
                .costFree(courseDTO.costFree())
                .domainSpecific(courseDTO.domainSpecific())
                .link(courseDTO.link())
                .build();
    }

    public CourseUpdateDTO map(CourseUpdateWsTo course) {
        return new CourseUpdateDTO(
                course.id(),
                course.name(),
                course.image(),
                course.description(),
                course.provider(),
                course.instructor(),
                course.certificate(),
                course.skilllevel(),
                ControllerCourseMapper.convertToMinutes(course.durationInHours()),
                course.format(),
                course.startDate(),
                course.costFree(),
                course.domainSpecific(),
                course.link()
        );
    }

    public Course mapToCourse(CourseCreationDTO courseCreationDTO, UserEntity user) {
        return Course
                .builder()
                .name(courseCreationDTO.name())
                .image(courseCreationDTO.image())
                .description(courseCreationDTO.description())
                .createdAt(courseCreationDTO.createdAt())
                .provider(courseCreationDTO.provider())
                .instructor(courseCreationDTO.instructor())
                .certificate(courseCreationDTO.certificate())
                .skilllevel(courseCreationDTO.skilllevel())
                .durationInMinutes(courseCreationDTO.durationInMinutes())
                .format(courseCreationDTO.format())
                .startDate(courseCreationDTO.startDate())
                .costFree(courseCreationDTO.costFree())
                .domainSpecific(courseCreationDTO.domainSpecific())
                .creator(user)
                .link(courseCreationDTO.link())
                .build();
    }
}
