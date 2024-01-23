package com.unimuenster.govlearnapi.courseCompletion.service;

import java.util.List;

import com.unimuenster.govlearnapi.course.service.CourseService;
import com.unimuenster.govlearnapi.course.service.dto.CourseDTO;
import com.unimuenster.govlearnapi.courseCompletion.entity.CourseCompletion;
import com.unimuenster.govlearnapi.tags.service.UserTagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.unimuenster.govlearnapi.courseCompletion.repository.CourseCompletionRepository;
import com.unimuenster.govlearnapi.course.controller.mapper.ControllerCourseMapper;
import com.unimuenster.govlearnapi.course.controller.wsto.CourseWsTo;
import com.unimuenster.govlearnapi.course.entity.Course;
import com.unimuenster.govlearnapi.course.service.mapper.ServiceCourseMapper;
import com.unimuenster.govlearnapi.user.entity.UserEntity;
import com.unimuenster.govlearnapi.course.exception.IllegalArgumentException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseCompletionService {
    private final CourseCompletionRepository courseCompletionRepository;
    private final ServiceCourseMapper serviceCourseMapper;
    private final ControllerCourseMapper controllerCourseMapper;
    private final CourseService courseService;
    private final UserTagService userTagService;

    @Transactional
    public void addCourseCompletion(UserEntity currentUser, Long courseId) {
        CourseDTO courseById = courseService.getCourseById(courseId);

        if ( isCourseCompleted(currentUser, courseById.id()) ) {
            throw new IllegalArgumentException();
        }

        userTagService.adjustUserTags(currentUser, courseId, true);

        CourseCompletion courseCompletion =
                CourseCompletion
                        .build(
                                currentUser,
                                Course.builder().id(courseById.id()).build()
                        );

        courseCompletionRepository.save(courseCompletion);
    }

    public List<CourseWsTo> getUsersCourseCompletion(UserEntity currentUser) {

        List<CourseCompletion> completions
                = courseCompletionRepository
                .getCompletedCoursesByUserId(
                        currentUser.getId()
                );

        return mapToCourseWsTo(completions);
    }

    private List<CourseWsTo> mapToCourseWsTo(List<CourseCompletion> completions){
        return controllerCourseMapper
                .mapList(
                        serviceCourseMapper
                                .mapListCourse(
                                        completions.stream().map(CourseCompletion::getCourse).toList()
                                )
                );
    }


    public boolean isCourseCompleted(UserEntity currentUser, Long courseId) {
        return courseCompletionRepository.isCourseCompleted(currentUser.getId(), courseId);
    }


    @Transactional
    public void deleteCourseCompletion(UserEntity currentUser, Long courseId) {
        courseCompletionRepository.deleteCourseCompletion(currentUser.getId(), courseId);
        userTagService.adjustUserTags(currentUser, courseId, false);
    }
}