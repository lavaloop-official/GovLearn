package com.unimuenster.govlearnapi.recommendation.service;

import com.unimuenster.govlearnapi.AbstractIntegrationTest;
import com.unimuenster.govlearnapi.course.service.dto.CourseDTO;
import com.unimuenster.govlearnapi.initializer.InitializerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecommendationServiceTest extends AbstractIntegrationTest {

    @Autowired
    RecommendationService recommendationService;
    @Autowired
    InitializerService initializer;

    @Test
    void getRecommendations(){

        List<CourseDTO> recommendation
                = recommendationService.getRecommendation(initializer.getRecommendationUser(), 10);

        assertEquals(recommendation.get(0).id(), initializer.getCourse3().getId());
    }
}