package com.unimuenster.govlearnapi.feedback.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unimuenster.govlearnapi.common.responsewrapper.Message;
import com.unimuenster.govlearnapi.common.responsewrapper.Response;
import com.unimuenster.govlearnapi.course.controller.wsto.CourseWsTo;
import com.unimuenster.govlearnapi.course.service.dto.CourseDTO;
import com.unimuenster.govlearnapi.feedback.controller.mapper.ControllerFeedbackMapper;
import com.unimuenster.govlearnapi.feedback.controller.wsto.FeedbackCreationWsTo;
import com.unimuenster.govlearnapi.feedback.controller.wsto.FeedbackUpdateWsTo;
import com.unimuenster.govlearnapi.feedback.controller.wsto.FeedbackWsTo;
import com.unimuenster.govlearnapi.feedback.service.FeedbackService;
import com.unimuenster.govlearnapi.feedback.service.dto.FeedbackCreationDTO;
import com.unimuenster.govlearnapi.feedback.service.dto.FeedbackDTO;
import com.unimuenster.govlearnapi.user.controller.wsto.UserWsTo;
import com.unimuenster.govlearnapi.user.entity.UserEntity;
import com.unimuenster.govlearnapi.user.service.AuthenticationService;
import com.unimuenster.govlearnapi.user.service.CustomUserCrudService;
import com.unimuenster.govlearnapi.user.service.dto.UserDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
@Slf4j
public class FeedbackController {
    private final FeedbackService feedbackService;
    private final ControllerFeedbackMapper controllerFeedbackMapper;
    private final AuthenticationService authenticationService;

    @Operation(
            security = { @SecurityRequirement(name = "Authorization") },
            description = "Create feedback for a course."
    )
    @PreAuthorize("hasAuthority('user')")
    @PostMapping("/feedback")
    public ResponseEntity<Response> createFeedback(
            @RequestBody FeedbackCreationWsTo feedbackCreationWsTo
    ){
        UserEntity currentUser = authenticationService.getCurrentUser();

        FeedbackCreationDTO feedbackCreationDTO = controllerFeedbackMapper.map(feedbackCreationWsTo);

        feedbackService.createFeedback(feedbackCreationDTO, currentUser);

        return ResponseEntity.ok(Response.of(true));
    }

    @Operation(
            security = { @SecurityRequirement(name = "Authorization") },
            description = "Get feedback by courseID and userID."
    )
    @PreAuthorize("hasAuthority('user')")
    @GetMapping("/feedback/user/course/{courseID}")
    public ResponseEntity<Response> getFeedbackByCourseIDAndFeedbackID(@PathVariable Long courseID) {

        UserEntity currentUser = authenticationService.getCurrentUser();

        List<FeedbackDTO> feedbackDTOs = feedbackService.getFeedbackByCourseAndUser(courseID, currentUser.getId());

        List<FeedbackWsTo> feedbackWsTos = controllerFeedbackMapper.mapList(feedbackDTOs);

        return ResponseEntity.ok( Response.of(feedbackWsTos, new Message(Message.SUCCESS)));
    }

    @Operation(
        security = { @SecurityRequirement(name = "Authorization") },
        description = "Get feedback by courseID with a limit and offset"
    )
    @PreAuthorize("hasAuthority('user')")
    @GetMapping("/feedback/course/{courseID}")
    public ResponseEntity<Response> getFeedbackByCourseIDWithLimitAndOffset(@PathVariable Long courseID, Optional<Long> limit, Optional<Long> offset) {

        List<FeedbackDTO> feedbackDTOs = feedbackService.getFeedbackByCourseWithLimitAndOffset(courseID, limit, offset);

        List<FeedbackWsTo> feedbackWsTos = controllerFeedbackMapper.mapList(feedbackDTOs);

        return ResponseEntity.ok( Response.of(feedbackWsTos, new Message(Message.SUCCESS)));
    }

    @Operation(
        security = { @SecurityRequirement(name = "Authorization") },
        description = "Get the average feedbackrate of a course"
    )
    @PreAuthorize("hasAuthority('user')")
    @GetMapping("/feedback/average/course/{courseID}")
    public ResponseEntity<Response> getAverageFeedbackByCourseID(@PathVariable Long courseID) {
        float averageFeedback = feedbackService.getAverageFeedbackByCourseID(courseID);
        return ResponseEntity.ok( Response.of(averageFeedback, new Message(Message.SUCCESS)));
    }

    @Operation(
        security = { @SecurityRequirement(name = "Authorization") },
        description = "Delete a feedback from a course"
    )
    @PreAuthorize("hasAuthority('user')")
    @DeleteMapping("/feedback/{feedbackID}")
    public ResponseEntity<Response> deleteFeedbackFromCourse(@PathVariable Long feedbackID) {

        feedbackService.deleteFeedbackFromCourse(feedbackID);

        return ResponseEntity.ok( Response.of(new Message(Message.SUCCESS)));
    }

    @Operation(
        security = { @SecurityRequirement(name = "Authorization") },
        description = "Update a feedback"
    )
    @PreAuthorize("hasAuthority('user')")
    @PutMapping("/feedback")
    public ResponseEntity<Response> updateFeedbackFromCourse(
            @RequestBody FeedbackUpdateWsTo feedbackUpdateWsTo
    ){

        feedbackService.updateFeedbackFromCourse(feedbackUpdateWsTo);

        FeedbackDTO feedbackDTO = feedbackService.getFeedbackDTOById(feedbackUpdateWsTo.feedbackID());

        FeedbackWsTo feedbackWsTo = controllerFeedbackMapper.map(feedbackDTO);

        return ResponseEntity.ok( Response.of(feedbackWsTo,new Message(Message.SUCCESS)));
    }
}