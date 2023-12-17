package com.unimuenster.govlearnapi.group.controller;

import com.unimuenster.govlearnapi.AbstractIntegrationTest;
import com.unimuenster.govlearnapi.group.controller.wsto.AddContentToGroupWsTo;
import com.unimuenster.govlearnapi.group.controller.wsto.AddMemberWsTo;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GetContentByMemberTest extends AbstractIntegrationTest {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private InitializerService initializerService;
    @Autowired
    private GroupContentController groupContentController;
    @Autowired
    private GroupMembersController groupMembersController;
    @Autowired
    private GroupRepository groupRepository;
    private Group group;


    @BeforeEach
    void setUp() {
        setCurrentUser(initializerService.getUser2());

        createGroup();
        addMember();
        addContent();
    }

    private void addContent() {
        groupContentController.addContentToAll(
                AddContentToGroupWsTo
                        .builder()
                        .groupId(group.getId())
                        .courseId(initializerService.getCourse1().getId())
                        .build()
        );
    }

    private void addMember() {
        AddMemberWsTo addMemberWsTo = AddMemberWsTo
                .builder()
                .groupId(group.getId())
                .userId(initializerService.getUser1().getId())
                .build();

        groupMembersController.addMember(addMemberWsTo);
    }

    @Transactional
    @Test
    void getContentByMember() {
        setCurrentUser(initializerService.getUser1());

        ResponseEntity responseEntity = groupContentController.getContent(group.getId());

        Optional<Group> byId = groupRepository.findById(group.getId());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, byId.get().getMembers().size());
        assertEquals(1, byId.get().getMembers().get(0).getCourses().size());
        assertEquals(initializerService.getCourse1().getId(), byId.get().getMembers().get(0).getCourses().get(0).getId());
    }

    @Transactional
    @Test
    void getContentButNotPartOfGroup() {

        setCurrentUser(initializerService.getRecommendationUser());

        assertThrows(
                RuntimeException.class,
                () -> groupContentController.getContent(group.getId())
        );
    }

    private void createGroup(){
        group = Group
                .builder()
                .admin(initializerService.getUser2())
                .build();

        groupRepository.save(group);
    }

    private void setCurrentUser(UserEntity userEntity){
        UserDetails userDetails = userDetailsService.loadUserByUsername(
                userEntity.getEmail()
        );
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(auth);
    }

}