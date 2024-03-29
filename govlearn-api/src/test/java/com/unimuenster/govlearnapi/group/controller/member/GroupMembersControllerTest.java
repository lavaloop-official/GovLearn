package com.unimuenster.govlearnapi.group.controller.member;

import com.unimuenster.govlearnapi.common.responsewrapper.Response;
import com.unimuenster.govlearnapi.group.controller.GroupTestBase;
import com.unimuenster.govlearnapi.group.controller.wsto.AddMemberWsTo;
import com.unimuenster.govlearnapi.group.entity.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
class GroupMembersControllerTest extends GroupTestBase {
    @BeforeEach
    void setUp() {
        setCurrentUser(initializerService.getUser1());
    }

    @Test
    void addMemberTest() {

        AddMemberWsTo addMemberWsTo = AddMemberWsTo
                .builder()
                .groupId(getGroup().getId())
                .userId(2L)
                .build();

        ResponseEntity responseEntity = groupMembersController.addMember(addMemberWsTo);

        Optional<Group> byId = groupRepository.findById(getGroup().getId());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(2, byId.get().getMembers().size());
        assertEquals(true, byId.get().getMembers().stream().map(member -> member.getUser().getId()).anyMatch(id -> id.equals(2L)));

    }

    @Test
    void getMembersTest() {

        addMember();

        ResponseEntity responseEntity = groupMembersController.getMembers(getGroup().getId());

        Response<List> memberIds = (Response<List>) responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(2, memberIds.getPayload().size());
    }

    @Test
    void addMemberButNotAdmin() {

        setCurrentUser(initializerService.getUser2());

        AddMemberWsTo addMemberWsTo = AddMemberWsTo
                .builder()
                .groupId(getGroup().getId())
                .userId(1L)
                .build();

        ResponseEntity responseEntity = groupMembersController.addMember(addMemberWsTo);

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());

    }
}