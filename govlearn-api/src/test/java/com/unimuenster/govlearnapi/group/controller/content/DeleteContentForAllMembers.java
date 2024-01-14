package com.unimuenster.govlearnapi.group.controller.content;

import com.unimuenster.govlearnapi.common.responsewrapper.Response;
import com.unimuenster.govlearnapi.group.controller.GroupTestBase;
import com.unimuenster.govlearnapi.group.controller.wsto.DeleteContentForGroupWsTo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeleteContentForAllMembers extends GroupTestBase {

    @BeforeEach
    void setUp() {
        setCurrentUser(initializerService.getUser1());

        addMember();
        addContent();
    }

    @Test
    void deleteContentFromAllMembers() {

        DeleteContentForGroupWsTo deleteContentForGroupWsTo = DeleteContentForGroupWsTo
                .builder()
                .groupId(getGroup().getId())
                .courseId(initializerService.getCourse1().getId())
                .build();

        // the course is deleted for both user1 (admin) and user2 (member)
        final int deleteCount = 2;


        ResponseEntity responseEntity = groupContentController.deleteContent(deleteContentForGroupWsTo);


        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        // Cannot test a delete query in this context, but we can test the delete count from the query
        assertEquals(deleteCount, ((Response<Integer>) responseEntity.getBody()).getPayload());
    }

    @Test
    void deleteContentFromAllMembersNotAdmin() {

        setCurrentUser(initializerService.getUser2());

        DeleteContentForGroupWsTo deleteContentForGroupWsTo = DeleteContentForGroupWsTo
                .builder()
                .groupId(getGroup().getId())
                .courseId(1L)
                .build();

        ResponseEntity responseEntity = groupContentController.deleteContent(deleteContentForGroupWsTo);

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());

    }
}