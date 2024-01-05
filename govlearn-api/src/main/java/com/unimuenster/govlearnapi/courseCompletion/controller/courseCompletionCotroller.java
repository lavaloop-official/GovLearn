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
import com.unimuenster.govlearnapi.course.service.dto.CourseDTO;
import com.unimuenster.govlearnapi.courseCompletion.service.courseCompletionService;
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
public class courseCompletionCotroller {
    private final AuthenticationService authenticationService;
    private final courseCompletionService courseCompletionService;

    @Operation(
            security = { @SecurityRequirement(name = "Authorization") },
            description = "Gebe alle fertige Kurse des Users wieder."
    )
    @PreAuthorize("hasAuthority('user')")
    @GetMapping("/completion")
    public ResponseEntity<Response> getUsersCourseCompletion(){
        
        UserEntity currentUser = authenticationService.getCurrentUser();
        List<CourseDTO> completionList = courseCompletionService.getUsersCourseCompletion(currentUser); 

        return ResponseEntity.ok(Response.of(completionList,true));
    }


    @Operation(
            security = { @SecurityRequirement(name = "Authorization") },
            description = "Gebe wieder, ob Kurs fertig ist."
    )
    @PreAuthorize("hasAuthority('user')")
    @GetMapping("/completion/course")
    public ResponseEntity<Response> isCourseCompleted(Long courseId){

        UserEntity currentUser = authenticationService.getCurrentUser();
        Boolean completion = courseCompletionService.isCourseCompleted(currentUser, courseId);

        return ResponseEntity.ok(Response.of(completion , true));
    }
    
    
    @Operation(
        security = { @SecurityRequirement(name = "Authorization") },
        description = "Add Coursecompletions."
        )
    @PreAuthorize("hasAuthority('user')")
    @PostMapping("/completion/courses/{id}")
    public ResponseEntity<Response> addCourseCompletion(@PathVariable Long courseId){

        UserEntity currentUser = authenticationService.getCurrentUser();
        courseCompletionService.addCourseCompletion(currentUser, courseId);
        return ResponseEntity.ok(Response.of(true));
    }



    @Operation(
    security = { @SecurityRequirement(name = "Authorization") },
    description = "Delete Coursecompletions."
    )
    @PreAuthorize("hasAuthority('user')")
    @DeleteMapping("/completion/courses/{id}")
    public ResponseEntity<Response> deleteCourseCompletion(Long courseId){

        UserEntity currentUser = authenticationService.getCurrentUser();
        courseCompletionService.deleteCourseCompletion(currentUser,courseId);

        return ResponseEntity.ok(Response.of(true));
    }
}