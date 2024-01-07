package com.unimuenster.govlearnapi.group.service;

import com.unimuenster.govlearnapi.core.config.enums.Role;
import com.unimuenster.govlearnapi.group.controller.wsto.MemberDetailsWsTo;
import com.unimuenster.govlearnapi.group.entity.Invitation;
import com.unimuenster.govlearnapi.group.entity.Member;
import com.unimuenster.govlearnapi.group.repository.MemberRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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

    public MemberDetailsWsTo getMemberByUserIDAndGroupID(Long userID, Long groupID) {
        Member member = memberRepository.findByUserIDAndGroupID(userID, groupID).orElseThrow();

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

    public List<MemberDetailsWsTo> getInvitedMembers(Long groupID){
        List<Invitation> invitedMembers = memberRepository.getInvitedMembers(groupID);

        List<MemberDetailsWsTo> invitedMemberDetailsWsTos = invitedMembers.stream().map(member -> {

            MemberDetailsWsTo memberDetailsWsTo = MemberDetailsWsTo
                .builder()
                .memberId(null)
                .name(member.getUser().getName())
                .email(member.getUser().getEmail())
                .memberSince(null)
                .role(Role.Invited)
                .build();
            
            return memberDetailsWsTo;
        }).collect(Collectors.toList());

        return invitedMemberDetailsWsTos;
    }
}
