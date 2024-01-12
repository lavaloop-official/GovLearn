package com.unimuenster.govlearnapi.course.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.unimuenster.govlearnapi.course.repository.CourseCompletionRepository;
import com.unimuenster.govlearnapi.course.controller.mapper.ControllerCourseMapper;
import com.unimuenster.govlearnapi.course.controller.wsto.CourseWsTo;
import com.unimuenster.govlearnapi.course.entity.Course;
import com.unimuenster.govlearnapi.course.exception.NotFoundException;
import com.unimuenster.govlearnapi.course.repository.CourseRepository;
import com.unimuenster.govlearnapi.course.service.dto.CourseDTO;
import com.unimuenster.govlearnapi.course.service.mapper.ServiceCourseMapper;
import com.unimuenster.govlearnapi.user.entity.UserEntity;
import com.unimuenster.govlearnapi.user.repository.UserRepository;
import com.unimuenster.govlearnapi.course.exception.IllegalArgumentException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseCompletionService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final CourseCompletionRepository courseCompletionRepository;
    private final ServiceCourseMapper serviceCourseMapper;
    private final ControllerCourseMapper controllerCourseMapper;

    @Transactional
    public void addCourseCompletion(UserEntity currentUser, Long courseId) {
        Optional<Course> course = courseRepository.findById(courseId);
        if (course.isEmpty()) {
            throw new NotFoundException();
        }
        if (isCourseCompleted(currentUser, courseId)) {
            throw new IllegalArgumentException();
        }
        currentUser.getCompleted().add(course.get());

        userRepository.save(currentUser);

        course.get().getCompletedBy().add(currentUser);

        courseRepository.save(course.get());
    }


    public List<CourseWsTo> getUsersCourseCompletion(UserEntity currentUser) {

        List<Course> completions = courseCompletionRepository.getCourseCompletionsByUserId(currentUser.getId());

        return controllerCourseMapper.mapList(serviceCourseMapper.mapList(completions));
    }


    public Boolean isCourseCompleted(UserEntity currentUser, Long courseId) {
        return courseCompletionRepository.isCourseCompleted(currentUser.getId(), courseId);
    }


    @Transactional
    public void deleteCourseCompletion(UserEntity currentUser, Long courseId) {
        Optional<Course> course = courseRepository.findById(courseId);
        if (course.isEmpty()) {
            throw new NotFoundException();
        }
        if (!isCourseCompleted(currentUser, courseId)) {
            throw new IllegalArgumentException();
        }

        currentUser.getCompleted().remove(course.get());
        
        userRepository.save(currentUser);

        course.get().getCompletedBy().remove(currentUser);

        courseRepository.save(course.get());
    }
}