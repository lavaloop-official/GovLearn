package com.unimuenster.govlearnapi.recommendation.service;

import com.unimuenster.govlearnapi.category.entity.Category;
import com.unimuenster.govlearnapi.category.repository.CategoryRepository;
import com.unimuenster.govlearnapi.course.controller.mapper.ControllerCourseMapper;
import com.unimuenster.govlearnapi.course.controller.wsto.CourseWsTo;
import com.unimuenster.govlearnapi.course.entity.Course;
import com.unimuenster.govlearnapi.course.repository.CourseRepository;
import com.unimuenster.govlearnapi.course.service.dto.CourseDTO;
import com.unimuenster.govlearnapi.feedback.service.FeedbackService;
import com.unimuenster.govlearnapi.recommendation.controller.wsto.CategorizedWsTo;
import com.unimuenster.govlearnapi.recommendation.controller.wsto.RecommendationBundleWsTo;
import com.unimuenster.govlearnapi.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RecommendationBundleService {
    private final CategoryRepository categoryRepository;
    private final RecommendationService recommendationService;
    private final CourseRepository courseRepository;
    private final ControllerCourseMapper controllerCourseMapper;
    private final FeedbackService feedbackService;


    public RecommendationBundleWsTo getBundle(UserEntity currentUser) {

        RecommendationBundleWsTo bundle = new RecommendationBundleWsTo();

        List<CourseDTO> recommendations = recommendationService.getRecommendation(currentUser, 5);
        List<CourseWsTo> list = recommendations.stream().map(course -> controllerCourseMapper.map(course)).toList();

        for (CourseWsTo courseWsTo : list) {
            courseWsTo.setRatingAverage(feedbackService.getAverageFeedbackByCourseID(courseWsTo.getId()));
            courseWsTo.setRatingAmount(feedbackService.getAmountFeedbackByCourseID(courseWsTo.getId()));
        }

        bundle.setFeatured(list);

        List<Category> recommendedCategories = getRecommendedCategories(currentUser, 6);

        for(Category category : recommendedCategories){
            List<Course> coursesByCategory = courseRepository.findCoursesByCategory(Math.toIntExact(category.getId()));
            List<CourseDTO> recommendedCourses = recommendationService.getRecommendationBasedOnCourseSet(currentUser, coursesByCategory);

            CategorizedWsTo categorizedWsTo = new CategorizedWsTo();
            categorizedWsTo.setCategory(category.getName());
            categorizedWsTo.getItems().addAll(
                    recommendedCourses
                            .stream()
                            .map(course -> controllerCourseMapper.map(course))
                            .toList()
            );

            for (int i = 0; i < categorizedWsTo.getItems().size(); i++) {
                categorizedWsTo.getItems().get(i).setRatingAverage(feedbackService.getAverageFeedbackByCourseID(categorizedWsTo.getItems().get(i).getId()));
                categorizedWsTo.getItems().get(i).setRatingAmount(feedbackService.getAmountFeedbackByCourseID(categorizedWsTo.getItems().get(i).getId()));
            }

            bundle.getCategorized().add(categorizedWsTo);
        }

        return bundle;
    }
    
    private List<Category> getRecommendedCategories(UserEntity currentUser, int limit){
        return categoryRepository.getMostCommonCategories(Math.toIntExact(currentUser.getId()), limit);
    }
}
