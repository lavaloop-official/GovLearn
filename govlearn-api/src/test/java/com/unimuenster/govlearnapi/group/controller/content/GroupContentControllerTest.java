package com.unimuenster.govlearnapi.group.controller.content;

import com.unimuenster.govlearnapi.group.controller.GroupTestBase;
import com.unimuenster.govlearnapi.group.controller.wsto.AddContentToMemberWsTo;
import com.unimuenster.govlearnapi.group.entity.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class GroupContentControllerTest extends GroupTestBase {
    @BeforeEach
    void setUp() {
        setCurrentUser(initializerService.getUser1());

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
        assertEquals(2, byId.get().getMembers().size());
        // user1(admin) has no courses
        assertEquals(0, byId.get().getMembers().get(0).getCourses().size());
        // user2(member) has one course
        assertEquals(1, byId.get().getMembers().get(1).getCourses().size());
        assertEquals(courseId, byId.get().getMembers().get(1).getCourses().get(0).getId());
    }
    @Test
    void addContentButNotAdmin() {

        setCurrentUser(initializerService.getUser2());

        AddContentToMemberWsTo addMemberWsTo = AddContentToMemberWsTo
                .builder()
                .memberId(currentMember.getId())
                .courseId(1L)
                .build();

        ResponseEntity responseEntity = groupContentController.addContent(addMemberWsTo);

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());

    }
}