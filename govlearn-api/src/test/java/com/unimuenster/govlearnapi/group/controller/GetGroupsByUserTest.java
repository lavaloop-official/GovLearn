package com.unimuenster.govlearnapi.group.controller;

import com.unimuenster.govlearnapi.AbstractIntegrationTest;
import com.unimuenster.govlearnapi.common.responsewrapper.Response;
import com.unimuenster.govlearnapi.group.controller.wsto.AddMemberWsTo;
import com.unimuenster.govlearnapi.group.controller.wsto.GetGroupsWsTo;
import com.unimuenster.govlearnapi.group.entity.Group;
import com.unimuenster.govlearnapi.group.repository.GroupRepository;
import com.unimuenster.govlearnapi.initializer.InitializerService;
import com.unimuenster.govlearnapi.user.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetGroupsByUserTest extends AbstractIntegrationTest {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private InitializerService initializerService;
    @Autowired
    private GroupMembersController groupMembersController;
    @Autowired
    private GroupController groupController;
    @Autowired
    private GroupRepository groupRepository;
    private Group group;


    @BeforeEach
    void setUp() {
        setCurrentUser(initializerService.getUser2());

        createGroup();
        addMember();
    }


    private void addMember() {
        AddMemberWsTo addMemberWsTo = AddMemberWsTo
                .builder()
                .groupId(group.getId())
                .userId(initializerService.getUser1().getId())
                .build();

        groupMembersController.addMember(addMemberWsTo);

        AddMemberWsTo addMemberWsTo2 = AddMemberWsTo
                .builder()
                .groupId(group.getId())
                .userId(initializerService.getUser2().getId())
                .build();

        groupMembersController.addMember(addMemberWsTo2);
    }

    @Transactional
    @Test
    void getContentByMember() {

        ResponseEntity responseEntity = groupController.getGroups();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        Response<GetGroupsWsTo> response = (Response<GetGroupsWsTo>) responseEntity.getBody();

        // Current user is member and admin of group with id 1
        assertEquals(group.getId(), response.getPayload().getAdminGroups().get(0));
        assertEquals(group.getId(), response.getPayload().getMemberGroups().get(0));
    }


    private void createGroup() {
        group = Group
                .builder()
                .admin(initializerService.getUser2())
                .build();

        groupRepository.save(group);
    }

    private void setCurrentUser(UserEntity userEntity) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(
                userEntity.getEmail()
        );
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}