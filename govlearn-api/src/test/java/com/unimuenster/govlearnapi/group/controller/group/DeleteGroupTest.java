package com.unimuenster.govlearnapi.group.controller.group;

import com.unimuenster.govlearnapi.group.controller.GroupTestBase;
import com.unimuenster.govlearnapi.group.controller.wsto.GroupDetailsUpdateWsTo;
import com.unimuenster.govlearnapi.group.entity.Group;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeleteGroupTest extends GroupTestBase {

    @Test
    void getMemberDetails(){
        setCurrentUser(initializerService.getUser2());

        ResponseEntity response
                = groupController.deleteGroup(getGroup().getId());

        Optional<Group> byId = groupRepository.findById(getGroup().getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(byId.isEmpty());
    }

    @Test
    void getMemberDetailsNotAuthorized(){
        setCurrentUser(initializerService.getUser1());

        ResponseEntity memberById = groupController.deleteGroup(getGroup().getId());

        assertEquals(HttpStatus.FORBIDDEN, memberById.getStatusCode());
    }

}
