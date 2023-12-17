package com.unimuenster.govlearnapi.group.controller;

import com.unimuenster.govlearnapi.common.responsewrapper.Response;
import com.unimuenster.govlearnapi.group.controller.wsto.AddContentToMemberWsTo;
import com.unimuenster.govlearnapi.group.controller.wsto.AddMemberWsTo;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @PutMapping()
    public ResponseEntity addContent(
            @RequestBody AddContentToMemberWsTo addContentToMemberWsTo
    ) {

        UserEntity currentUser = authenticationService.getCurrentUser();

        Group group = groupService.findGroupByMemberId(addContentToMemberWsTo.getMemberId());

        boolean userAdmin = groupService.isUserAdmin(currentUser, group.getId());

        if (!userAdmin) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        groupService.addContentToMember(addContentToMemberWsTo.getMemberId(), addContentToMemberWsTo.getCourseId());

        return ResponseEntity.ok(Response.of(true));

    }
}
