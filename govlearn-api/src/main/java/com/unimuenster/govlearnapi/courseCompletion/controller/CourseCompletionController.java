package com.unimuenster.govlearnapi.courseCompletion.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unimuenster.govlearnapi.common.responsewrapper.Response;
import com.unimuenster.govlearnapi.course.controller.wsto.CourseWsTo;
import com.unimuenster.govlearnapi.courseCompletion.service.CourseCompletionService;
import com.unimuenster.govlearnapi.user.entity.UserEntity;
import com.unimuenster.govlearnapi.user.service.AuthenticationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
@Slf4j
public class CourseCompletionController {
    private final AuthenticationService authenticationService;
    private final CourseCompletionService courseCompletionService;

    @Operation(
            security = { @SecurityRequirement(name = "Authorization") },
            description = "Return all courses a user has been completed."
    )
    @PreAuthorize("hasAuthority('user')")
    @GetMapping("/completions")
    public ResponseEntity<Response> getCourseCompletion(){

        UserEntity currentUser = authenticationService.getCurrentUser();
        List<CourseWsTo> completionList = courseCompletionService.getUsersCourseCompletion(currentUser); 

        return ResponseEntity.ok(Response.of(completionList, true));
    }


    @Operation(
            security = { @SecurityRequirement(name = "Authorization") },
            description = "Return whether course has been completed."
    )
    @PreAuthorize("hasAuthority('user')")
    @GetMapping("/completions/course/{courseId}")
    public ResponseEntity<Response> isCourseCompleted(@PathVariable Long courseId){

        UserEntity currentUser = authenticationService.getCurrentUser();
        Boolean completion = courseCompletionService.isCourseCompleted(currentUser, courseId);

        return ResponseEntity.ok(Response.of(completion , true));
    }


    @Operation(
        security = { @SecurityRequirement(name = "Authorization") },
        description = "Mark a course as completed."
        )
    @PreAuthorize("hasAuthority('user')")
    @PostMapping("/completions/course/{courseId}")
    public ResponseEntity<Response> addCourseCompletion(@PathVariable Long courseId){

        UserEntity currentUser = authenticationService.getCurrentUser();
        courseCompletionService.addCourseCompletion(currentUser, courseId);
        return ResponseEntity.ok(Response.of(true));
    }



    @Operation(
    security = { @SecurityRequirement(name = "Authorization") },
    description = "Remove a completed course."
    )
    @PreAuthorize("hasAuthority('user')")
    @DeleteMapping("/completions/course/{courseId}")
    public ResponseEntity<Response> deleteCourseCompletion(@PathVariable Long courseId){

        UserEntity currentUser = authenticationService.getCurrentUser();
        courseCompletionService.deleteCourseCompletion(currentUser,courseId);

        return ResponseEntity.ok(Response.of(true));
    }
}