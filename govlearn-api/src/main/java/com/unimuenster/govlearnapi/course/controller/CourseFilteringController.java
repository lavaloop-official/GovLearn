package com.unimuenster.govlearnapi.course.controller;


import com.unimuenster.govlearnapi.common.responsewrapper.Message;
import com.unimuenster.govlearnapi.common.responsewrapper.Response;
import com.unimuenster.govlearnapi.course.controller.mapper.ControllerCourseMapper;
import com.unimuenster.govlearnapi.course.controller.wsto.CourseFilterWsTo;
import com.unimuenster.govlearnapi.course.controller.wsto.CourseWsTo;
import com.unimuenster.govlearnapi.course.service.CourseFilteringService;
import com.unimuenster.govlearnapi.course.service.dto.CourseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/filter")
@Slf4j
public class CourseFilteringController {

    private final CourseFilteringService courseFilteringService;
    private final ControllerCourseMapper controllerCourseMapper;

    @Operation(
        security = { @SecurityRequirement(name = "Authorization") },
        description = "Filter courses by attributes and categories."
    )
    @PreAuthorize("hasAuthority('user')")
    @PostMapping(value = {"/limit/{limit}/offset/{offset}/", "/limit/{limit}/offset/{offset}/{name-search}"})
    public ResponseEntity<Response> filterCourses(
        @PathVariable("limit") Integer limit,
        @PathVariable("offset") Integer offset, 
        @PathVariable("name-search") Optional<String> nameSearch, 
        @RequestBody CourseFilterWsTo courseFilterWsTo
    ) {

        List<CourseDTO> courseDTOS = courseFilteringService.filterCourses(limit, offset, nameSearch, courseFilterWsTo);

        List<CourseWsTo> courseWsTos = controllerCourseMapper.mapToCourseWsToWithRating(courseDTOS);

        return ResponseEntity.ok( Response.of(courseWsTos, new Message(Message.SUCCESS)));
    }
}
