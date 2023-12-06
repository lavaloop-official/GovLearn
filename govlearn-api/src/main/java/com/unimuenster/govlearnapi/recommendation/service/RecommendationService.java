package com.unimuenster.govlearnapi.recommendation.service;

import com.unimuenster.govlearnapi.course.entity.Course;
import com.unimuenster.govlearnapi.course.service.CourseService;
import com.unimuenster.govlearnapi.course.service.dto.CourseDTO;
import com.unimuenster.govlearnapi.course.service.mapper.ServiceCourseMapper;
import com.unimuenster.govlearnapi.core.config.math.Measure;
import com.unimuenster.govlearnapi.tags.entity.UserTag;
import com.unimuenster.govlearnapi.tags.service.CourseTagService;
import com.unimuenster.govlearnapi.tags.service.TagService;
import com.unimuenster.govlearnapi.tags.service.UserTagService;
import com.unimuenster.govlearnapi.tags.service.dto.TagDTO;
import com.unimuenster.govlearnapi.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RecommendationService {

    private final UserTagService userTagService;
    private final TagService tagService;
    private final CourseService courseService;
    private final CourseTagService courseTagService;
    private final ServiceCourseMapper serviceCourseMapper;

    public List<CourseDTO> getRecommendationBasedOnCourseSet(UserEntity user, List<Course> courses){
        List<UserTag> userTags = userTagService.getUserTags(user);
        // TODO declare allTags globally
        List<TagDTO> allTags = tagService.getTags();

        double[] userTagRatingVector = userTagService.getUserTagRatingVector(userTags, allTags);

        List<Object[]> courseSimilarityList = compareToCourseSet(userTagRatingVector, allTags, courses);

        sortSimilarityList(courseSimilarityList);

        List<Course> selectedCourses = mapAndLimitCourses(courseSimilarityList, 10000);

        return selectedCourses.stream().map(course -> serviceCourseMapper.map(course)).toList();
    }

    public List<CourseDTO> getRecommendation(UserEntity user, int maxReturnedCourses){

        List<UserTag> userTags = userTagService.getUserTags(user);
        // TODO declare allTags globally
        List<TagDTO> allTags = tagService.getTags();

        double[] userTagRatingVector = userTagService.getUserTagRatingVector(userTags, allTags);

        List<Object[]> courseSimilarityList = compareToCourses(userTagRatingVector, allTags);

        sortSimilarityList(courseSimilarityList);

        List<Course> courses = mapAndLimitCourses(courseSimilarityList, maxReturnedCourses);

        return courses.stream().map(course -> serviceCourseMapper.map(course)).toList();
    }

    private List<Course> mapAndLimitCourses(List<Object[]> courseSimilarityList, int maxReturnedCourses) {
        return courseSimilarityList
                .stream()
                .map(object -> (Course)object[0])
                .limit(maxReturnedCourses)
                .collect(Collectors.toList());
    }

    private List<Object[]> compareToCourses(double[] tagVector, List<TagDTO> allTags) {
        List<Object[]> courseSimilarityList = new ArrayList<>();

        List<CourseDTO> courses = courseService.getCourses();

        for( int i = 0; i< courses.size(); i++) {
            Course course = serviceCourseMapper.map(courses.get(i));

            double[] courseTagBinaryVector = courseTagService.getCourseTagBinaryVector(course, allTags);

            Optional<Object[]> similarityAndCourse
                    = computeSimilarityForCourse(tagVector, courseTagBinaryVector, course);

            similarityAndCourse.ifPresent(
                    sc -> courseSimilarityList.add(sc)
            );
        }

        return courseSimilarityList;
    }

    public List<Object[]> compareToCourseSet(double[] tagVector, List<TagDTO> allTags, List<Course> courses) {
        List<Object[]> courseSimilarityList = new ArrayList<>();

        List<CourseDTO> courseDTOS = courses
                .stream()
                .map(course -> serviceCourseMapper.map(course))
                .collect(Collectors.toList());

        for( int i = 0; i< courses.size(); i++) {
            Course course = serviceCourseMapper.map(courseDTOS.get(i));

            double[] courseTagBinaryVector = courseTagService.getCourseTagBinaryVector(course, allTags);

            Optional<Object[]> similarityAndCourse
                    = computeSimilarityForCourse(tagVector, courseTagBinaryVector, course);

            similarityAndCourse.ifPresent(
                    sc -> courseSimilarityList.add(sc)
            );
        }

        return courseSimilarityList;
    }

    private Optional<Object[]> computeSimilarityForCourse(double[] vectorA, double[] vectorB, Course course){

        double similarity = Measure.cosineSimilarity(vectorA, vectorB);

        return getSimilarityAndCourse(similarity, course);
    }


    private Optional<Object[]> getSimilarityAndCourse(double similarity, Course course){
        if ( ! Double.isNaN(similarity)){
            Object[] courseSimilarity = new Object[2];
            courseSimilarity[0] = course;
            courseSimilarity[1] = similarity;

            return Optional.of(courseSimilarity);
        }

        return Optional.empty();
    }

    private void sortSimilarityList(List<Object[]> courseSimilarityList) {
        courseSimilarityList.sort(Comparator.comparing(o ->  (Comparable)o[1]));
        Collections.reverse(courseSimilarityList);
    }

}
