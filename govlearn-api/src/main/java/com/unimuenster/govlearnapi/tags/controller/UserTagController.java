package com.unimuenster.govlearnapi.tags.controller;

import com.unimuenster.govlearnapi.common.responsewrapper.Message;
import com.unimuenster.govlearnapi.common.responsewrapper.Response;
import com.unimuenster.govlearnapi.tags.controller.mapper.ControllerTagMapper;
import com.unimuenster.govlearnapi.tags.controller.wsto.AddTagToUserWsTo;
import com.unimuenster.govlearnapi.tags.controller.wsto.DeleteTagFromUserWsTo;
import com.unimuenster.govlearnapi.tags.controller.wsto.TagWsTo;
import com.unimuenster.govlearnapi.tags.service.TagService;
import com.unimuenster.govlearnapi.tags.service.UserTagService;
import com.unimuenster.govlearnapi.tags.service.dto.TagDTO;
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
public class UserTagController {
    private final AuthenticationService authenticationService;
    private final TagService tagService;
    private final UserTagService userTagService;
    private final ControllerTagMapper controllerTagMapper;

    @Operation(
            security = { @SecurityRequirement(name = "Authorization") },
            description = "Get all Tags of a user."
    )
    @PreAuthorize("hasAuthority('user')")
    @GetMapping("/tags/users")
    public ResponseEntity<Response> getAllTagsByUserId(){
        UserEntity currentUser = authenticationService.getCurrentUser();

        List<TagDTO> tagsByUser = tagService.getTagsByUser(currentUser.getId());

        List<TagWsTo> tagWsTos = controllerTagMapper.mapList(tagsByUser);

        return ResponseEntity.ok( Response.of(tagWsTos, new Message(Message.SUCCESS)));
    }

    @Operation(
            security = { @SecurityRequirement(name = "Authorization") },
            description = "Add a tag to the user."
    )
    @PreAuthorize("hasAuthority('user')")
    @PostMapping("/tags/users")
    public ResponseEntity<Response> addTagToUser(
            @RequestBody AddTagToUserWsTo addTagToUserWsTo
    ){
        UserEntity currentUser = authenticationService.getCurrentUser();

        userTagService.addTagToUser(currentUser, addTagToUserWsTo);

        return ResponseEntity.ok( Response.of(true));
    }

    @Operation(
            security = { @SecurityRequirement(name = "Authorization") },
            description = "remove tag from user."
    )
    @PreAuthorize("hasAuthority('user')")
    @DeleteMapping("/tags/users")
    public ResponseEntity<Response> removeTagFromUser(
            @RequestBody DeleteTagFromUserWsTo deleteTagFromUserWsTo
    ){
        UserEntity currentUser = authenticationService.getCurrentUser();

        tagService.deleteTagFromUser(currentUser, deleteTagFromUserWsTo.tagId());

        return ResponseEntity.ok( Response.of(true));
    }
}
