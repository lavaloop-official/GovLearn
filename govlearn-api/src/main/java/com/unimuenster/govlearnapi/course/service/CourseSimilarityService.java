package com.unimuenster.govlearnapi.course.service;

import com.unimuenster.govlearnapi.course.entity.Course;
import com.unimuenster.govlearnapi.course.exception.NotFoundException;
import com.unimuenster.govlearnapi.course.repository.CourseRepository;
import com.unimuenster.govlearnapi.course.service.dto.CourseDTO;
import com.unimuenster.govlearnapi.course.service.mapper.ServiceCourseMapper;
import com.unimuenster.govlearnapi.recommendation.service.RecommendationService;
import com.unimuenster.govlearnapi.tags.service.CourseTagService;
import com.unimuenster.govlearnapi.tags.service.dto.TagDTO;
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

    public List<CourseDTO> getMostSimilarCourses(Long id, List<TagDTO> allTags) {
        List<Course> allCoursesExceptSelf = courseRepository.findAllCourses().stream().filter(course -> !Objects.equals(course.getId(), id)).collect(Collectors.toList());
        Optional<Course> courseById = courseRepository.findById(id);

        if ( courseById.isEmpty() ) {
            throw new NotFoundException();
        }

        double[] courseTagVector = courseTagService.getCourseTagBinaryVector(courseById.get(), allTags);
        List<Object[]> coursesWithSimilarity = recommendationService.compareToCourseSet(courseTagVector, allTags, allCoursesExceptSelf, authenticationService.getCurrentUser());

        return filterBySimilarity(coursesWithSimilarity);
    }

    private List<CourseDTO> filterBySimilarity(List<Object[]> coursesWithSimilarity) {
        return mapSimilarityToCoursesAndLimit(coursesWithSimilarity, 5);
    }


    private List<CourseDTO> mapSimilarityToCoursesAndLimit(List<Object[]> coursesWithSimilarity, int maxReturnedCourses) {
        coursesWithSimilarity.sort(Comparator.comparing(o ->  (Comparable)o[1]));
        Collections.reverse(coursesWithSimilarity);

        return coursesWithSimilarity
                .stream()
                .map(object -> serviceCourseMapper.map((Course) object[0]))
                .limit(maxReturnedCourses)
                .collect(Collectors.toList());
    }
}
