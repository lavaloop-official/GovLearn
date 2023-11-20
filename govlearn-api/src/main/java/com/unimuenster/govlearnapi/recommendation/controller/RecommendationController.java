package com.unimuenster.govlearnapi.recommendation.controller;

import com.unimuenster.govlearnapi.common.responsewrapper.Response;
import com.unimuenster.govlearnapi.course.controller.mapper.ControllerCourseMapper;
import com.unimuenster.govlearnapi.course.controller.wsto.CourseWsTo;
import com.unimuenster.govlearnapi.course.service.dto.CourseDTO;
import com.unimuenster.govlearnapi.recommendation.controller.wsto.RecommendationBundleWsTo;
import com.unimuenster.govlearnapi.recommendation.service.RecommendationBundleService;
import com.unimuenster.govlearnapi.recommendation.service.RecommendationService;
import com.unimuenster.govlearnapi.user.entity.UserEntity;
import com.unimuenster.govlearnapi.user.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/recommendations")
@Slf4j
public class RecommendationController {

    private final RecommendationService recommendationService;
    private final RecommendationBundleService recommendationBundleService;
    private final AuthenticationService authenticationService;
    private final ControllerCourseMapper controllerCourseMapper;

    @Operation(
            security = { @SecurityRequirement(name = "Authorization") },
            description = "Get a recommendation."
    )
    @PreAuthorize("hasAuthority('user')")
    @GetMapping("/{length}")
    public ResponseEntity<Response> createCourse(
            @PathVariable int length
    ){
        UserEntity currentUser = authenticationService.getCurrentUser();

        List<CourseDTO> recommendations = recommendationService.getRecommendation(currentUser, length);

        List<CourseWsTo> list = recommendations.stream().map(course -> controllerCourseMapper.map(course)).toList();

        return ResponseEntity.ok(Response.of(list, true));
    }

    @Operation(
            security = { @SecurityRequirement(name = "Authorization") },
            description = "Get a recommendation bundle for the frontend landing page."
    )
    @PreAuthorize("hasAuthority('user')")
    @GetMapping("/bundle")
    public ResponseEntity<Response> getRecommendationBundle(){
        UserEntity currentUser = authenticationService.getCurrentUser();

        RecommendationBundleWsTo bundle = recommendationBundleService.getBundle(currentUser);

        return ResponseEntity.ok(Response.of(bundle, true));
    }
}
