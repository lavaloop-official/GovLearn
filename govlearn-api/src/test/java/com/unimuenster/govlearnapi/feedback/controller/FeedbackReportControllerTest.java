package com.unimuenster.govlearnapi.feedback.controller;

import com.unimuenster.govlearnapi.AbstractIntegrationTest;
import com.unimuenster.govlearnapi.common.responsewrapper.Response;
import com.unimuenster.govlearnapi.core.config.enums.ReportReason;
import com.unimuenster.govlearnapi.feedback.controller.wsto.FeedbackCreationWsTo;
import com.unimuenster.govlearnapi.feedback.controller.wsto.FeedbackReportCreationWsTo;
import com.unimuenster.govlearnapi.feedback.controller.wsto.FeedbackReportWsTo;
import com.unimuenster.govlearnapi.feedback.controller.wsto.FeedbackUpdateWsTo;
import com.unimuenster.govlearnapi.feedback.controller.wsto.FeedbackWsTo;
import com.unimuenster.govlearnapi.initializer.InitializerService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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

@Transactional
public class FeedbackReportControllerTest extends AbstractIntegrationTest {
    @Autowired
    private InitializerService initializerService;
    @Autowired
    private FeedbackReportController feedbackReportController;
    @Autowired
    private UserDetailsService userDetailsService;

    @BeforeEach
    void setUp() {
        setCurrentUser();
    }

    @Test
    public void createFeedbackReport(){

        FeedbackReportCreationWsTo feedbackReportCreationWsTo = new FeedbackReportCreationWsTo(
                "Test",
                ReportReason.Sonstiges
        );

        ResponseEntity<Response> response = feedbackReportController.createFeedbackReport(1,feedbackReportCreationWsTo);

        assert response.getStatusCode().is2xxSuccessful();
    }

    @Test
    public void getFeedbackReport(){

        FeedbackReportCreationWsTo feedbackReportCreationWsTo = new FeedbackReportCreationWsTo(
                "Test",
                ReportReason.Sonstiges
        );

        feedbackReportController.createFeedbackReport(1,feedbackReportCreationWsTo);

        ResponseEntity<Response> response = feedbackReportController.getFeedbackReport(2L);
        FeedbackReportWsTo payload = ((Response<FeedbackReportWsTo>) response.getBody()).getPayload();

        assertEquals("Test",payload.getReport_message());
        assertEquals(ReportReason.Sonstiges,payload.getReport_reason());
    }

    private void setCurrentUser(){
        UserDetails userDetails = userDetailsService.loadUserByUsername(
                initializerService.getUser2().getEmail()
        );
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
