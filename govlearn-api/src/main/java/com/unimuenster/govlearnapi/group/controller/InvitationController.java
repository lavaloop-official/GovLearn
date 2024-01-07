package com.unimuenster.govlearnapi.group.controller;

import com.unimuenster.govlearnapi.common.responsewrapper.Response;
import com.unimuenster.govlearnapi.group.controller.wsto.InvitationWsTo;
import com.unimuenster.govlearnapi.group.controller.wsto.RetrieveInvitationWsTo;
import com.unimuenster.govlearnapi.group.controller.wsto.UpdateInvitationWsTo;
import com.unimuenster.govlearnapi.group.entity.Group;
import com.unimuenster.govlearnapi.group.entity.Invitation;
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

import java.util.List;

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
            description = "Create a invitations to a group."
    )
    @PreAuthorize("hasAuthority('user')")
    @PostMapping()
    public ResponseEntity sendInvitation(@RequestBody List<InvitationWsTo> invitationWsTo) {

        UserEntity currentUser = authenticationService.getCurrentUser();

        boolean userAdmin = groupService.isUserAdmin(currentUser, invitationWsTo.get(0).getGroupId());

        if (!userAdmin) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }


        Group groupById = groupService.getGroupById(invitationWsTo.get(0).getGroupId());

        List<UserEntity> userEntities = authenticationService.getListOfUsersByEmail(invitationWsTo);

        for (UserEntity userEntity : userEntities) {
            invitationService.sendInvitation(groupById, userEntity);
        }
        
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

        List<RetrieveInvitationWsTo> retrieveInvitationWsTos = invitationService.getInvitations(currentUser);

        return ResponseEntity.ok(Response.of(retrieveInvitationWsTos));
    }

    @Operation(
            security = { @SecurityRequirement(name = "Authorization") },
            description = "Update the state of an Invitation."
    )
    @PreAuthorize("hasAuthority('user')")
    @PutMapping()
    public ResponseEntity answerInvitation(@RequestBody UpdateInvitationWsTo updateInvitationWsTo) {

        UserEntity currentUser = authenticationService.getCurrentUser();

        Invitation invitation = invitationService.hasInvitation(currentUser, updateInvitationWsTo.getInvitationId());

        if ( updateInvitationWsTo.isAccept() ) {
            invitationService.acceptInvitation(invitation);
        } else {
            invitationService.declineInvitation(invitation);
        }

        return ResponseEntity.ok(Response.of(true));
    }

    @Operation(
            security = { @SecurityRequirement(name = "Authorization") },
            description = "Delete an Invitation."
    )
    @PreAuthorize("hasAuthority('user')")
    @DeleteMapping("/groupID/{groupID}/usermail/{usermail}")
    public ResponseEntity deleteInvitation(@PathVariable long groupID, @PathVariable String usermail) {

        if(!groupService.isUserAdmin(authenticationService.getCurrentUser(), groupID))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        invitationService.deleteInvitation(groupID, usermail);

        return ResponseEntity.ok(Response.of(true));
    }
}
