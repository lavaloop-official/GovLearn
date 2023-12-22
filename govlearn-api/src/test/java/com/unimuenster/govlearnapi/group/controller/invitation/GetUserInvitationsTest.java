package com.unimuenster.govlearnapi.group.controller.invitation;

import com.unimuenster.govlearnapi.common.responsewrapper.Response;
import com.unimuenster.govlearnapi.group.controller.GroupTestBase;
import com.unimuenster.govlearnapi.group.controller.wsto.AllInvitationsWsTo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetUserInvitationsTest extends GroupTestBase {

    @BeforeEach
    void setUp() {
        setCurrentUser(initializerService.getUser2());

        sendInvitation();
    }


    @Test
    void getUserInvitationsTest() {
        setCurrentUser(initializerService.getUser1());

        ResponseEntity allInvitation = invitationController.getAllInvitation();

        AllInvitationsWsTo invitations = ((Response<AllInvitationsWsTo>) allInvitation.getBody()).getPayload();

        assertEquals(1, invitations.getGroupIds().size());
        assertEquals(initializerService.getGroup().getId(), invitations.getGroupIds().get(0));
    }
}
