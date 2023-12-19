package com.unimuenster.govlearnapi.course.service;

import com.unimuenster.govlearnapi.course.entity.Course;
import com.unimuenster.govlearnapi.course.exception.NotFoundException;
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
                .link(courseCreationDTO.link())
                .build();
        
        return courseRepository.save(course);
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
                .name(course.get().getName())
                .description(course.get().getDescription())
                .creator(course.get().getCreator())
                .createdAt(course.get().getCreatedAt())
                .image(course.get().getImage())
                .provider(course.get().getProvider())
                .instructor(course.get().getInstructor())
                .certificate(course.get().getCertificate())
                .skilllevel(course.get().getSkilllevel())
                .duration(course.get().getDuration())
                .format(course.get().getFormat())
                .startDate(course.get().getStartDate())
                .costFree(course.get().getCostFree())
                .domainSpecific(course.get().getDomainSpecific())
                .link(course.get().getLink())
                .build();

        return map;
    }

    @Transactional
    public void deleteCourse(Long courseId, Long userId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Kurs nicht gefunden"));

        // Prüfe, ob aktueller Nutzer der Kurs-Ersteller ist
        if (!Objects.equals(course.getCreator().getId(), userId)) {
            throw new RuntimeException("keine Berechtigung den Kurs zu löschen");
        }

        // Lösche Kurs-Tags bevor Kurs gelöscht wird
        course.getCourseTags().clear();

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