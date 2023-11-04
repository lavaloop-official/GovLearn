package com.unimuenster.govlearnapi.tags.controller;

import com.unimuenster.govlearnapi.common.responsewrapper.Message;
import com.unimuenster.govlearnapi.common.responsewrapper.Response;
import com.unimuenster.govlearnapi.course.controller.mapper.ControllerCourseMapper;
import com.unimuenster.govlearnapi.course.controller.wsto.CourseWsTo;
import com.unimuenster.govlearnapi.course.service.CourseService;
import com.unimuenster.govlearnapi.course.service.dto.CourseDTO;
import com.unimuenster.govlearnapi.tags.controller.mapper.ControllerTagMapper;
import com.unimuenster.govlearnapi.tags.controller.wsto.AddTagToCourseWsTo;
import com.unimuenster.govlearnapi.tags.controller.wsto.DeleteTagFromCourseWsTo;
import com.unimuenster.govlearnapi.tags.controller.wsto.TagWsTo;
import com.unimuenster.govlearnapi.tags.service.TagService;
import com.unimuenster.govlearnapi.tags.service.dto.TagDTO;
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
public class CourseTagController {
    private final ControllerTagMapper controllerTagMapper;
    private final ControllerCourseMapper controllerCourseMapper;
    private final TagService tagService;
    private final CourseService courseService;

    @Operation(
            security = { @SecurityRequirement(name = "Authorization") },
            description = "Get all Tags of a course."
    )
    @PreAuthorize("hasAuthority('user')")
    @GetMapping("/tags/courses/{id}")
    public ResponseEntity<Response> getAllTagsByCourseId(@PathVariable long id){
        List<TagDTO> tagsByCourse = tagService.getTagsByCourse(id);

        List<TagWsTo> tagWsTos = controllerTagMapper.mapList(tagsByCourse);

        return ResponseEntity.ok( Response.of(tagWsTos, new Message(Message.SUCCESS)));
    }

    @Operation(
            description = "Get all courses with a tag."
    )
    @GetMapping("/courses/tag/{id}")
    public ResponseEntity<Response> getAllCoursesByTagId(@PathVariable Long id){

        List<CourseDTO> coursesDTO = courseService.getAllCoursesByTagId(id);
        List<CourseWsTo> courses = controllerCourseMapper.mapList(coursesDTO);

        return ResponseEntity.ok( Response.of(courses, new Message(Message.SUCCESS)));
    }

    @Operation(
            security = { @SecurityRequirement(name = "Authorization") },
            description = "Add a tag to a course."
    )
    @PreAuthorize("hasAuthority('user')")
    @PostMapping("tags/course")
    public ResponseEntity<Response> addTagToCourse(
            @RequestBody AddTagToCourseWsTo addTagToCourseWsTo
    ){
        CourseDTO courseDTO = courseService.getCourseById(addTagToCourseWsTo.courseId());
        CourseWsTo course = controllerCourseMapper.map(courseDTO);

        tagService.addTagToCourse(course.getId(), addTagToCourseWsTo.tagId() );
        return ResponseEntity.ok( Response.of(true));
    }

    @Operation(
            security = { @SecurityRequirement(name = "Authorization") },
            description = "remove tag from course."
    )
    @PreAuthorize("hasAuthority('user')")
    @DeleteMapping("/course")
    public ResponseEntity<Response> removeTagFromCourse(
            @RequestBody DeleteTagFromCourseWsTo deleteTagFromCourseWsTo
    ){
        CourseDTO courseDTO = courseService.getCourseById(deleteTagFromCourseWsTo.courseId());
        CourseWsTo course = controllerCourseMapper.map(courseDTO);
        tagService.deleteTagFromCourse(course, deleteTagFromCourseWsTo.tagId());

        return ResponseEntity.ok( Response.of(true));
    }
}
