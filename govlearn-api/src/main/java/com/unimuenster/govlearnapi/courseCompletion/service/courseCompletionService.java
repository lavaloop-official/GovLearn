package com.unimuenster.govlearnapi.courseCompletion.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.unimuenster.govlearnapi.courseCompletion.repository.courseCompletionRepository;
import com.unimuenster.govlearnapi.course.entity.Course;
import com.unimuenster.govlearnapi.course.exception.NotFoundException;
import com.unimuenster.govlearnapi.course.repository.CourseRepository;
import com.unimuenster.govlearnapi.course.service.dto.CourseDTO;
import com.unimuenster.govlearnapi.course.service.mapper.ServiceCourseMapper;
import com.unimuenster.govlearnapi.user.entity.UserEntity;
import com.unimuenster.govlearnapi.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;;

@Service
@RequiredArgsConstructor
public class courseCompletionService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final courseCompletionRepository courseCompletionRepository;
    private final ServiceCourseMapper serviceCourseMapper;
    

    
    @Transactional
    public void addCourseCompletion(UserEntity currentUser, Long courseId) {
        Optional<Course> course = courseRepository.findById(courseId);
        if (course.isEmpty()) {
            throw new NotFoundException();
        }
        if (isCourseCompleted(currentUser, courseId)) {
            throw new IllegalArgumentException();
        }
        //currentUser.getBookmarked().add(course.get());
        // Get Reference to the course in the list of bookmarks
        //Course courseInList = currentUser.getBookmarked().stream().filter(course1 -> course1.getId() == course.get().getId()).findFirst().get();
        //courseInList.getBookmarkedBy().add(currentUser);

        // Save changes to both entities
        //userRepository.save(currentUser);
        //courseRepository.save(course.get());
    }


    public List<CourseDTO> getUsersCourseCompletion(UserEntity currentUser) {

        List<Course> completions = courseCompletionRepository.getCourseCompletionsByUserId(currentUser.getId());

        return completions.stream().map(completion -> serviceCourseMapper.map(completion)).toList();
    }

    
    public Boolean isCourseCompleted(UserEntity currentUser, Long courseId) {
        Boolean isCompleted = courseCompletionRepository.isCourseCompleted(currentUser.getId(), courseId);

        return isCompleted; 
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
        // Get Reference to the course in the list of bookmarks
        //??? Course courseInList = currentUser.getBookmarked().stream().filter(course1 -> course1.getId() == course.get().getId()).findFirst().get();

        //currentUser.getBookmarked().remove(courseInList);
        //courseInList.getBookmarkedBy().remove(currentUser);

        // Save changes to both entities
        //userRepository.save(currentUser);
        //courseRepository.save(course.get());
    }
}
