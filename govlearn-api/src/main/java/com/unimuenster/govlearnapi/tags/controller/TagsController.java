package com.unimuenster.govlearnapi.tags.controller;

import com.unimuenster.govlearnapi.common.responsewrapper.Message;
import com.unimuenster.govlearnapi.common.responsewrapper.Response;
import com.unimuenster.govlearnapi.tags.controller.mapper.ControllerTagMapper;
import com.unimuenster.govlearnapi.tags.controller.wsto.AddTagToUserWsTo;
import com.unimuenster.govlearnapi.tags.controller.wsto.DeleteTagFromUserWsTo;
import com.unimuenster.govlearnapi.tags.controller.wsto.TagWsTo;
import com.unimuenster.govlearnapi.tags.controller.wsto.TagsCreationWsTo;
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
import java.util.Set;

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

    @Operation(
            security = { @SecurityRequirement(name = "Authorization") },
            description = "Create a tag."
    )
    @PreAuthorize("hasAuthority('user')")
    @GetMapping("/user")
    public ResponseEntity<Response> getAllTagsbyUserId(){
        UserEntity currentUser = authenticationService.getCurrentUser();

        List<TagsDTO> tagsByUser = tagsService.getTagsByUser(currentUser.getId());

        List<TagWsTo> tagWsTos = controllerTagMapper.mapList(tagsByUser);

        return ResponseEntity.ok( Response.of(tagWsTos, new Message(Message.SUCCESS)));
    }

    @Operation(
            security = { @SecurityRequirement(name = "Authorization") },
            description = "Create a tag."
    )
    @PreAuthorize("hasAuthority('user')")
    @PostMapping("/user")
    public ResponseEntity<Response> addTagToUser(
            @RequestBody AddTagToUserWsTo addTagToUserWsTo
    ){
        UserEntity currentUser = authenticationService.getCurrentUser();

        tagsService.addTagToUser(currentUser, addTagToUserWsTo.tagId());

        return ResponseEntity.ok( Response.of(true));
    }

    @Operation(
            security = { @SecurityRequirement(name = "Authorization") },
            description = "Create a tag."
    )
    @PreAuthorize("hasAuthority('user')")
    @DeleteMapping("/user")
    public ResponseEntity<Response> removeTagFromUser(
            @RequestBody DeleteTagFromUserWsTo deleteTagFromUserWsTo
    ){
        UserEntity currentUser = authenticationService.getCurrentUser();

        tagsService.deleteTagFromUser(currentUser, deleteTagFromUserWsTo.tagId());

        return ResponseEntity.ok( Response.of(true));
    }
}
