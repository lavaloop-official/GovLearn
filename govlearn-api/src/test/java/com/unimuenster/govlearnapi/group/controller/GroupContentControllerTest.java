package com.unimuenster.govlearnapi.group.controller;

import com.unimuenster.govlearnapi.group.controller.wsto.AddContentToMemberWsTo;
import com.unimuenster.govlearnapi.group.entity.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
@Transactional
public class GroupContentControllerTest extends GroupTestBase {
    @BeforeEach
    void setUp() {
        setCurrentUser(initializerService.getUser2());

        addMember();
    }

    @Test
    void addContentTest() {

        Long courseId = 1L;

        AddContentToMemberWsTo addMemberWsTo = AddContentToMemberWsTo
                .builder()
                .memberId(currentMember.getId())
                .courseId(courseId)
                .build();


        ResponseEntity responseEntity = groupContentController.addContent(addMemberWsTo);

        Optional<Group> byId = groupRepository.findById(getGroup().getId());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, byId.get().getMembers().size());
        assertEquals(1, byId.get().getMembers().get(0).getCourses().size());
        assertEquals(courseId, byId.get().getMembers().get(0).getCourses().get(0).getId());
    }
    @Test
    void addContentButNotAdmin() {

        setCurrentUser(initializerService.getUser1());

        AddContentToMemberWsTo addMemberWsTo = AddContentToMemberWsTo
                .builder()
                .memberId(currentMember.getId())
                .courseId(1L)
                .build();

        ResponseEntity responseEntity = groupContentController.addContent(addMemberWsTo);

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());

    }
}