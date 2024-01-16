package com.unimuenster.govlearnapi.group.controller.invitation;

import com.unimuenster.govlearnapi.group.controller.GroupTestBase;
import com.unimuenster.govlearnapi.group.controller.wsto.InvitationWsTo;
import com.unimuenster.govlearnapi.group.entity.Invitation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

class SendInvitationTest extends GroupTestBase {

    @BeforeEach
    void setUp() {

        setCurrentUser(initializerService.getUser1());

    }

    @Test
    void sendInvitationTest() {

        InvitationWsTo invitationWsTo = new InvitationWsTo();
        invitationWsTo.setGroupId(initializerService.getGroup().getId());
        invitationWsTo.setUserEmail(initializerService.getUser2().getEmail());
        List<InvitationWsTo> invitationWsTos = new ArrayList<InvitationWsTo>();
        invitationWsTos.add(invitationWsTo);

        ResponseEntity responseEntity = invitationController.sendInvitation(invitationWsTos);

        List<Invitation> all = invitationRepository.findAll();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, all.size());
        assertEquals(initializerService.getUser2().getId(), all.get(0).getUser().getId());
        assertEquals(initializerService.getGroup().getId(), all.get(0).getGroup().getId());
    }

    @Test
    void sendInvitationNotAuthorized() {

        setCurrentUser(initializerService.getUser2());

        InvitationWsTo invitationWsTo = new InvitationWsTo();
        invitationWsTo.setGroupId(initializerService.getGroup().getId());
        invitationWsTo.setUserEmail(initializerService.getUser1().getEmail());
        List<InvitationWsTo> invitationWsTos = new ArrayList<InvitationWsTo>();
        invitationWsTos.add(invitationWsTo);

        ResponseEntity responseEntity = invitationController.sendInvitation(invitationWsTos);

        List<Invitation> all = invitationRepository.findAll();

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }
}