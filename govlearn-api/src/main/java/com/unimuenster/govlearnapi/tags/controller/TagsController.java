package com.unimuenster.govlearnapi.tags.controller;

import com.unimuenster.govlearnapi.common.responsewrapper.Message;
import com.unimuenster.govlearnapi.common.responsewrapper.Response;
import com.unimuenster.govlearnapi.course.controller.mapper.ControllerCourseMapper;
import com.unimuenster.govlearnapi.course.controller.wsto.CourseCreationWsTo;
import com.unimuenster.govlearnapi.course.controller.wsto.CourseWsTo;
import com.unimuenster.govlearnapi.course.service.CourseService;
import com.unimuenster.govlearnapi.course.service.dto.CourseCreationDTO;
import com.unimuenster.govlearnapi.course.service.dto.CourseDTO;
import com.unimuenster.govlearnapi.tags.controller.mapper.ControllerTagMapper;
import com.unimuenster.govlearnapi.tags.controller.wsto.TagWsTo;
import com.unimuenster.govlearnapi.tags.controller.wsto.TagsCreationWsTo;
import com.unimuenster.govlearnapi.tags.entity.Tag;
import com.unimuenster.govlearnapi.tags.service.TagsService;
import com.unimuenster.govlearnapi.tags.service.dto.TagsCreationDTO;
import com.unimuenster.govlearnapi.tags.service.dto.TagsDTO;
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
@RequestMapping("/api/v1/tags")
@Slf4j
public class TagsController {

    private final ControllerTagMapper controllerTagMapper;
    private final TagsService tagsService;
    private  final AuthenticationService authenticationService;

    @Operation(
            security = { @SecurityRequirement(name = "Authorization") },
            description = "Create a tag."
    )
    @PreAuthorize("hasAuthority('user')")
    @PostMapping()
    public ResponseEntity<Response> createTags(
            @RequestBody TagsCreationWsTo tagsCreationWsTo
    ){

        TagsCreationDTO tagsCreationDTO = controllerTagMapper.map(tagsCreationWsTo);

        tagsService.createTag(tagsCreationDTO);

        return ResponseEntity.ok(Response.of(true));
    }

    @Operation(
            description = "Get a list of all tags."
    )
    @GetMapping()
    public ResponseEntity<Response> getTags() {

        List<TagsDTO> tags = tagsService.getTags();

        List<TagWsTo> tagWsTos = controllerTagMapper.mapList(tags);

        return ResponseEntity.ok( Response.of(tagWsTos, new Message(Message.SUCCESS)));
    }

    @Operation(
            description = "Get a tag by id."
    )
    @GetMapping("/{id}")
    public ResponseEntity<Response> getTagById(@PathVariable Long id) {

        TagsDTO tagById = tagsService.getTagsById(id);

        TagWsTo map = controllerTagMapper.map(tagById);

        return ResponseEntity.ok( Response.of(map, new Message(Message.SUCCESS)));
    }

    @GetMapping("/tags/user/{id}")
    public ResponseEntity<List<Tag>> getAllTagsbyUserId(@PathVariable(value = "userId") Long userId){

    }

}
