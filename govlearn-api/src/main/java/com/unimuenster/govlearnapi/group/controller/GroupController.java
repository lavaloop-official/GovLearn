package com.unimuenster.govlearnapi.group.controller;

import com.unimuenster.govlearnapi.common.responsewrapper.Response;
import com.unimuenster.govlearnapi.group.service.GroupService;
import com.unimuenster.govlearnapi.user.entity.UserEntity;
import com.unimuenster.govlearnapi.user.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity createGroup() {
        UserEntity currentUser = authenticationService.getCurrentUser();

        groupService.createGroup(currentUser);

        return ResponseEntity.ok(Response.of(true));
    }
}
