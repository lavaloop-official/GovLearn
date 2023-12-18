package com.unimuenster.govlearnapi.group.controller;

import com.unimuenster.govlearnapi.group.controller.wsto.AddContentToGroupWsTo;
import com.unimuenster.govlearnapi.group.entity.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class AddContentToAllMembersTest extends GroupTestBase {

    @BeforeEach
    void setUp() {
        setCurrentUser(initializerService.getUser2());

        addMember();
    }


    @Test
    void addContentToAll() {

        Long courseId = 1L;

        AddContentToGroupWsTo addContentToGroupWsTo = AddContentToGroupWsTo
                .builder()
                .groupId(getGroup().getId())
                .courseId(courseId)
                .build();


        ResponseEntity responseEntity = groupContentController.addContentToAll(addContentToGroupWsTo);

        Optional<Group> byId = groupRepository.findById(getGroup().getId());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, byId.get().getMembers().size());
        assertEquals(1, byId.get().getMembers().get(0).getCourses().size());
        assertEquals(courseId, byId.get().getMembers().get(0).getCourses().get(0).getId());
    }

    @Test
    void addContentButNotAdmin() {

        setCurrentUser(initializerService.getUser1());

        AddContentToGroupWsTo addContentToGroupWsTo = AddContentToGroupWsTo
                .builder()
                .groupId(getGroup().getId())
                .courseId(1L)
                .build();


        ResponseEntity responseEntity = groupContentController.addContentToAll(addContentToGroupWsTo);

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }
}