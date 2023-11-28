package com.unimuenster.govlearnapi.recommendation.service;

import com.unimuenster.govlearnapi.course.entity.Course;
import com.unimuenster.govlearnapi.course.service.CourseService;
import com.unimuenster.govlearnapi.course.service.dto.CourseDTO;
import com.unimuenster.govlearnapi.course.service.mapper.ServiceCourseMapper;
import com.unimuenster.govlearnapi.core.config.math.Measure;
import com.unimuenster.govlearnapi.tags.entity.CourseTag;
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
        List<UserTag> userTags = getUserTags(user);
        // TODO declare allTags globally
        List<TagDTO> allTags = tagService.getTags();

        double[] userTagRatingVector = getUserTagRatingVector(userTags, allTags);

        List<Object[]> courseSimilarityList = compareToCourses(userTagRatingVector, allTags, courses);

        sortSimilarityList(courseSimilarityList);

        List<Course> selectedCourses = mapAndLimitCourses(courseSimilarityList, 10000);

        return selectedCourses.stream().map(course -> serviceCourseMapper.map(course)).toList();
    }

    public List<CourseDTO> getRecommendation(UserEntity user, int maxReturnedCourses){

        List<UserTag> userTags = getUserTags(user);
        // TODO declare allTags globally
        List<TagDTO> allTags = tagService.getTags();

        double[] userTagRatingVector = getUserTagRatingVector(userTags, allTags);

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

    private List<Object[]> compareToCourses(double[] userTagRatingVector, List<TagDTO> allTags) {
        List<Object[]> courseSimilarityList = new ArrayList<>();

        List<CourseDTO> courses = courseService.getCourses();

        for( int i = 0; i< courses.size(); i++) {
            Course course = serviceCourseMapper.map(courses.get(i));

            double[] courseTagBinaryVector = getCourseTagBinaryVector(course, allTags);

            Optional<Object[]> similarityAndCourse
                    = computeSimilarityForCourse(userTagRatingVector, courseTagBinaryVector, course);

            similarityAndCourse.ifPresent(
                    sc -> courseSimilarityList.add(sc)
            );
        }

        return courseSimilarityList;
    }

    public List<Object[]> compareToCourses(double[] tagVector, List<TagDTO> allTags, List<Course> courses) {
        List<Object[]> courseSimilarityList = new ArrayList<>();

        List<CourseDTO> courseDTOS = courses
                .stream()
                .map(course -> serviceCourseMapper.map(course))
                .collect(Collectors.toList());

        for( int i = 0; i< courses.size(); i++) {
            Course course = serviceCourseMapper.map(courseDTOS.get(i));

            double[] courseTagBinaryVector = getCourseTagBinaryVector(course, allTags);

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

    public double[] getCourseTagBinaryVector(Course course, List<TagDTO> allTags){

        List<CourseTag> courseTags = courseTagService.getCourseTags(course);

        return getCourseTagVector(courseTags, allTags);
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

    private double[] getCourseTagVector(List<CourseTag> courseTags, List<TagDTO> allTags){
        double[] courseTagVector = new double[allTags.size()];

        for ( int i = 0; i < allTags.size(); i++ ){
            TagDTO currentTagDTO = allTags.get(i);

            boolean containsTag = isTagInCourseTags(courseTags, currentTagDTO);

            courseTagVector[i] = getTagValue(containsTag);
        }

        return courseTagVector;
    }

    private int getTagValue (boolean containsTag){
        if ( containsTag ) {
            return 1;
        }else {
            return 0;
        }
    }

    private boolean isTagInCourseTags(List<CourseTag> courseTags, TagDTO currentTagDTO){
        return courseTags.stream().anyMatch(courseTag -> courseTag.getId() == currentTagDTO.id());
    }

    private void sortSimilarityList(List<Object[]> courseSimilarityList) {
        courseSimilarityList.sort(Comparator.comparing(o ->  (Comparable)o[1]));
        Collections.reverse(courseSimilarityList);
    }


    private double[] getUserTagRatingVector(List<UserTag> tags, List<TagDTO> allTags){
        double[] userTagRatingVector = new double[allTags.size()];

        for (int i = 0; i < allTags.size(); i++) {
            TagDTO currentTag = allTags.get(i);

            Optional<UserTag> userTag = findCurrentTag(currentTag, tags);

            userTagRatingVector[i] = getUserTagRating(userTag);
        }

        return userTagRatingVector;
    }

    private int getUserTagRating(Optional<UserTag> foundUserTag){
        if ( foundUserTag.isPresent() ) {
            return foundUserTag.get().getRating();
        }

        return  0;
    }

    private Optional<UserTag> findCurrentTag(TagDTO currentTag, List<UserTag> tags){
        return tags
                .stream()
                .filter(userTag ->
                        userTag.getTag().getName().equals(currentTag.name())
                )
                .findFirst();
    }
    public List<UserTag> getUserTags(UserEntity user) {
        return userTagService.getUserTags(user);
    }
}
