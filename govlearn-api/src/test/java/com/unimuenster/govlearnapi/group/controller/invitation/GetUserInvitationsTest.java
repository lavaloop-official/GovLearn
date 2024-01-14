package com.unimuenster.govlearnapi.group.controller.invitation;

import com.unimuenster.govlearnapi.common.responsewrapper.Response;
import com.unimuenster.govlearnapi.group.controller.GroupTestBase;
import com.unimuenster.govlearnapi.group.controller.wsto.RetrieveInvitationWsTo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetUserInvitationsTest extends GroupTestBase {

    @BeforeEach
    void setUp() {
        setCurrentUser(initializerService.getUser1());

        sendInvitation();
    }


    @Test
    void getUserInvitationsTest() {
        setCurrentUser(initializerService.getUser2());

        ResponseEntity allInvitation = invitationController.getAllInvitation();

        List<RetrieveInvitationWsTo> retrieveInvitationWsToList = ((Response<List<RetrieveInvitationWsTo>>) allInvitation.getBody()).getPayload();

        assertEquals(1, retrieveInvitationWsToList.size());
        assertEquals(retrieveInvitationWsToList.get(0).getGroupId(), retrieveInvitationWsToList.get(0).getGroupId());
        assertEquals(retrieveInvitationWsToList.get(0).getInvitationId(), retrieveInvitationWsToList.get(0).getInvitationId());
    }
}
