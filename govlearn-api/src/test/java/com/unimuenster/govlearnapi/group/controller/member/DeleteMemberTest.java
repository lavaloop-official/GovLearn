package com.unimuenster.govlearnapi.group.controller.member;

import com.unimuenster.govlearnapi.group.controller.GroupTestBase;
import com.unimuenster.govlearnapi.group.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
public class DeleteMemberTest extends GroupTestBase {

    @BeforeEach
    void setup(){
        setCurrentUser(initializerService.getUser1());

        addMember();
        // Add content to check that the content is deleted with the member
        addContent();
    }

    @Test
    void deleteMember(){
        ResponseEntity memberById =
                groupMembersController.deleteMember(currentMember.getId());

        assertEquals(HttpStatus.OK, memberById.getStatusCode());

        entityManager.flush();
        entityManager.clear();

        Optional<Member> byId = memberRepository.findById(currentMember.getId());

        assertTrue(byId.isEmpty());
    }

    @Test
    void deleteMemberNotAuthorized(){
        setCurrentUser(initializerService.getRecommendationUser());

        ResponseEntity memberById = groupMembersController.deleteMember(currentMember.getId());

        assertEquals(HttpStatus.FORBIDDEN, memberById.getStatusCode());
    }
}
