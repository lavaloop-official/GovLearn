package com.unimuenster.govlearnapi.group.controller.group;

import com.unimuenster.govlearnapi.common.responsewrapper.Response;
import com.unimuenster.govlearnapi.group.controller.GroupTestBase;
import com.unimuenster.govlearnapi.group.controller.wsto.GroupCreationWsTo;
import com.unimuenster.govlearnapi.group.controller.wsto.GroupDetailsWsTo;
import com.unimuenster.govlearnapi.group.entity.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
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

        List<Group> group = groupRepository.getGroupsByAdmin(initializerService.getUser2().getId());

        assertEquals(1, group.size());
    }

    @Test
    void getGroupDetails() {
        setCurrentUser(initializerService.getUser1());
        Group group = initializerService.getGroup();

        ResponseEntity groupDetails = groupController.getGroupDetails(group.getId());

        Response<GroupDetailsWsTo> response = (Response<GroupDetailsWsTo>) groupDetails.getBody();

        assertEquals(group.getDescription(), response.getPayload().getGroupDescription());
        assertEquals(group.getName(), response.getPayload().getGroupName());
    }
}