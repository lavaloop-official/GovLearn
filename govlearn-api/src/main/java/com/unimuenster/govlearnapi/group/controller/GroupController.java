package com.unimuenster.govlearnapi.group.controller;

import com.unimuenster.govlearnapi.common.responsewrapper.Response;
import com.unimuenster.govlearnapi.group.controller.wsto.GetGroupsWsTo;
import com.unimuenster.govlearnapi.group.controller.wsto.GroupCreationWsTo;
import com.unimuenster.govlearnapi.group.controller.wsto.GroupDetailsWsTo;
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
@RequestMapping("/api/v1/groups")
@Slf4j
public class GroupController {

    private final AuthenticationService authenticationService;
    private final GroupService groupService;

    @Operation(
            security = { @SecurityRequirement(name = "Authorization") },
            description = "Create a group and become admin of this group."
    )
    @PreAuthorize("hasAuthority('user')")
    @PostMapping()
    public ResponseEntity createGroup(
            @RequestBody GroupCreationWsTo groupCreationWsTo
            ) {
        UserEntity currentUser = authenticationService.getCurrentUser();

        groupService.createGroup(currentUser, groupCreationWsTo);

        return ResponseEntity.ok(Response.of(true));
    }

    @Operation(
            security = { @SecurityRequirement(name = "Authorization") },
            description = "Gets all groups this user is an admin and member of."
    )
    @PreAuthorize("hasAuthority('user')")
    @GetMapping()
    public ResponseEntity getGroups() {
        UserEntity currentUser = authenticationService.getCurrentUser();

        GetGroupsWsTo getGroupsWsTo = new GetGroupsWsTo();

        getGroupsWsTo.setAdminGroups(groupService.getAdminGroups(currentUser));
        getGroupsWsTo.setMemberGroups(groupService.getMemberGroups(currentUser));

        return ResponseEntity.ok(Response.of(getGroupsWsTo));
    }

    @Operation(
            security = { @SecurityRequirement(name = "Authorization") },
            description = "Get group details."
    )
    @PreAuthorize("hasAuthority('user')")
    @GetMapping("/{groupId}")
    public ResponseEntity getGroupDetails(@PathVariable Long groupId) {
        UserEntity currentUser = authenticationService.getCurrentUser();

        boolean userAdmin = groupService.isUserAdmin(currentUser, groupId);
        boolean isMember = groupService.isUserMember(currentUser, groupId);

        if ( !userAdmin && !isMember ) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        GroupDetailsWsTo groupDetails = groupService.getGroupDetails(groupId);

        return ResponseEntity.ok(Response.of(groupDetails));
    }
}
