package com.unimuenster.govlearnapi.group.controller.member;

import com.unimuenster.govlearnapi.group.controller.GroupTestBase;
import com.unimuenster.govlearnapi.group.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeleteMemberTest extends GroupTestBase {


    @BeforeEach
    void setup(){
        setCurrentUser(initializerService.getUser2());

        addMember();
        // Add content to check that the content is deleted with the member
        addContent();
    }

    @Test
    void getMemberDetails(){
        ResponseEntity memberById =
                groupMembersController.deleteMember(currentMember.getId());

        Optional<Member> byId = memberRepository.findById(currentMember.getId());

        assertEquals(HttpStatus.OK, memberById.getStatusCode());
        assertTrue(byId.isEmpty());
    }

    @Test
    void getMemberDetailsNotAuthorized(){
        setCurrentUser(initializerService.getUser1());

        ResponseEntity memberById = groupMembersController.deleteMember(currentMember.getId());

        assertEquals(HttpStatus.FORBIDDEN, memberById.getStatusCode());
    }
}
