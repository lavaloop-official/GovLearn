package com.unimuenster.govlearnapi.group.controller;

import com.unimuenster.govlearnapi.common.responsewrapper.Response;
import com.unimuenster.govlearnapi.group.controller.wsto.GroupCreationWsTo;
import com.unimuenster.govlearnapi.group.controller.wsto.GroupDetailsWsTo;
import com.unimuenster.govlearnapi.group.entity.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GroupControllerTest extends GroupTestBase {

    @BeforeEach
    void setUp() {
        setCurrentUser(initializerService.getUser2());
    }

    @Test
    void createGroupTest() {

        GroupCreationWsTo groupCreationWsTo = new GroupCreationWsTo();
        groupCreationWsTo.setGroupName("TestGroup");
        groupCreationWsTo.setGroupDescription("TestDescription");

        groupController.createGroup(groupCreationWsTo);

        List<Group> group = groupRepository.findByAdmin(initializerService.getUser2().getId());

        assertEquals(1, group.size());
        assertEquals(initializerService.getUser2().getId(), group.get(0).getAdmin().getId());
    }

    @Test
    void getGroupDetails() {

        Group group = new Group();
        group.setName("TestGroup");
        group.setDescription("TestDescription");
        group.setAdmin(initializerService.getUser2());

        Group save = groupRepository.save(group);

        ResponseEntity groupDetails = groupController.getGroupDetails(save.getId());

        Response<GroupDetailsWsTo> response = (Response<GroupDetailsWsTo>) groupDetails.getBody();

        assertEquals(group.getDescription(), response.getPayload().getGroupDescription());
        assertEquals(group.getName(), response.getPayload().getGroupName());
    }
}