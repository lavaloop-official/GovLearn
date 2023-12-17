package com.unimuenster.govlearnapi.group.service;

import com.unimuenster.govlearnapi.course.entity.Course;
import com.unimuenster.govlearnapi.course.repository.CourseRepository;
import com.unimuenster.govlearnapi.group.controller.wsto.DeleteContentForGroupWsTo;
import com.unimuenster.govlearnapi.group.controller.wsto.GroupContentWsTo;
import com.unimuenster.govlearnapi.group.entity.Group;
import com.unimuenster.govlearnapi.group.entity.Member;
import com.unimuenster.govlearnapi.group.repository.GroupRepository;
import com.unimuenster.govlearnapi.group.repository.MemberRepository;
import com.unimuenster.govlearnapi.tags.exception.NotFoundException;
import com.unimuenster.govlearnapi.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.devtools.v85.runtime.Runtime;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;
    private final CourseRepository courseRepository;

    public void createGroup(UserEntity admin) {

        Group group = Group
                .builder()
                .admin(admin)
                .build();

        groupRepository.save(group);
    }

    public boolean isUserAdmin(UserEntity user, Long groupId) {
        return groupRepository.existsByIdAndAdmin(groupId, user.getId());
    }

    public Group findGroupByMemberId(Long memberId) {
        return groupRepository.findByMemberId(memberId);
    }

    public void addMember(Long userId, Long groupId) {
        Group group = groupRepository.findById(groupId).orElseThrow();

        UserEntity user = UserEntity
                .builder()
                .id(userId)
                .build();

        Member member = Member
                .builder()
                .user(user)
                .build();

        memberRepository.save(member);

        checkIfNull(group);
        group.getMembers().add(member);

        groupRepository.save(group);
    }

    private void checkIfNull(Group group) {
        if (group.getMembers() == null) {
            group.setMembers(new ArrayList());
        }
    }

    private void checkIfNull(Member member) {
        if (member.getCourses() == null) {
            member.setCourses(new ArrayList());
        }
    }

    public void addContentToMember(Long memberId, Long courseId) {

        Member member = memberRepository.findById(memberId).orElseThrow();

        Course course = courseRepository.findById(courseId).orElseThrow();

        checkIfNull(member);
        member.getCourses().add(course);

        memberRepository.save(member);
    }

    public void addContentToGroup(Long groupId, Long courseId) {

        Group group = groupRepository.findById(groupId).orElseThrow();

        Course course = courseRepository.findById(courseId).orElseThrow();

        checkIfNull(group);
        addContentToMembers(group.getMembers(), course);
    }

    public void addContentToMembers(List<Member> members, Course course) {
        members.stream().forEach(member -> {
            addContentToMember(member.getId(), course.getId());
        });
    }

    public GroupContentWsTo getContent(UserEntity currentUser, Long groupId) {

        Group group = groupRepository.findById(groupId).orElseThrow();

        boolean isMember = groupRepository.isMember(currentUser.getId(), groupId);

        if ( !isMember ) {
            throw new RuntimeException("User is not in group");
        }

        Member member = groupRepository.getMember(currentUser.getId(), groupId);

        return GroupContentWsTo
                .builder()
                .groupId(group.getId())
                .courseIds(
                        member.getCourses().stream().map(course -> course.getId()).toList()
                )
                .build();
    }

    public int deleteContentForGroup(DeleteContentForGroupWsTo deleteContentForGroupWsTo) {
        Group group = groupRepository.findById(deleteContentForGroupWsTo.getGroupId()).orElseThrow();

        return memberRepository.deleteCourseForAllMembers(group.getId(), deleteContentForGroupWsTo.getCourseId());
    }
}
