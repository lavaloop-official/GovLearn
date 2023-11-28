package com.unimuenster.govlearnapi.course.service;

import com.unimuenster.govlearnapi.course.entity.Course;
import com.unimuenster.govlearnapi.course.exception.NotFoundException;
import com.unimuenster.govlearnapi.course.repository.CourseRepository;
import com.unimuenster.govlearnapi.course.service.dto.CourseCreationDTO;
import com.unimuenster.govlearnapi.course.service.dto.CourseDTO;
import com.unimuenster.govlearnapi.course.service.mapper.ServiceCourseMapper;
import com.unimuenster.govlearnapi.recommendation.service.RecommendationService;
import com.unimuenster.govlearnapi.tags.service.dto.TagDTO;
import com.unimuenster.govlearnapi.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final ServiceCourseMapper serviceCourseMapper;
    private final RecommendationService recommendationService;

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
                .name(courseCreationDTO.name())
                .image(courseCreationDTO.image())
                .description(courseCreationDTO.description())
                .createdAt(courseCreationDTO.createdAt())
                .provider(courseCreationDTO.provider())
                .instructor(courseCreationDTO.instructor())
                .certificate(courseCreationDTO.certificate())
                .skilllevel(courseCreationDTO.skilllevel())
                .duration(courseCreationDTO.durationInHours())
                .format(courseCreationDTO.format())
                .startDate(courseCreationDTO.startDate())
                .costFree(courseCreationDTO.costFree())
                .domainSpecific(courseCreationDTO.domainSpecific())
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

    public Course getCourseEntityById(Long courseId){
        Optional<Course> course = courseRepository.findById(courseId);

        if ( course.isEmpty() ) {
            throw new NotFoundException();
        }

        Course map = Course
                .builder()
                .description(course.get().getDescription())
                .creator(course.get().getCreator())
                .createdAt(course.get().getCreatedAt())
                .build();

        return map;
    }

    public List<Course> getSimiliarCourses(Long id, List<TagDTO> allTags) {
        List<Course> allCoursesExceptSelf = courseRepository.findAllCourses().stream().filter(course -> !Objects.equals(course.getId(), id)).collect(Collectors.toList());
        Optional<Course> courseById = courseRepository.findById(id);

        if ( courseById.isEmpty() ) {
            throw new NotFoundException();
        }

        double[] courseTagVector = recommendationService.getCourseTagBinaryVector(courseById.get(), allTags);
        List<Object[]> coursesWithSimilarity = recommendationService.compareToCourses(courseTagVector, allTags, allCoursesExceptSelf);

        return this.mostSimilarCourses(coursesWithSimilarity);
    }

    private List<Course> mostSimilarCourses(List<Object[]> coursesWithSimilarity) {
        coursesWithSimilarity.sort(Comparator.comparing(o ->  (Comparable)o[1]));
        Collections.reverse(coursesWithSimilarity);

        return coursesWithSimilarity
                .stream()
                .map(object -> (Course)object[0])
                .limit(5)
                .collect(Collectors.toList());
        }
    }
