package com.unimuenster.govlearnapi.course.controller;


import com.unimuenster.govlearnapi.common.responsewrapper.Message;
import com.unimuenster.govlearnapi.common.responsewrapper.Response;
import com.unimuenster.govlearnapi.course.controller.mapper.ControllerCourseMapper;
import com.unimuenster.govlearnapi.course.controller.wsto.CourseCreationWsTo;
import com.unimuenster.govlearnapi.course.controller.wsto.CourseWsTo;
import com.unimuenster.govlearnapi.course.service.CourseFilteringService;
import com.unimuenster.govlearnapi.course.service.dto.CourseDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/filter")
@Slf4j
public class CourseFilteringController {

    private final CourseFilteringService courseFilteringService;
    private final ControllerCourseMapper controllerCourseMapper;

    @Operation(
            description = "Filter courses by attributes and categories."
    )
    @PostMapping("/{name-search}")
    public ResponseEntity<Response> filterCourses(@PathVariable("name-search") String nameSearch, 
        @RequestBody List<Long> categoryIDs) {

        List<CourseDTO> courseDTOS = courseFilteringService.filterCourses(nameSearch, categoryIDs);

        List<CourseWsTo> courseWsTos = controllerCourseMapper.mapList(courseDTOS);

        return ResponseEntity.ok( Response.of(courseWsTos, new Message(Message.SUCCESS)));
    }
}
