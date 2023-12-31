package com.unimuenster.govlearnapi.group.controller;

import com.unimuenster.govlearnapi.common.responsewrapper.Response;
import com.unimuenster.govlearnapi.core.config.enums.Role;
import com.unimuenster.govlearnapi.group.controller.wsto.AddMemberWsTo;
import com.unimuenster.govlearnapi.group.controller.wsto.MemberDetailsWsTo;
import com.unimuenster.govlearnapi.group.entity.Group;
import com.unimuenster.govlearnapi.group.entity.Member;
import com.unimuenster.govlearnapi.group.service.GroupService;
import com.unimuenster.govlearnapi.group.service.MemberService;
import com.unimuenster.govlearnapi.user.controller.wsto.UserWsTo;
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
@RequestMapping("/api/v1/groups")
@Slf4j
public class GroupMembersController {

    private final AuthenticationService authenticationService;
    private final GroupService groupService;
    private final MemberService memberService;

    @Operation(
            security = { @SecurityRequirement(name = "Authorization") },
            description = "Add a member to the group, returns forbidden, if the current user is not admin of the group."
    )
    @PreAuthorize("hasAuthority('user')")
    @PostMapping("/members")
    public ResponseEntity addMember(
            @RequestBody AddMemberWsTo addMemberWsTo
    ) {

        UserEntity currentUser = authenticationService.getCurrentUser();

        boolean userAdmin = groupService.isUserAdmin(currentUser, addMemberWsTo.getGroupId());

        if (!userAdmin) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Long memberId = groupService.addMember(addMemberWsTo.getUserId(), addMemberWsTo.getGroupId(), Role.Member);

        return ResponseEntity.ok(Response.of(memberId));

    }

    @Operation(
            security = { @SecurityRequirement(name = "Authorization") },
            description = "Get all members of this group."
    )
    @PreAuthorize("hasAuthority('user')")
    @GetMapping("/{groupId}/members")
    public ResponseEntity getMembers(
            @PathVariable Long groupId
    ) {

        UserEntity currentUser = authenticationService.getCurrentUser();

        boolean userMemberOrAdmin = (groupService.isUserMember(currentUser, groupId) || groupService.isUserAdmin(currentUser, groupId));

        if (!userMemberOrAdmin) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        List<MemberDetailsWsTo> members = groupService.getMembers(groupId);

        return ResponseEntity.ok(Response.of(members));

    }

    @Operation(
            security = { @SecurityRequirement(name = "Authorization") },
            description = "Get details regarding a member."
    )
    @PreAuthorize("hasAuthority('user')")
    @GetMapping("/members/{memberId}")
    public ResponseEntity getMemberById(
            @PathVariable Long memberId
    ) {

        UserEntity currentUser = authenticationService.getCurrentUser();

        Group groupByMemberId = groupService.findGroupByMemberId(memberId);

        if (groupByMemberId == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        boolean userAdmin = groupService.isUserAdmin(currentUser, groupByMemberId.getId());

        if (!userAdmin) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        MemberDetailsWsTo memberDetails = memberService.getMember(memberId);

        return ResponseEntity.ok(Response.of(memberDetails));
    }

    @Operation(
            security = { @SecurityRequirement(name = "Authorization") },
            description = "Delete a member. Only allowed as an admin."
    )
    @PreAuthorize("hasAuthority('user')")
    @DeleteMapping("/{memberId}")
    public ResponseEntity deleteMember(
            @PathVariable Long memberId
    ) {

        UserEntity currentUser = authenticationService.getCurrentUser();

        Group groupByMemberId = groupService.findGroupByMemberId(memberId);

        if (groupByMemberId == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        boolean userAdmin = groupService.isUserAdmin(currentUser, groupByMemberId.getId());

        if (!userAdmin) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        memberService.removeMember(memberId);

        return ResponseEntity.ok(Response.of(true));
    }

    @Operation(
            security = { @SecurityRequirement(name = "Authorization") },
            description = "Allows a member to leave a group."
    )
    @PreAuthorize("hasAuthority('user')")
    @DeleteMapping("/members/removes/{groupID}")
    public ResponseEntity leaveGroup(
        @PathVariable long groupID
    ) {

        UserEntity currentUser = authenticationService.getCurrentUser();

        Group group = groupService.getGroupById(groupID);

        MemberDetailsWsTo memberDetailsWsTo = memberService.getMemberByUserIDAndGroupID(currentUser.getId(), groupID);

        if (group == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        boolean member = groupService.isUserMember(currentUser, groupID);

        if (!member) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        memberService.removeMember(memberDetailsWsTo.getMemberId());

        return ResponseEntity.ok(Response.of(true));
    }
}
