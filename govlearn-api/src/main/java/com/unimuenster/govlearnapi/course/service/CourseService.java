package com.unimuenster.govlearnapi.course.service;

import com.unimuenster.govlearnapi.course.entity.Course;
import com.unimuenster.govlearnapi.course.exception.NotFoundException;
import com.unimuenster.govlearnapi.course.repository.CourseRepository;
import com.unimuenster.govlearnapi.course.service.dto.CourseCreationDTO;
import com.unimuenster.govlearnapi.course.service.dto.CourseDTO;
import com.unimuenster.govlearnapi.course.service.mapper.ServiceCourseMapper;
import com.unimuenster.govlearnapi.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final ServiceCourseMapper serviceCourseMapper;

    public CourseDTO getCourseById(Long courseId){
        Optional<Course> courseById = courseRepository.findById(courseId);

        if ( courseById.isEmpty() ) {
            throw new NotFoundException();
        }

        CourseDTO map = serviceCourseMapper.map(
                courseById.get()
        );

        return map;
    }
    
    public void createCourse(CourseCreationDTO courseCreationDTO, UserEntity currentUser) {
        
        Course course = Course
                .builder()
                .description(courseCreationDTO.description())
                .creator(currentUser)
                .build();
        
        courseRepository.save(course);
    }

    public List<CourseDTO> getCourses() {

        List<Course> allCourses = courseRepository.findAllCourses();

        return mapCourses(allCourses);
    }

    public List<CourseDTO> getAllCoursesByTagId(Long id) {

        List<Course> allCourses = courseRepository.findAllCoursesByTagId(id);

        return mapCourses(allCourses);
    }

    private List<CourseDTO> mapCourses(List<Course> courses) {
        return courses
                .stream()
                .map(course -> serviceCourseMapper.map(course))
                .collect(Collectors.toList());
    }
}
