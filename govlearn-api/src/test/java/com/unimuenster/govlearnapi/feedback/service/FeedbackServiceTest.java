package com.unimuenster.govlearnapi.feedback.service;

import com.unimuenster.govlearnapi.AbstractIntegrationTest;
import com.unimuenster.govlearnapi.feedback.entity.Feedback;
import com.unimuenster.govlearnapi.feedback.service.dto.FeedbackCreationDTO;
import com.unimuenster.govlearnapi.initializer.InitializerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class FeedbackServiceTest extends AbstractIntegrationTest {
    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private InitializerService initializerService;

    @Test
    public void getFeedbackById(){
        Feedback feedback = feedbackService.getFeedbackEntityById(1L);

        assertEquals(feedback.getId(), initializerService.getFeedback().getId());
    }

    @Test
    public void getAmountOfFeedbacksByCourseId(){
        Long amountOfFeedbacks = feedbackService.getAmountFeedbackByCourseID(2L);
        assertEquals(amountOfFeedbacks, (Object) 1L);

        feedbackService.createFeedback(new FeedbackCreationDTO(
                "Test Feedback",
                "Test Feedback Description",
                4,
                2L
        ), initializerService.getUser1());

        amountOfFeedbacks = feedbackService.getAmountFeedbackByCourseID(2L);
        assertEquals(amountOfFeedbacks, (Object) 2L);
    }

    @Test
    public void getAverageFeedbackByCourseId(){
        Float averageFeedback = feedbackService.getAverageFeedbackByCourseID(2L);
        assertEquals((double) averageFeedback, 5.0, 0.01);

        feedbackService.createFeedback(new FeedbackCreationDTO(
                "Test Feedback",
                "Test Feedback Description",
                4,
                2L
        ), initializerService.getUser1());

        averageFeedback = feedbackService.getAverageFeedbackByCourseID(2L);
        assertEquals((double) averageFeedback, 4.5, 0.01);
    }
}
