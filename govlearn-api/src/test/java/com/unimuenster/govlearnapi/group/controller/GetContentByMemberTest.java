package com.unimuenster.govlearnapi.group.controller;

import com.unimuenster.govlearnapi.group.entity.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
@Transactional
public class GetContentByMemberTest extends GroupTestBase {

    @BeforeEach
    void setUp() {
        setCurrentUser(initializerService.getUser2());

        addMember();
        addContent();
    }

    @Test
    void getContentByMember() {
        setCurrentUser(initializerService.getUser1());

        ResponseEntity responseEntity = groupContentController.getContent(getGroup().getId());

        Optional<Group> byId = groupRepository.findById(getGroup().getId());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, byId.get().getMembers().size());
        assertEquals(1, byId.get().getMembers().get(0).getCourses().size());
        assertEquals(initializerService.getCourse1().getId(), byId.get().getMembers().get(0).getCourses().get(0).getId());
    }

    @Test
    void getContentButNotPartOfGroup() {

        setCurrentUser(initializerService.getRecommendationUser());

        assertThrows(
                RuntimeException.class,
                () -> groupContentController.getContent(getGroup().getId())
        );
    }
}