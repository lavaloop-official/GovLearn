package com.unimuenster.govlearnapi.course.service;

import com.unimuenster.govlearnapi.course.entity.Course;
import com.unimuenster.govlearnapi.course.exception.NotFoundException;
import com.unimuenster.govlearnapi.course.repository.CourseRepository;
import com.unimuenster.govlearnapi.course.service.dto.CourseDTO;
import com.unimuenster.govlearnapi.course.service.mapper.ServiceCourseMapper;
import com.unimuenster.govlearnapi.recommendation.dto.CourseSimilarityHolder;
import com.unimuenster.govlearnapi.recommendation.service.RecommendationService;
import com.unimuenster.govlearnapi.tags.service.CourseTagService;
import com.unimuenster.govlearnapi.tags.service.dto.TagDTO;
import com.unimuenster.govlearnapi.tags.service.dto.TagRatingVector;
import com.unimuenster.govlearnapi.user.service.AuthenticationService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseSimilarityService {
    private final CourseRepository courseRepository;
    private final ServiceCourseMapper serviceCourseMapper;
    private final RecommendationService recommendationService;
    private final CourseTagService courseTagService;
    private final AuthenticationService authenticationService;

    public List<CourseDTO> getMostSimilarCourses(Long courseId, List<TagDTO> allTags) {
        List<Course> allCoursesExceptSelf
                = courseRepository.findAllCoursesUnequal(courseId);

        Optional<Course> courseById = courseRepository.findById(courseId);

        if ( courseById.isEmpty() ) {
            throw new NotFoundException();
        }

        TagRatingVector courseTagVector = courseTagService.getCourseTagBinaryVector(courseById.get(), allTags);

        List<CourseSimilarityHolder> coursesWithSimilarity
                = recommendationService
                .compareToCourseSet(
                        courseTagVector,
                        allTags,
                        allCoursesExceptSelf,
                        authenticationService.getCurrentUser()
                );

        return filterBySimilarity(coursesWithSimilarity);
    }

    private List<CourseDTO> filterBySimilarity(List<CourseSimilarityHolder> coursesWithSimilarity) {
        return mapSimilarityToCoursesAndLimit(coursesWithSimilarity, 5);
    }


    private List<CourseDTO> mapSimilarityToCoursesAndLimit(List<CourseSimilarityHolder> coursesWithSimilarity, int maxReturnedCourses) {

        sortBySimilarity(coursesWithSimilarity);

        List<CourseDTO> courseList = getCourseList(coursesWithSimilarity);

        return limitBy(courseList, maxReturnedCourses);
    }

    private List<CourseDTO> limitBy(List<CourseDTO> courseList, int maxReturnedCourses) {
        return courseList.stream().limit(maxReturnedCourses).collect(Collectors.toList());
    }

    private List<CourseDTO> getCourseList(List<CourseSimilarityHolder> coursesWithSimilarity) {
        return coursesWithSimilarity.stream().map(holder -> serviceCourseMapper.map(holder.getCourse())).collect(Collectors.toList());
    }

    private void sortBySimilarity(List<CourseSimilarityHolder> coursesWithSimilarity) {
        coursesWithSimilarity.sort(Comparator.comparing(holder ->  holder.getSimilarity()));
    }
}
