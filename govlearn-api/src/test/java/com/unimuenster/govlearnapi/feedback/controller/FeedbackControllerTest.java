package com.unimuenster.govlearnapi.feedback.controller;

import com.unimuenster.govlearnapi.AbstractIntegrationTest;
import com.unimuenster.govlearnapi.common.responsewrapper.Response;
import com.unimuenster.govlearnapi.course.entity.Course;
import com.unimuenster.govlearnapi.feedback.controller.wsto.FeedbackCreationWsTo;
import com.unimuenster.govlearnapi.feedback.controller.wsto.FeedbackUpdateWsTo;
import com.unimuenster.govlearnapi.feedback.entity.Feedback;
import com.unimuenster.govlearnapi.feedback.repository.FeedbackRepository;
import com.unimuenster.govlearnapi.initializer.InitializerService;
import com.unimuenster.govlearnapi.user.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class FeedbackControllerTest extends AbstractIntegrationTest {

    @Autowired
    private FeedbackController feedbackController;
    @Autowired
    private InitializerService initializerService;
    @Autowired
    private FeedbackRepository feedbackRepository;
    private UserEntity testUser;

    @BeforeEach
    void setUp() {
        testUser = initializerService.getUser1();

        setCurrentUser(testUser);
    }

    @Test
    void createFeedback(){

        Course testCourse = initializerService.getCourse3();

        FeedbackCreationWsTo feedbackCreationWsTo = new FeedbackCreationWsTo(
                "Feedbacktitle",
                "Feedbacktext",
                3,
                testCourse.getId()
        );

        ResponseEntity<Response> feedbackResponse = feedbackController.createFeedback(feedbackCreationWsTo);

        Feedback feedback = feedbackRepository
                .findFeedbackByCourseAndUser(testCourse.getId(), testUser.getId())
                .get(0);

        assertEquals(feedbackCreationWsTo.rating(), feedback.getRating());
        assertEquals(feedbackCreationWsTo.courseID(), feedback.getCourse().getId());
        assertEquals(feedbackCreationWsTo.title(), feedback.getTitle());
        assertEquals(feedbackCreationWsTo.description(), feedback.getDescription());
        assertEquals(testUser.getId(), feedback.getUser().getId());

        assertEquals(HttpStatus.OK, feedbackResponse.getStatusCode());
    }

    @Test
    void updateFeedback() {

        Course testCourse = initializerService.getCourse4();

        Feedback feedback = new Feedback();
        feedback.setTitle("title");
        feedback.setDescription("description");
        feedback.setRating(2);
        feedback.setCourse(testCourse);
        feedback.setUser(testUser);

        feedbackRepository.save(feedback);

        FeedbackUpdateWsTo feedbackUpdateWsTo = new FeedbackUpdateWsTo(
                feedback.getId(),
                "newTitle",
                "newDescription",
                4
        );

        ResponseEntity<Response> responseResponseEntity = feedbackController.updateFeedbackFromCourse(feedbackUpdateWsTo);

        Feedback updatedFeedback = feedbackRepository
                .findFeedbackByCourseAndUser(testCourse.getId(), testUser.getId())
                .get(0);

        assertEquals(feedbackUpdateWsTo.rating(), updatedFeedback.getRating());
        assertEquals(feedbackUpdateWsTo.title(), updatedFeedback.getTitle());
        assertEquals(feedbackUpdateWsTo.description(), updatedFeedback.getDescription());
        assertEquals(testUser.getId(), updatedFeedback.getUser().getId());

        assertEquals(HttpStatus.OK, responseResponseEntity.getStatusCode());
    }
}