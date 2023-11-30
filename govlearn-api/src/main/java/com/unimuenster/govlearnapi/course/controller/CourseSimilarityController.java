package com.unimuenster.govlearnapi.course.controller;

import com.unimuenster.govlearnapi.common.responsewrapper.Message;
import com.unimuenster.govlearnapi.common.responsewrapper.Response;
import com.unimuenster.govlearnapi.course.controller.mapper.ControllerCourseMapper;
import com.unimuenster.govlearnapi.course.controller.wsto.CourseWsTo;
import com.unimuenster.govlearnapi.course.service.CourseService;
import com.unimuenster.govlearnapi.course.service.CourseSimilarityService;
import com.unimuenster.govlearnapi.course.service.dto.CourseDTO;
import com.unimuenster.govlearnapi.tags.service.TagService;
import com.unimuenster.govlearnapi.tags.service.dto.TagDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/similiar-courses")
@Slf4j
public class CourseSimilarityController {
    private final TagService tagService;
    private final CourseSimilarityService courseSimilarityService;
    private final ControllerCourseMapper controllerCourseMapper;
    @Operation(
            security = { @SecurityRequirement(name = "Authorization") },
            description = "Get similar courses."
    )
    @PreAuthorize("hasAuthority('user')")
    @GetMapping("/{id}/")
    public ResponseEntity<Response> getSimiliarCourses(@PathVariable Long id) {
        List<TagDTO> allTags = tagService.getTags();
        List<CourseDTO> similiarCourses = courseSimilarityService.getMostSimiliarCourses(id,allTags);

        List<CourseWsTo> courseWsTos = controllerCourseMapper.mapList(similiarCourses);

        return ResponseEntity.ok( Response.of(courseWsTos, new Message(Message.SUCCESS)));
    }
}
