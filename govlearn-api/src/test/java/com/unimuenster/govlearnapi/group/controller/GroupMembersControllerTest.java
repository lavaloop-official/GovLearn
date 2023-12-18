package com.unimuenster.govlearnapi.group.controller;

import com.unimuenster.govlearnapi.common.responsewrapper.Response;
import com.unimuenster.govlearnapi.group.controller.wsto.AddMemberWsTo;
import com.unimuenster.govlearnapi.group.entity.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
class GroupMembersControllerTest extends GroupTestBase {
    @BeforeEach
    void setUp() {
        setCurrentUser(initializerService.getUser2());
    }

    @Test
    void addMemberTest() {

        AddMemberWsTo addMemberWsTo = AddMemberWsTo
                .builder()
                .groupId(getGroup().getId())
                .userId(1L)
                .build();

        ResponseEntity responseEntity = groupMembersController.addMember(addMemberWsTo);

        Optional<Group> byId = groupRepository.findById(getGroup().getId());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, byId.get().getMembers().size());
        assertEquals(1, byId.get().getMembers().get(0).getUser().getId());

    }

    @Test
    void getMembersTest() {

        addMember();

        ResponseEntity responseEntity = groupMembersController.getMembers(getGroup().getId());

        Response<List> memberIds = (Response<List>) responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, memberIds.getPayload().size());
        assertEquals(currentMember.getId(), memberIds.getPayload().get(0));

    }

    @Test
    void addMemberButNotAdmin() {

        setCurrentUser(initializerService.getUser1());

        AddMemberWsTo addMemberWsTo = AddMemberWsTo
                .builder()
                .groupId(getGroup().getId())
                .userId(1L)
                .build();

        ResponseEntity responseEntity = groupMembersController.addMember(addMemberWsTo);

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());

    }
}