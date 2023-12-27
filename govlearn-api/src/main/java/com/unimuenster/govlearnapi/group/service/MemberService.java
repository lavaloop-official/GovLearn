package com.unimuenster.govlearnapi.group.service;

import com.unimuenster.govlearnapi.group.controller.wsto.MemberDetailsWsTo;
import com.unimuenster.govlearnapi.group.entity.Member;
import com.unimuenster.govlearnapi.group.repository.MemberRepository;

import jakarta.transaction.Transactional;
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

        MemberDetailsWsTo memberDetailsWsTo = MemberDetailsWsTo
            .builder()
            .memberId(member.getId())
            .name(member.getUser().getName())
            .email(member.getUser().getEmail())
            .memberSince(member.getMemberSince())
            .role(member.getRole())
            .build();

        return memberDetailsWsTo;
    }

    @Transactional
    public void removeMember(Long memberId) {
        memberRepository.removeMemberFromGroup(memberId);
    }
}
