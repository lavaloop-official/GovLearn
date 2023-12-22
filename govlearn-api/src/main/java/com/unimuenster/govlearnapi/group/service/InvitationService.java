package com.unimuenster.govlearnapi.group.service;

import com.unimuenster.govlearnapi.group.controller.wsto.AllInvitationsWsTo;
import com.unimuenster.govlearnapi.group.entity.Group;
import com.unimuenster.govlearnapi.group.entity.Invitation;
import com.unimuenster.govlearnapi.group.repository.InvitationRepository;
import com.unimuenster.govlearnapi.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class InvitationService {

    private final InvitationRepository invitationRepository;

    public void sendInvitation(Group group, UserEntity user){

        Invitation invitation = Invitation
                .builder()
                .user(user)
                .group(group)
                .build();

        invitationRepository.save(invitation);
    }

    public void acceptInvitation(Invitation invitation){


    }

    public AllInvitationsWsTo getInvitations(UserEntity currentUser) {

        List<Invitation> invitationList = invitationRepository.getAllInvitationsByUser(currentUser.getId());

        List<Long> groupIds = invitationList.stream().map(invitation -> invitation.getGroup().getId()).collect(Collectors.toList());

        AllInvitationsWsTo allInvitationsWsTo = new AllInvitationsWsTo();
        allInvitationsWsTo.setGroupIds(groupIds);

        return allInvitationsWsTo;
    }
}
