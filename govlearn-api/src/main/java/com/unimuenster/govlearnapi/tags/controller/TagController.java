package com.unimuenster.govlearnapi.tags.controller;

import com.unimuenster.govlearnapi.common.responsewrapper.Message;
import com.unimuenster.govlearnapi.common.responsewrapper.Response;
import com.unimuenster.govlearnapi.tags.controller.mapper.ControllerTagMapper;
import com.unimuenster.govlearnapi.tags.controller.wsto.*;
import com.unimuenster.govlearnapi.tags.service.TagService;
import com.unimuenster.govlearnapi.tags.service.dto.TagCreationDTO;
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
@RequestMapping("/api/v1/tags")
@Slf4j
public class TagController {

    private final ControllerTagMapper controllerTagMapper;
    private final TagService tagService;

    @Operation(
        security = { @SecurityRequirement(name = "Authorization") },
        description = "Create a tag."
    )
    @PreAuthorize("hasAuthority('user')")
    @PostMapping()
    public ResponseEntity<Response> createTags(
            @RequestBody TagCreationWsTo tagCreationWsTo
    ){

        TagCreationDTO tagCreationDTO = controllerTagMapper.map(tagCreationWsTo);

        tagService.createTag(tagCreationDTO);

        return ResponseEntity.ok(Response.of(true));
    }

    @Operation(
        security = { @SecurityRequirement(name = "Authorization") },
        description = "Get a list of all tags."
    )
    @PreAuthorize("hasAuthority('user')")
    @GetMapping()
    public ResponseEntity<Response> getTags() {

        List<TagDTO> tags = tagService.getTags();

        List<TagWsTo> tagWsTos = controllerTagMapper.mapList(tags);

        return ResponseEntity.ok( Response.of(tagWsTos, new Message(Message.SUCCESS)));
    }

    @Operation(
        security = { @SecurityRequirement(name = "Authorization") },
        description = "Get a tag by id."
    )
    @PreAuthorize("hasAuthority('user')")
    @GetMapping("/{id}")
    public ResponseEntity<Response> getTagById(@PathVariable Long id) {

        TagDTO tagById = tagService.getTagsById(id);

        TagWsTo map = controllerTagMapper.map(tagById);

        return ResponseEntity.ok( Response.of(map, new Message(Message.SUCCESS)));
    }

    @Operation(
        security = { @SecurityRequirement(name = "Authorization") },
        description = "Get all tags by categoryID."
    )
    @PreAuthorize("hasAuthority('user')")
    @GetMapping("/category/{categoryID}")
    public ResponseEntity<Response> getTagsByCategoryID(@PathVariable Long categoryID) {

        List<TagDTO> tags = tagService.getTagsByCategoryID(categoryID);

        List<TagWsTo> tagWsTos = controllerTagMapper.mapList(tags);

        return ResponseEntity.ok( Response.of(tagWsTos, new Message(Message.SUCCESS)));
    }

}
