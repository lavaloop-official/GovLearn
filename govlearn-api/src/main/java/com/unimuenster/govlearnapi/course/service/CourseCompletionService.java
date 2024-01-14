package com.unimuenster.govlearnapi.course.service;

import java.util.List;
import java.util.Optional;

import com.unimuenster.govlearnapi.course.entity.CourseCompletion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.unimuenster.govlearnapi.course.repository.CourseCompletionRepository;
import com.unimuenster.govlearnapi.course.controller.mapper.ControllerCourseMapper;
import com.unimuenster.govlearnapi.course.controller.wsto.CourseWsTo;
import com.unimuenster.govlearnapi.course.entity.Course;
import com.unimuenster.govlearnapi.course.exception.NotFoundException;
import com.unimuenster.govlearnapi.course.repository.CourseRepository;
import com.unimuenster.govlearnapi.course.service.mapper.ServiceCourseMapper;
import com.unimuenster.govlearnapi.user.entity.UserEntity;
import com.unimuenster.govlearnapi.course.exception.IllegalArgumentException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseCompletionService {
    private final CourseRepository courseRepository;
    private final CourseCompletionRepository courseCompletionRepository;
    private final ServiceCourseMapper serviceCourseMapper;
    private final ControllerCourseMapper controllerCourseMapper;

    @Transactional
    public void addCourseCompletion(UserEntity currentUser, Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(NotFoundException::new);

        if ( isCourseCompleted(currentUser, courseId) ) {
            throw new IllegalArgumentException();
        }

        CourseCompletion courseCompletion = new CourseCompletion();
        courseCompletion.setCourse(course);
        courseCompletion.setCompletee(currentUser);

        courseCompletionRepository.save(courseCompletion);
    }


    public List<CourseWsTo> getUsersCourseCompletion(UserEntity currentUser) {

        List<CourseCompletion> completions = courseCompletionRepository.getCourseCompletionsByUserId(currentUser.getId());

        return controllerCourseMapper.mapList(serviceCourseMapper.mapList(completions.stream().map(CourseCompletion::getCourse).toList()));
    }


    public boolean isCourseCompleted(UserEntity currentUser, Long courseId) {
        return courseCompletionRepository.isCourseCompleted(currentUser.getId(), courseId);
    }


    @Transactional
    public void deleteCourseCompletion(UserEntity currentUser, Long courseId) {
        courseCompletionRepository.deleteCourseCompletion(currentUser.getId(), courseId);
    }
}