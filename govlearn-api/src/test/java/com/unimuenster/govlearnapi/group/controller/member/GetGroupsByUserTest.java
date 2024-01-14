package com.unimuenster.govlearnapi.group.controller.member;

import com.unimuenster.govlearnapi.common.responsewrapper.Response;
import com.unimuenster.govlearnapi.group.controller.GroupTestBase;
import com.unimuenster.govlearnapi.group.controller.wsto.AddMemberWsTo;
import com.unimuenster.govlearnapi.group.controller.wsto.GroupDetailsWsTo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
public class GetGroupsByUserTest extends GroupTestBase {
    @BeforeEach
    void setUp() {
        setCurrentUser(initializerService.getUser1());

        addMember();
    }

    @Test
    void getContentByMember() {

        ResponseEntity responseEntity = groupController.getGroups();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        Response<List<GroupDetailsWsTo>> response = (Response<List<GroupDetailsWsTo>>) responseEntity.getBody();

        // Current user is member of group with id 1
        assertEquals(getGroup().getId(), response.getPayload().get(0).getGroupId());
    }
}