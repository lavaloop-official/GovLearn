package com.unimuenster.govlearnapi.group.controller;

import com.unimuenster.govlearnapi.common.responsewrapper.Response;
import com.unimuenster.govlearnapi.group.controller.wsto.AllInvitationsWsTo;
import com.unimuenster.govlearnapi.group.controller.wsto.InvitationWsTo;
import com.unimuenster.govlearnapi.group.entity.Group;
import com.unimuenster.govlearnapi.group.service.GroupService;
import com.unimuenster.govlearnapi.group.service.InvitationService;
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
@RequestMapping("/api/v1/groups/invitations")
@Slf4j
public class InvitationController {

    private final AuthenticationService authenticationService;
    private final GroupService groupService;
    private final InvitationService invitationService;

    @Operation(
            security = { @SecurityRequirement(name = "Authorization") },
            description = "Create an invitation to a group."
    )
    @PreAuthorize("hasAuthority('user')")
    @PostMapping()
    public ResponseEntity sendInvitation(@RequestBody InvitationWsTo invitationWsTo) {

        UserEntity currentUser = authenticationService.getCurrentUser();

        boolean userAdmin = groupService.isUserAdmin(currentUser, invitationWsTo.getGroupId());

        if (!userAdmin) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }


        Group groupById = groupService.getGroupById(invitationWsTo.getGroupId());

        UserEntity userByEmail = authenticationService.getUserByEmail(invitationWsTo.getUserEmail());

        invitationService.sendInvitation(groupById, userByEmail);

        return ResponseEntity.ok(Response.of(true));
    }

    @Operation(
            security = { @SecurityRequirement(name = "Authorization") },
            description = "Get all invitations."
    )
    @PreAuthorize("hasAuthority('user')")
    @GetMapping()
    public ResponseEntity getAllInvitation() {

        UserEntity currentUser = authenticationService.getCurrentUser();

        AllInvitationsWsTo allInvitationsWsTo = invitationService.getInvitations(currentUser);

        return ResponseEntity.ok(Response.of(allInvitationsWsTo));
    }
}
