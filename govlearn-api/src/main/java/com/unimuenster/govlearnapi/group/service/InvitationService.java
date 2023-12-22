package com.unimuenster.govlearnapi.group.service;

import com.unimuenster.govlearnapi.core.globalExceptions.NotFoundException;
import com.unimuenster.govlearnapi.core.globalExceptions.UnauthorizedException;
import com.unimuenster.govlearnapi.group.controller.wsto.RetrieveInvitationWsTo;
import com.unimuenster.govlearnapi.group.entity.Group;
import com.unimuenster.govlearnapi.group.entity.Invitation;
import com.unimuenster.govlearnapi.group.repository.InvitationRepository;
import com.unimuenster.govlearnapi.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class InvitationService {

    private final InvitationRepository invitationRepository;
    private final GroupService groupService;

    public void sendInvitation(Group group, UserEntity user){

        Invitation invitation = Invitation
                .builder()
                .user(user)
                .group(group)
                .build();

        invitationRepository.save(invitation);
    }

    public void acceptInvitation(Invitation invitation){

        groupService.addMember(invitation.getUser().getId(), invitation.getGroup().getId());

        invitationRepository.delete(invitation);
    }

    public List<RetrieveInvitationWsTo> getInvitations(UserEntity currentUser) {

        List<Invitation> invitationList = invitationRepository.getAllInvitationsByUser(currentUser.getId());

        List<RetrieveInvitationWsTo> retrieveInvitationWsTos
                = invitationList
                .stream()
                .map(invitation -> {
                    RetrieveInvitationWsTo retrieveInvitationWsTo = new RetrieveInvitationWsTo();

                    retrieveInvitationWsTo.setInvitationId(invitation.getId());
                    retrieveInvitationWsTo.setGroupId(invitation.getGroup().getId());

                    return retrieveInvitationWsTo;
                })
                .collect(Collectors.toList());

        return retrieveInvitationWsTos;
    }

    public void declineInvitation(Invitation invitation) {
        invitationRepository.delete(invitation);
    }

    public Invitation hasInvitation(UserEntity currentUser, Long invitationId) {

        Optional<Invitation> byId = invitationRepository.findById(invitationId);

        if ( byId.isEmpty() ) {
            throw new NotFoundException("Invitation not found.");
        }

        if ( byId.get().getUser().getId() != currentUser.getId() ){
            throw new UnauthorizedException("You are not authorized to answer this invitation.");
        }

        return byId.get();
    }
}
