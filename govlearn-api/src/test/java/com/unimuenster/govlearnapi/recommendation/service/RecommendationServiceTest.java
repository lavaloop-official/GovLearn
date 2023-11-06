package com.unimuenster.govlearnapi.recommendation.service;

import com.unimuenster.govlearnapi.AbstractIntegrationTest;
import com.unimuenster.govlearnapi.Initializer;
import com.unimuenster.govlearnapi.course.entity.Course;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecommendationServiceTest extends AbstractIntegrationTest {

    @Autowired
    RecommendationService recommendationService;
    @Autowired
    Initializer initializer;

    @Test
    void getRecommendations(){

        List<Course> recommendation = recommendationService.getRecommendation(initializer.getRecommendationUser(), 10);


    }
}