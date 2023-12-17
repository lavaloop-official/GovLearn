package com.unimuenster.govlearnapi.group.controller;

import com.unimuenster.govlearnapi.AbstractIntegrationTest;
import com.unimuenster.govlearnapi.common.responsewrapper.Response;
import com.unimuenster.govlearnapi.group.controller.wsto.GroupCreationWsTo;
import com.unimuenster.govlearnapi.group.controller.wsto.GroupDetailsWsTo;
import com.unimuenster.govlearnapi.group.entity.Group;
import com.unimuenster.govlearnapi.group.repository.GroupRepository;
import com.unimuenster.govlearnapi.initializer.InitializerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
class GroupControllerTest extends AbstractIntegrationTest {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private InitializerService initializerService;
    @Autowired
    private GroupController groupController;
    @Autowired
    private GroupRepository groupRepository;

    @BeforeEach
    void setUp() {
        setCurrentUser();
    }

    @Test
    void createGroup() {

        GroupCreationWsTo groupCreationWsTo = new GroupCreationWsTo();
        groupCreationWsTo.setGroupName("TestGroup");
        groupCreationWsTo.setGroupDescription("TestDescription");

        groupController.createGroup(groupCreationWsTo);

        List<Group> group = groupRepository.findByAdmin(initializerService.getUser2().getId());

        assertEquals(1, group.size());
        assertEquals(initializerService.getUser2().getId(), group.get(0).getAdmin().getId());
    }

    @Test
    void getGroupDetails() {

        Group group = new Group();
        group.setName("TestGroup");
        group.setDescription("TestDescription");
        group.setAdmin(initializerService.getUser2());

        Group save = groupRepository.save(group);

        ResponseEntity groupDetails = groupController.getGroupDetails(save.getId());

        Response<GroupDetailsWsTo> response = (Response<GroupDetailsWsTo>) groupDetails.getBody();

        assertEquals(group.getDescription(), response.getPayload().getGroupDescription());
        assertEquals(group.getName(), response.getPayload().getGroupName());
    }

    private void setCurrentUser(){
        UserDetails userDetails = userDetailsService.loadUserByUsername(
                initializerService.getUser2().getEmail()
        );
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}