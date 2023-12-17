package com.unimuenster.govlearnapi.group.controller;

import com.unimuenster.govlearnapi.group.controller.wsto.AddMemberWsTo;
import com.unimuenster.govlearnapi.group.entity.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class GroupMembersControllerTest extends GroupTestBase {

    @BeforeEach
    void setUp() {
        setCurrentUser(initializerService.getUser2());

        createGroup();
    }

    @Test
    void addMemberTest() {

        AddMemberWsTo addMemberWsTo = AddMemberWsTo
                .builder()
                .groupId(group.getId())
                .userId(1L)
                .build();

        ResponseEntity responseEntity = groupMembersController.addMember(addMemberWsTo);

        Optional<Group> byId = groupRepository.findById(group.getId());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, byId.get().getMembers().size());
        assertEquals(1, byId.get().getMembers().get(0).getUser().getId());

    }

    @Test
    void addMemberButNotAdmin() {

        setCurrentUser(initializerService.getUser1());

        AddMemberWsTo addMemberWsTo = AddMemberWsTo
                .builder()
                .groupId(group.getId())
                .userId(1L)
                .build();

        ResponseEntity responseEntity = groupMembersController.addMember(addMemberWsTo);

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());

    }
}