package com.unimuenster.govlearnapi.group.controller;

import com.unimuenster.govlearnapi.common.responsewrapper.Response;
import com.unimuenster.govlearnapi.group.controller.wsto.MemberDetailsWsTo;
import com.unimuenster.govlearnapi.group.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
public class GetMemberDetailsTest extends GroupTestBase{

    @BeforeEach
    void setup(){
        setCurrentUser(initializerService.getUser2());

        addMember();
    }

    @Test
    void getMemberDetails(){
        ResponseEntity memberById =
                groupMembersController.getMemberById(currentMember.getId());

        MemberDetailsWsTo memberDetails = ((Response<MemberDetailsWsTo>) memberById.getBody()).getPayload();

        Member member = memberRepository.findById(currentMember.getId()).orElseThrow();

        assertEquals(member.getId(), memberDetails.getMemberId());
        assertEquals(member.getMemberSince(), memberDetails.getMemberSince());
        assertEquals(member.getUser().getName(), memberDetails.getName());
        assertEquals(member.getUser().getEmail(), memberDetails.getEmail());
    }

    @Test
    void getMemberDetailsNotAuthorized(){
        setCurrentUser(initializerService.getUser1());

        ResponseEntity memberById = groupMembersController.getMemberById(currentMember.getId());

        assertEquals(HttpStatus.FORBIDDEN, memberById.getStatusCode());
    }
}
