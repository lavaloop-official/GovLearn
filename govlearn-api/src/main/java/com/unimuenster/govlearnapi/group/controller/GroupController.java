package com.unimuenster.govlearnapi.group.controller;

import com.unimuenster.govlearnapi.common.responsewrapper.Response;
import com.unimuenster.govlearnapi.course.controller.wsto.CourseCreationWsTo;
import com.unimuenster.govlearnapi.course.service.dto.CourseCreationDTO;
import com.unimuenster.govlearnapi.group.controller.mapper.GroupMapper;
import com.unimuenster.govlearnapi.group.controller.wsto.GroupAdminWsTo;
import com.unimuenster.govlearnapi.group.controller.wsto.GroupContentWsTo;
import com.unimuenster.govlearnapi.group.controller.wsto.GroupCreationWsTo;
import com.unimuenster.govlearnapi.group.service.GroupService;
import com.unimuenster.govlearnapi.group.service.dto.GroupDTO;
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
@RequestMapping("/api/v1/groups")
@Slf4j
public class GroupController {

    private final AuthenticationService authenticationService;
    private final GroupMapper groupMapper;
    private final GroupService groupService;

    // Gebraucht werden: POST, PUT, GET, DELETE

    @Operation(
            security = { @SecurityRequirement(name = "Authorization") },
            description = "Create a group."
    )
    @PreAuthorize("hasAuthority('user')")
    @PostMapping()
    public ResponseEntity<Response> createCourse(
            @RequestBody GroupCreationWsTo groupCreationWsTo
    ){
        UserEntity currentUser = authenticationService.getCurrentUser();

        GroupDTO groupDTO = groupMapper.mapToDto(groupCreationWsTo, currentUser.getId());

        groupService.createGroup(groupDTO);

        return ResponseEntity.ok(Response.of(true));
    }

    @Operation(
            security = { @SecurityRequirement(name = "Authorization") },
            description = "Get all groups of a user."
    )
    @PreAuthorize("hasAuthority('user')")
    @GetMapping("/member")
    public ResponseEntity<Response> getMemberGroup(){
        UserEntity currentUser = authenticationService.getCurrentUser();

        List<GroupContentWsTo> groupContents = groupService.getGroupsByMember(currentUser);

        return ResponseEntity.ok(Response.of(groupContents));
    }

    @Operation(
            security = { @SecurityRequirement(name = "Authorization") },
            description = "Get all groups of a user."
    )
    @PreAuthorize("hasAuthority('user')")
    @GetMapping("/admin")
    public ResponseEntity<Response> getAdminGroup(){
        UserEntity currentUser = authenticationService.getCurrentUser();

        List<GroupAdminWsTo> groupContents = groupService.getGroupsByAdmin(currentUser);

        return ResponseEntity.ok(Response.of(groupContents));
    }
}
