package com.unimuenster.govlearnapi.group.controller.invitation;

import com.unimuenster.govlearnapi.core.globalExceptions.UnauthorizedException;
import com.unimuenster.govlearnapi.group.controller.GroupTestBase;
import com.unimuenster.govlearnapi.group.controller.wsto.UpdateInvitationWsTo;
import com.unimuenster.govlearnapi.group.entity.Invitation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class AcceptInvitationTest extends GroupTestBase {

    @BeforeEach
    void setUp() {
        setCurrentUser(initializerService.getUser1());
        //only admin can send invitation
        sendInvitation();
    }

    @Test
    void acceptInvitationTest() {
        setCurrentUser(initializerService.getUser2());

        List<Invitation> all = invitationRepository.findAll();

        UpdateInvitationWsTo updateInvitationWsTo = new UpdateInvitationWsTo();

        updateInvitationWsTo.setInvitationId(all.get(0).getId());
        updateInvitationWsTo.setAccept(true);

        ResponseEntity responseEntity = invitationController.answerInvitation(updateInvitationWsTo);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(0, invitationRepository.findAll().size());
        // user 1 is the admin of the group, user 2 is the member
        assertEquals(2, memberRepository.findAll().size());
        assertEquals(initializerService.getUser1().getId(), memberRepository.findAll().get(0).getUser().getId());
    }

    @Test
    void acceptInvitationTestNotAuthorized() {
        setCurrentUser(initializerService.getRecommendationUser());

        List<Invitation> all = invitationRepository.findAll();

        UpdateInvitationWsTo updateInvitationWsTo = new UpdateInvitationWsTo();

        updateInvitationWsTo.setInvitationId(all.get(0).getId());
        updateInvitationWsTo.setAccept(true);

        // current user is not the user of the invitation
        assertThrows(UnauthorizedException.class, () -> invitationController.answerInvitation(updateInvitationWsTo));

    }
}
