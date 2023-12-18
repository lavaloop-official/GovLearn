package com.unimuenster.govlearnapi.course.controller;

import com.unimuenster.govlearnapi.common.responsewrapper.Message;
import com.unimuenster.govlearnapi.common.responsewrapper.Response;
import com.unimuenster.govlearnapi.course.controller.mapper.ControllerCourseMapper;
import com.unimuenster.govlearnapi.course.controller.wsto.CourseCreationWsTo;
import com.unimuenster.govlearnapi.course.controller.wsto.CourseWsTo;
import com.unimuenster.govlearnapi.course.entity.Course;
import com.unimuenster.govlearnapi.course.service.CourseService;
import com.unimuenster.govlearnapi.course.service.dto.CourseCreationDTO;
import com.unimuenster.govlearnapi.course.service.dto.CourseDTO;
import com.unimuenster.govlearnapi.feedback.service.FeedbackService;
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
    private final FeedbackService feedbackService;

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

        Course createdCourse = courseService.createCourse(courseCreationDTO, currentUser);
        CourseDTO courseDTO = courseService.getCourseById(createdCourse.getId());
        return ResponseEntity.ok(Response.of(controllerCourseMapper.map(courseDTO), new Message(Message.SUCCESS)));
    }

    @Operation(
        security = { @SecurityRequirement(name = "Authorization") },
        description = "Get a list of all courses."
    )
    @PreAuthorize("hasAuthority('user')")
    @GetMapping("/courses")
    public ResponseEntity<Response> getCourses() {

        List<CourseDTO> courses = courseService.getCourses();

        List<CourseWsTo> courseWsTos = controllerCourseMapper.mapList(courses);

        for (CourseWsTo courseWsTo : courseWsTos) {
            courseWsTo.setRatingAverage(feedbackService.getAverageFeedbackByCourseID(courseWsTo.getId()));
            courseWsTo.setRatingAmount(feedbackService.getAmountFeedbackByCourseID(courseWsTo.getId()));
        }

        return ResponseEntity.ok( Response.of(courseWsTos, new Message(Message.SUCCESS)));
    }

    @Operation(
        security = { @SecurityRequirement(name = "Authorization") },
        description = "Get a course by id."
    )
    @PreAuthorize("hasAuthority('user')")
    @GetMapping("/courses/{id}")
    public ResponseEntity<Response> getCourseById(@PathVariable Long id) {

        CourseDTO courseById = courseService.getCourseById(id);

        CourseWsTo courseWsTo = controllerCourseMapper.map(courseById);

        courseWsTo.setRatingAverage(feedbackService.getAverageFeedbackByCourseID(courseWsTo.getId()));
        courseWsTo.setRatingAmount(feedbackService.getAmountFeedbackByCourseID(courseWsTo.getId()));

        return ResponseEntity.ok( Response.of(courseWsTo, new Message(Message.SUCCESS)));
    }

    @Operation(
            security = { @SecurityRequirement(name = "Authorization") },
            description = "Delete a course"
    )
    @PreAuthorize("hasAuthority('user')")
    @DeleteMapping("/courses/{id}")
    public ResponseEntity<Response> deleteCourse(@PathVariable Long id) {
        // Übergebe User um Berechtigung des Löschens zu prüfen
        UserEntity currentUser = authenticationService.getCurrentUser();
        courseService.deleteCourse(id, currentUser.getId());

        return ResponseEntity.ok( Response.of(new Message(Message.SUCCESS)));
    }

    @Operation(
            security = { @SecurityRequirement(name = "Authorization") },
            description = "Get courses the user created"
    )
    @PreAuthorize("hasAuthority('user')")
    @GetMapping("/providers/courses")
    public ResponseEntity<Response> getProvidedCourses() {

        UserEntity currentUser = authenticationService.getCurrentUser();
        List<CourseDTO> courseDTOs = courseService.getProvidedCourses(currentUser.getId());

        List<CourseWsTo> courseWsTos = controllerCourseMapper.mapList(courseDTOs);

        for (CourseWsTo courseWsTo : courseWsTos) {
            courseWsTo.setRatingAverage(feedbackService.getAverageFeedbackByCourseID(courseWsTo.getId()));
            courseWsTo.setRatingAmount(feedbackService.getAmountFeedbackByCourseID(courseWsTo.getId()));
        }
        return ResponseEntity.ok( Response.of(courseWsTos, new Message(Message.SUCCESS)));
    }

}
