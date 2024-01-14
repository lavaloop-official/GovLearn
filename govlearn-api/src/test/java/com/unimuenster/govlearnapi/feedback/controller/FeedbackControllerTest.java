package com.unimuenster.govlearnapi.feedback.controller;

import com.unimuenster.govlearnapi.AbstractIntegrationTest;
import com.unimuenster.govlearnapi.common.responsewrapper.Response;
import com.unimuenster.govlearnapi.course.controller.wsto.CourseWsTo;
import com.unimuenster.govlearnapi.feedback.controller.FeedbackController;
import com.unimuenster.govlearnapi.feedback.controller.wsto.FeedbackCreationWsTo;
import com.unimuenster.govlearnapi.feedback.controller.wsto.FeedbackUpdateWsTo;
import com.unimuenster.govlearnapi.feedback.controller.wsto.FeedbackWsTo;
import com.unimuenster.govlearnapi.initializer.InitializerService;
import com.unimuenster.govlearnapi.user.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Transactional
public class FeedbackControllerTest extends AbstractIntegrationTest {
    @Autowired
    private InitializerService initializerService;
    @Autowired
    private FeedbackController feedbackController;
    @Autowired
    private UserDetailsService userDetailsService;

    @Test
    @Rollback
    public void createFeedback(){
        setCurrentUser();

        FeedbackCreationWsTo feedbackCreationWsTo = new FeedbackCreationWsTo(
                "Test Feedback",
                "Test Feedback Description",
                5,
                1L
        );

        ResponseEntity<Response> response = feedbackController.createFeedback(feedbackCreationWsTo);

        assert response.getStatusCode().is2xxSuccessful();
    }

    @Test
    public void getFeedback(){
        setCurrentUser();
        ResponseEntity<Response> response = feedbackController.getFeedbackByCourseIDAndFeedbackID(2L);
        List<FeedbackWsTo> payload = ((Response<List< FeedbackWsTo>>) response.getBody()).getPayload();

        assertEquals(1, payload.size());
    }

    @Test
    public void getFeedbackWithLimitAndOffset(){
        setCurrentUser();
        // Kurs 2 enthält ein Feedback
        ResponseEntity<Response> response = feedbackController.getFeedbackByCourseIDWithLimitAndOffset(2L, Optional.of(1L), java.util.Optional.<Long>empty());
        List<FeedbackWsTo> payload = ((Response<List< FeedbackWsTo>>) response.getBody()).getPayload();

        assertEquals(1, payload.size());

        // Durch den Offset wird das erste Feedback übersprungen
        response = feedbackController.getFeedbackByCourseIDWithLimitAndOffset(2L, Optional.of(1L), Optional.of(1L));
        payload = ((Response<List< FeedbackWsTo>>) response.getBody()).getPayload();

        assertEquals(0, payload.size());
    }

    @Test
    @Rollback
    public void updateFeedback(){
        setCurrentUser();

        FeedbackUpdateWsTo feedbackUpdateWsTo = new FeedbackUpdateWsTo(
                1L,
                "Test Feedback",
                "Test Feedback Description",
                4
        );

        ResponseEntity<Response> updateResponse = feedbackController.updateFeedbackFromCourse(feedbackUpdateWsTo);

        assert updateResponse.getStatusCode().is2xxSuccessful();
    }

    @Test
    @Rollback
    public void deleteFeedback(){
        setCurrentUser();

        ResponseEntity<Response> deleteResponse = feedbackController.deleteFeedbackFromCourse(1L);

        assert deleteResponse.getStatusCode().is2xxSuccessful();
    }

    private void setCurrentUser(){
        UserDetails userDetails = userDetailsService.loadUserByUsername(
                initializerService.getUser2().getEmail()
        );
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
