package com.unimuenster.govlearnapi.group.controller;

import com.unimuenster.govlearnapi.AbstractIntegrationTest;
import com.unimuenster.govlearnapi.common.responsewrapper.Response;
import com.unimuenster.govlearnapi.group.controller.wsto.AddContentToGroupWsTo;
import com.unimuenster.govlearnapi.group.controller.wsto.AddMemberWsTo;
import com.unimuenster.govlearnapi.group.controller.wsto.DeleteContentForGroupWsTo;
import com.unimuenster.govlearnapi.group.entity.Group;
import com.unimuenster.govlearnapi.group.repository.GroupRepository;
import com.unimuenster.govlearnapi.group.repository.MemberRepository;
import com.unimuenster.govlearnapi.initializer.InitializerService;
import com.unimuenster.govlearnapi.user.entity.UserEntity;
import jakarta.persistence.EntityManager;
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

public class DeleteContentForAllMembers extends AbstractIntegrationTest {
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
    void deleteContentFromAllMembers() {

        DeleteContentForGroupWsTo deleteContentForGroupWsTo = DeleteContentForGroupWsTo
                .builder()
                .groupId(group.getId())
                .courseId(initializerService.getCourse1().getId())
                .build();

        final int deleteCount = 1;


        ResponseEntity responseEntity = groupContentController.deleteContent(deleteContentForGroupWsTo);


        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        // Cannot test a delete query in this context, but we can test the delete count from the query
        assertEquals(deleteCount, ((Response<Integer>) responseEntity.getBody()).getPayload());
    }

    @Transactional
    @Test
    void deleteContentFromAllMembersNotAdmin() {

        setCurrentUser(initializerService.getUser1());

        DeleteContentForGroupWsTo deleteContentForGroupWsTo = DeleteContentForGroupWsTo
                .builder()
                .groupId(group.getId())
                .courseId(1L)
                .build();

        ResponseEntity responseEntity = groupContentController.deleteContent(deleteContentForGroupWsTo);

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());

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