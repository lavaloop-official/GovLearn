package com.unimuenster.govlearnapi.tags.controller;

import com.unimuenster.govlearnapi.common.responsewrapper.Message;
import com.unimuenster.govlearnapi.common.responsewrapper.Response;
import com.unimuenster.govlearnapi.tags.controller.mapper.ControllerTagMapper;
import com.unimuenster.govlearnapi.tags.controller.wsto.AddTagToUserWsTo;
import com.unimuenster.govlearnapi.tags.controller.wsto.DeleteTagFromUserWsTo;
import com.unimuenster.govlearnapi.tags.controller.wsto.TagWsTo;
import com.unimuenster.govlearnapi.tags.service.TagsService;
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
@RequestMapping("/api/v1")
@Slf4j
public class UserTagsController {
    private final AuthenticationService authenticationService;
    private final TagsService tagsService;
    private final ControllerTagMapper controllerTagMapper;

    @Operation(
            security = { @SecurityRequirement(name = "Authorization") },
            description = "Get all Tags of a user."
    )
    @PreAuthorize("hasAuthority('user')")
    @GetMapping("/tag/user")
    public ResponseEntity<Response> getAllTagsByUserId(){
        UserEntity currentUser = authenticationService.getCurrentUser();

        List<TagsDTO> tagsByUser = tagsService.getTagsByUser(currentUser.getId());

        List<TagWsTo> tagWsTos = controllerTagMapper.mapList(tagsByUser);

        return ResponseEntity.ok( Response.of(tagWsTos, new Message(Message.SUCCESS)));
    }

    @Operation(
            security = { @SecurityRequirement(name = "Authorization") },
            description = "Add a tag to the user."
    )
    @PreAuthorize("hasAuthority('user')")
    @PostMapping("/tag/user")
    public ResponseEntity<Response> addTagToUser(
            @RequestBody AddTagToUserWsTo addTagToUserWsTo
    ){
        UserEntity currentUser = authenticationService.getCurrentUser();

        tagsService.addTagToUser(currentUser, addTagToUserWsTo.tagId());

        return ResponseEntity.ok( Response.of(true));
    }

    @Operation(
            security = { @SecurityRequirement(name = "Authorization") },
            description = "remove tag from user."
    )
    @PreAuthorize("hasAuthority('user')")
    @DeleteMapping("/tag/user")
    public ResponseEntity<Response> removeTagFromUser(
            @RequestBody DeleteTagFromUserWsTo deleteTagFromUserWsTo
    ){
        UserEntity currentUser = authenticationService.getCurrentUser();

        tagsService.deleteTagFromUser(currentUser, deleteTagFromUserWsTo.tagId());

        return ResponseEntity.ok( Response.of(true));
    }
}
