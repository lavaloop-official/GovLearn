package com.unimuenster.govlearnapi.group.controller;

import com.unimuenster.govlearnapi.AbstractIntegrationTest;
import com.unimuenster.govlearnapi.common.responsewrapper.Response;
import com.unimuenster.govlearnapi.group.controller.wsto.AddContentToGroupWsTo;
import com.unimuenster.govlearnapi.group.controller.wsto.AddMemberWsTo;
import com.unimuenster.govlearnapi.group.controller.wsto.InvitationWsTo;
import com.unimuenster.govlearnapi.group.entity.Group;
import com.unimuenster.govlearnapi.group.entity.Member;
import com.unimuenster.govlearnapi.group.repository.GroupRepository;
import com.unimuenster.govlearnapi.group.repository.InvitationRepository;
import com.unimuenster.govlearnapi.group.repository.MemberRepository;
import com.unimuenster.govlearnapi.initializer.InitializerService;
import com.unimuenster.govlearnapi.user.entity.UserEntity;

import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class GroupTestBase extends AbstractIntegrationTest {
    @Autowired
    protected UserDetailsService userDetailsService;
    @Autowired
    protected InitializerService initializerService;
    @Autowired
    protected GroupContentController groupContentController;
    @Autowired
    protected GroupMembersController groupMembersController;
    @Autowired
    protected GroupController groupController;
    @Autowired
    protected MemberRepository memberRepository;
    @Autowired
    protected GroupRepository groupRepository;
    @Autowired
    protected InvitationController invitationController;
    @Autowired
    protected InvitationRepository invitationRepository;
    @Autowired
    protected EntityManager entityManager;
    protected Member currentMember;

    protected void addMember() {
        AddMemberWsTo addMemberWsTo = AddMemberWsTo
                .builder()
                .groupId(getGroup().getId())
                .userId(initializerService.getUser2().getId())
                .build();

        ResponseEntity responseEntity = groupMembersController.addMember(addMemberWsTo);

        Response<Long> body = (Response<Long>) responseEntity.getBody();

        currentMember = new Member();
        currentMember.setId(body.getPayload());
    }

    protected void setCurrentUser(UserEntity userEntity){
        UserDetails userDetails = userDetailsService.loadUserByUsername(
                userEntity.getEmail()
        );
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    protected void addContent() {
        groupContentController.addContentToAll(
                AddContentToGroupWsTo
                        .builder()
                        .groupId(getGroup().getId())
                        .courseId(initializerService.getCourse1().getId())
                        .build()
        );
    }

    protected void sendInvitation() {
        InvitationWsTo invitationWsTo = new InvitationWsTo();
        invitationWsTo.setGroupId(initializerService.getGroup().getId());
        invitationWsTo.setUserEmail(initializerService.getUser2().getEmail());
        List<InvitationWsTo> invitationWsTos = new ArrayList<InvitationWsTo>();
        invitationWsTos.add(invitationWsTo);

        invitationController.sendInvitation(invitationWsTos);
    }

    protected Group getGroup() {
        return initializerService.getGroup();
    }
}
