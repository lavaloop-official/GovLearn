package com.unimuenster.govlearnapi.course.service;

import com.unimuenster.govlearnapi.course.controller.wsto.CourseUpdateWsTo;
import com.unimuenster.govlearnapi.course.entity.Course;
import com.unimuenster.govlearnapi.course.exception.NotFoundException;
import com.unimuenster.govlearnapi.course.exception.UnauthorizedException;
import com.unimuenster.govlearnapi.course.repository.CourseRepository;
import com.unimuenster.govlearnapi.course.service.dto.CourseCreationDTO;
import com.unimuenster.govlearnapi.course.service.dto.CourseDTO;
import com.unimuenster.govlearnapi.course.service.mapper.ServiceCourseMapper;
import com.unimuenster.govlearnapi.user.entity.UserEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
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
    
    public Course createCourse(CourseCreationDTO courseCreationDTO, UserEntity currentUser) {
        
        Course course = serviceCourseMapper.mapToCourse(courseCreationDTO, currentUser);
        
        return courseRepository.save(course);
    }

    @Transactional
    public void changeCourse(CourseUpdateWsTo courseUpdateWsTo, UserEntity currentUser) {

        Optional<Course> optionalCourseEntity = courseRepository.findById(courseUpdateWsTo.id());

        if(optionalCourseEntity.isEmpty()){
            throw new NotFoundException();
        }

        boolean isCreator = isCreatorOfCourse(currentUser, optionalCourseEntity.get());
        if(!isCreator){
            throw new UnauthorizedException();
        }

       courseRepository.updateCourse(serviceCourseMapper.map(courseUpdateWsTo));
    }

    private boolean isCreatorOfCourse(UserEntity user, Course course) {
        return course.getCreator().getId().equals(user.getId());
    }


    public List<CourseDTO> getCourses() {

        List<Course> allCourses = courseRepository.findAllCourses();

        return mapCourses(allCourses);
    }

    public List<CourseDTO> getCoursesWithoutGroupMember(Long groupmemberID) {

        List<Course> allCourses = courseRepository.getCoursesWithoutGroupmember(groupmemberID);

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

    @Transactional
    public void deleteCourse(Long courseId, UserEntity user) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Kurs nicht gefunden"));

        // Prüfe, ob aktueller Nutzer der Kurs-Ersteller ist
        if (!isCreatorOfCourse(user, course)) {
            throw new UnauthorizedException();
        }

        courseRepository.delete(course);
    }

    public List<CourseDTO> getCreatedCourses(Long userId) {
        List<Course> courses = courseRepository.getCreatedCourses(userId);

        return mapCourses(courses);
    }

    public List<String> getAllCourseProviders() {

        List<String> allCourseProviders = courseRepository.findAllCourseProviders();

        return allCourseProviders;
    }
}