package com.unimuenster.govlearnapi.group.controller.group;

import com.unimuenster.govlearnapi.group.controller.GroupTestBase;
import com.unimuenster.govlearnapi.group.controller.wsto.GroupDetailsUpdateWsTo;
import com.unimuenster.govlearnapi.group.entity.Group;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UpdateGroupDetailsTest extends GroupTestBase {

    @Test
    void getMemberDetails(){
        setCurrentUser(initializerService.getUser2());

        GroupDetailsUpdateWsTo groupDetailsUpdateWsTo = new GroupDetailsUpdateWsTo();
        groupDetailsUpdateWsTo.setGroupId(getGroup().getId());
        groupDetailsUpdateWsTo.setGroupDescription("asfdadsf");
        groupDetailsUpdateWsTo.setGroupName("kmninuhtr");

        ResponseEntity response
                = groupController.updateGroupDetails(groupDetailsUpdateWsTo);

        Optional<Group> byId = groupRepository.findById(getGroup().getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(groupDetailsUpdateWsTo.getGroupName(), byId.get().getName());
        assertEquals(groupDetailsUpdateWsTo.getGroupDescription(), byId.get().getDescription());
    }

    @Test
    void getMemberDetailsNotAuthorized(){
        setCurrentUser(initializerService.getUser1());

        GroupDetailsUpdateWsTo groupDetailsUpdateWsTo = new GroupDetailsUpdateWsTo();
        groupDetailsUpdateWsTo.setGroupId(getGroup().getId());

        ResponseEntity memberById = groupController.updateGroupDetails(groupDetailsUpdateWsTo);

        assertEquals(HttpStatus.FORBIDDEN, memberById.getStatusCode());
    }

}
