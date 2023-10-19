package com.unimuenster.govlearnapi.course.service.mapper;

import com.unimuenster.govlearnapi.course.entity.Course;
import com.unimuenster.govlearnapi.course.service.dto.CourseDTO;
import org.springframework.stereotype.Service;

@Service
public class ServiceCourseMapper {

    public CourseDTO map(Course course) {
        return new CourseDTO(course.getId(), course.getDescription());
    }
}
