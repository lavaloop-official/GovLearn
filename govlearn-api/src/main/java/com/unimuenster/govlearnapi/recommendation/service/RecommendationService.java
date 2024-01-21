package com.unimuenster.govlearnapi.recommendation.service;

import com.unimuenster.govlearnapi.course.controller.mapper.ControllerCourseMapper;
import com.unimuenster.govlearnapi.course.controller.wsto.CourseWsTo;
import com.unimuenster.govlearnapi.course.entity.Course;
import com.unimuenster.govlearnapi.course.service.CourseCompletionService;
import com.unimuenster.govlearnapi.course.service.CourseService;
import com.unimuenster.govlearnapi.course.service.dto.CourseDTO;
import com.unimuenster.govlearnapi.course.service.mapper.ServiceCourseMapper;
import com.unimuenster.govlearnapi.core.config.math.Measure;
import com.unimuenster.govlearnapi.recommendation.dto.CourseSimilarityHolder;
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
    private final CourseCompletionService courseCompletionService;
    private final ControllerCourseMapper controllerCourseMapper;

    public List<CourseDTO> getRecommendationBasedOnCourseSet(UserEntity user, List<Course> courses){
        List<UserTag> userTags = userTagService.getUserTags(user);
        List<TagDTO> allTags = tagService.getTags();

        double[] userTagRatingVector = userTagService.getUserTagRatingVector(userTags, allTags);

        List<CourseSimilarityHolder> courseSimilarityList = compareToCourseSet(userTagRatingVector, allTags, courses, user);

        sortList(courseSimilarityList);

        List<Course> selectedCourses = mapAndLimitCourses(courseSimilarityList, 10000);

        return selectedCourses.stream().map(course -> serviceCourseMapper.map(course)).collect(Collectors.toList());
    }

    public List<CourseWsTo> getRecommendation(UserEntity user, int maxReturnedCourses){

        List<UserTag> userTags = userTagService.getUserTags(user);
        // Tags could be cached in the future, but we do not have that kind of infrastructure yet
        List<TagDTO> allTags = tagService.getTags();

        double[] userTagRatingVector = userTagService.getUserTagRatingVector(userTags, allTags);

        List<CourseSimilarityHolder> courseSimilarityList = compareToCourses(userTagRatingVector, allTags, user);

        sortList(courseSimilarityList);

        List<Course> courses = mapAndLimitCourses(courseSimilarityList, maxReturnedCourses);

        return courses
                .stream()
                .map(course -> serviceCourseMapper.map(course))
                .map(course -> controllerCourseMapper.map(course))
                .collect(Collectors.toList());
    }


    private List<Course> mapAndLimitCourses(List<CourseSimilarityHolder> courseSimilarityList, int maxReturnedCourses) {
        return courseSimilarityList
                .stream()
                .map(holder -> holder.getCourse())
                .limit(maxReturnedCourses)
                .collect(Collectors.toList());
    }

    private List<CourseSimilarityHolder> compareToCourses(double[] tagVector, List<TagDTO> allTags, UserEntity user) {

        List<CourseDTO> completedCourses = serviceCourseMapper
                .mapListCourseWsTo(courseCompletionService.getUsersCourseCompletion(user));

        List<Course> courses = filterCourseList(courseService.getCourses(), completedCourses)
                .stream()
                .map(course -> serviceCourseMapper.map(course))
                .collect(Collectors.toList());

        return calculateCourseUserSimilarity(courses, tagVector, allTags);
    }

    private List<CourseSimilarityHolder> calculateCourseUserSimilarity (List<Course> courses, double[] tagVector, List<TagDTO> allTags) {
        List<CourseSimilarityHolder> courseSimilarityList = new ArrayList<>();

        for( int i = 0; i< courses.size(); i++) {
            Course course = courses.get(i);

            double[] courseTagBinaryVector = courseTagService.getCourseTagBinaryVector(course, allTags);

            Optional<CourseSimilarityHolder> similarityAndCourse
                    = computeSimilarityForCourse(tagVector, courseTagBinaryVector, course);

            similarityAndCourse.ifPresent(
                    sc -> courseSimilarityList.add(sc)
            );
        }

        return courseSimilarityList;
    }

    private List<CourseDTO> filterCourseList(List<CourseDTO> courseDTOs, List<CourseDTO> filter){
        return courseDTOs.stream().filter(course -> filter.stream().noneMatch(b -> b.id() == course.id())).collect(Collectors.toList());
    }

    public List<CourseSimilarityHolder> compareToCourseSet(double[] tagVector, List<TagDTO> allTags, List<Course> courses, UserEntity user) {

        List<CourseDTO> completedCourses = serviceCourseMapper.mapListCourseWsTo(courseCompletionService.getUsersCourseCompletion(user));

        List<CourseDTO> courseDTOS = courses
                .stream()
                .map(course -> serviceCourseMapper.map(course))
                .collect(Collectors.toList());

        List<Course> notCompletedCourses = filterCourseList(courseDTOS, completedCourses)
                .stream()
                .map(course -> serviceCourseMapper.map(course))
                .collect(Collectors.toList());

        return calculateCourseUserSimilarity(notCompletedCourses, tagVector, allTags);
    }

    private Optional<CourseSimilarityHolder> computeSimilarityForCourse(double[] vectorA, double[] vectorB, Course course){

        double similarity = Measure.euclidianDistance(vectorA, vectorB);

        return getSimilarityAndCourse(similarity, course);
    }


    private Optional<CourseSimilarityHolder> getSimilarityAndCourse(double similarity, Course course){
        if ( ! Double.isNaN(similarity)){
            CourseSimilarityHolder courseSimilarityHolder = new CourseSimilarityHolder();
            courseSimilarityHolder.setCourse(course);
            courseSimilarityHolder.setSimilarity(similarity);

            return Optional.of(courseSimilarityHolder);
        }

        return Optional.empty();
    }

    private void sortList(List<CourseSimilarityHolder> courseSimilarityList) {
        courseSimilarityList.sort(Comparator.comparing(holder ->  holder.getSimilarity()));
    }

}
