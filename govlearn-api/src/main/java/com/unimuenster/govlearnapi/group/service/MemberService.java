package com.unimuenster.govlearnapi.group.service;

import com.unimuenster.govlearnapi.group.controller.wsto.MemberDetailsWsTo;
import com.unimuenster.govlearnapi.group.entity.Member;
import com.unimuenster.govlearnapi.group.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberDetailsWsTo getMember(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow();

        MemberDetailsWsTo memberDetailsWsTo = new MemberDetailsWsTo();

        memberDetailsWsTo.setMemberId(member.getId());
        memberDetailsWsTo.setEmail(member.getUser().getEmail());
        memberDetailsWsTo.setName(member.getUser().getName());
        memberDetailsWsTo.setMemberSince(member.getMemberSince());

        return memberDetailsWsTo;
    }

}
