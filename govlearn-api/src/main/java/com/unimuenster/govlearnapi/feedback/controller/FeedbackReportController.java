package com.unimuenster.govlearnapi.feedback.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unimuenster.govlearnapi.common.responsewrapper.Response;
import com.unimuenster.govlearnapi.feedback.controller.wsto.FeedbackReportCreationWsTo;
import com.unimuenster.govlearnapi.feedback.controller.wsto.FeedbackReportWsTo;
import com.unimuenster.govlearnapi.feedback.service.FeedbackReportService;
import com.unimuenster.govlearnapi.user.service.AuthenticationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
@Slf4j
public class FeedbackReportController {

    private final AuthenticationService authenticationService;
    private final FeedbackReportService feedbackReportService;
    
    @Operation(
            security = { @SecurityRequirement(name = "Authorization") },
            description = "Create a report"
    )
    @PreAuthorize("hasAuthority('user')")
    @PostMapping("/reports/feedback/{feedbackId}")
    public ResponseEntity<Response> createFeedbackReport(
        @PathVariable long feedbackId,
        @RequestBody FeedbackReportCreationWsTo feedbackReportCreationWsTo
    ){

        feedbackReportService.createFeedbackReport(feedbackReportCreationWsTo, authenticationService.getCurrentUser(), feedbackId);

        return ResponseEntity.ok(Response.of(true));
    }

    @Operation(
            security = { @SecurityRequirement(name = "Authorization") },
            description = "Find a report by id"
    )
    @PreAuthorize("hasAuthority('user')")
    @GetMapping("/reports/{feedbackReportId}")
    public ResponseEntity<Response> getFeedbackReport(
        @PathVariable long feedbackReportId
    ){
        FeedbackReportWsTo feedbackReportWsTo = feedbackReportService.getFeedbackReport(feedbackReportId);

        return ResponseEntity.ok(Response.of(feedbackReportWsTo, true));
    }

    @Operation(
        security = { @SecurityRequirement(name = "Authorization") },
        description = "Return all reports"
    )
    @PreAuthorize("hasAuthority('user')")
    @GetMapping("/reports")
    public ResponseEntity<Response> getAllFeedbackReports(){
        List<FeedbackReportWsTo> feedbackReportWsTo = feedbackReportService.getAllFeedbackReports();

        return ResponseEntity.ok(Response.of(feedbackReportWsTo, true));
    }
}
