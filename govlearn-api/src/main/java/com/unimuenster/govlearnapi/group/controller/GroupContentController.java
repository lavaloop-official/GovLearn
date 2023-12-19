package com.unimuenster.govlearnapi.group.controller;

import com.unimuenster.govlearnapi.common.responsewrapper.Response;
import com.unimuenster.govlearnapi.group.controller.wsto.*;
import com.unimuenster.govlearnapi.group.entity.Group;
import com.unimuenster.govlearnapi.group.service.GroupService;
import com.unimuenster.govlearnapi.user.entity.UserEntity;
import com.unimuenster.govlearnapi.user.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/groups/content")
@Slf4j
public class GroupContentController {

    private final AuthenticationService authenticationService;
    private final GroupService groupService;

    @Operation(
            security = { @SecurityRequirement(name = "Authorization") },
            description = "Add content for a group member, returns forbidden, if the current user is not admin of the group."
    )
    @PreAuthorize("hasAuthority('user')")
    @PostMapping()
    public ResponseEntity addContent(
            @RequestBody AddContentToMemberWsTo addContentToMemberWsTo
    ) {

        UserEntity currentUser = authenticationService.getCurrentUser();

        // Using then actual memeber Id here
        Group group = groupService.findGroupByMemberId(addContentToMemberWsTo.getMemberId());

        if (group == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        boolean userAdmin = groupService.isUserAdmin(currentUser, group.getId());

        if (!userAdmin) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        groupService.addContentToMember(addContentToMemberWsTo.getMemberId(), addContentToMemberWsTo.getCourseId());

        return ResponseEntity.ok(Response.of(true));

    }

    @Operation(
            security = { @SecurityRequirement(name = "Authorization") },
            description = "Add content to an entire group, returns forbidden, if the current user is not admin of the group."
    )
    @PreAuthorize("hasAuthority('user')")
    @PutMapping("/all")
    public ResponseEntity addContentToAll(
            @RequestBody AddContentToGroupWsTo addContentToGroupWsTo
    ) {

        UserEntity currentUser = authenticationService.getCurrentUser();

        boolean userAdmin = groupService.isUserAdmin(currentUser, addContentToGroupWsTo.getGroupId());

        if (!userAdmin) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        groupService.addContentToGroup(addContentToGroupWsTo.getGroupId(), addContentToGroupWsTo.getCourseId());

        return ResponseEntity.ok(Response.of(true));

    }

    @Operation(
            security = { @SecurityRequirement(name = "Authorization") },
            description = "Get content based on user."
    )
    @PreAuthorize("hasAuthority('user')")
    @GetMapping("/{groupId}")
    public ResponseEntity getContent(
            @PathVariable Long groupId
    ) {
        UserEntity currentUser = authenticationService.getCurrentUser();

        GroupContentWsTo groupContentWsTo = groupService.getContent(currentUser, groupId);

        return ResponseEntity.ok(Response.of(groupContentWsTo));
    }

    @Operation(
            security = { @SecurityRequirement(name = "Authorization") },
            description = "Delete courses for all members."
    )
    @PreAuthorize("hasAuthority('user')")
    @DeleteMapping()
    public ResponseEntity deleteContent(
            @RequestBody DeleteContentForGroupWsTo deleteContentForGroupWsTo
    ) {
        UserEntity currentUser = authenticationService.getCurrentUser();

        boolean userAdmin = groupService.isUserAdmin(currentUser, deleteContentForGroupWsTo.getGroupId());

        if (!userAdmin) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        int deleteCount = groupService.deleteContentForGroup(deleteContentForGroupWsTo);

        return ResponseEntity.ok(Response.of((deleteCount), true));
    }
}
