package com.unimuenster.govlearnapi.course.controller;

import com.unimuenster.govlearnapi.common.responsewrapper.Message;
import com.unimuenster.govlearnapi.common.responsewrapper.Response;
import com.unimuenster.govlearnapi.course.controller.mapper.ControllerCourseMapper;
import com.unimuenster.govlearnapi.course.controller.wsto.CourseCreationWsTo;
import com.unimuenster.govlearnapi.course.controller.wsto.CourseWsTo;
import com.unimuenster.govlearnapi.course.service.CourseService;
import com.unimuenster.govlearnapi.course.service.dto.CourseCreationDTO;
import com.unimuenster.govlearnapi.course.service.dto.CourseDTO;
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
@RequestMapping("/api/v1")
@Slf4j
public class CourseController {

    private final CourseService courseService;
    private final ControllerCourseMapper controllerCourseMapper;
    private final AuthenticationService authenticationService;

    @Operation(
            security = { @SecurityRequirement(name = "Authorization") },
            description = "Create a course."
    )
    @PreAuthorize("hasAuthority('user')")
    @PostMapping("/courses")
    public ResponseEntity<Response> createCourse(
            @RequestBody CourseCreationWsTo courseCreationWsTo
    ){
        UserEntity currentUser = authenticationService.getCurrentUser();

        CourseCreationDTO courseCreationDTO = controllerCourseMapper.map(courseCreationWsTo);

        courseService.createCourse(courseCreationDTO, currentUser);

        return ResponseEntity.ok(Response.of(true));
    }

    @Operation(
            description = "Get a list of all courses."
    )
    @GetMapping("/courses")
    public ResponseEntity<Response> getCourses() {

        List<CourseDTO> courses = courseService.getCourses();

        List<CourseWsTo> courseWsTos = controllerCourseMapper.mapList(courses);

        return ResponseEntity.ok( Response.of(courseWsTos, new Message(Message.SUCCESS)));
    }

    @Operation(
            description = "Get a course by id."
    )
    @GetMapping("/courses/{id}")
    public ResponseEntity<Response> getCourseById(@PathVariable Long id) {

        CourseDTO courseById = courseService.getCourseById(id);

        CourseWsTo map = controllerCourseMapper.map(courseById);

        return ResponseEntity.ok( Response.of(map, new Message(Message.SUCCESS)));
    }


}
