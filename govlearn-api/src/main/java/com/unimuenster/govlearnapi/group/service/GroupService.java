package com.unimuenster.govlearnapi.group.service;

import com.unimuenster.govlearnapi.core.globalExceptions.NotFoundException;
import com.unimuenster.govlearnapi.core.globalExceptions.UnauthorizedException;
import com.unimuenster.govlearnapi.course.entity.Course;
import com.unimuenster.govlearnapi.course.repository.CourseRepository;
import com.unimuenster.govlearnapi.group.controller.wsto.*;
import com.unimuenster.govlearnapi.group.entity.Group;
import com.unimuenster.govlearnapi.group.entity.Member;
import com.unimuenster.govlearnapi.group.repository.GroupRepository;
import com.unimuenster.govlearnapi.group.repository.MemberRepository;
import com.unimuenster.govlearnapi.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupService {

    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;
    private final CourseRepository courseRepository;

    public void createGroup(UserEntity admin, GroupCreationWsTo groupCreationWsTo) {

        Group group = Group
                .builder()
                .admin(admin)
                .description(groupCreationWsTo.getGroupDescription())
                .name(groupCreationWsTo.getGroupName())
                .build();

        groupRepository.save(group);
    }

    public boolean isUserAdmin(UserEntity user, Long groupId) {
        return groupRepository.existsByIdAndAdmin(groupId, user.getId());
    }

    public Group findGroupByMemberId(Long memberId) {
        return groupRepository.findByMemberId(memberId);
    }

    public Long addMember(Long userId, Long groupId) {
        Group group = groupRepository.findById(groupId).orElseThrow();

        UserEntity user = UserEntity
                .builder()
                .id(userId)
                .build();

        Member member = Member
                .builder()
                .user(user)
                .build();

        Member save = memberRepository.save(member);
        log.info("New member Id is {}", save.getId());

        checkIfNull(group);
        group.getMembers().add(member);

        groupRepository.save(group);

        return save.getId();
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

        Group group = groupRepository.findById(groupId).orElseThrow(() -> new NotFoundException("Group not found"));

        boolean isMember = groupRepository.existsByIdAndMember(groupId, currentUser.getId());

        if ( !isMember ) {
            throw new UnauthorizedException("Not part of group.");
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

    public List<GroupDetailsWsTo> getAdminGroups(UserEntity currentUser) {

        List<Group> groups = groupRepository.getGroupsByAdmin(currentUser.getId());

        return groups.stream().map(group -> GroupDetailsWsTo.builder().groupId(group.getId()).groupName(group.getName()).groupDescription(group.getDescription()).build()).toList();
    }

    public List<GroupDetailsWsTo> getMemberGroups(UserEntity currentUser) {
        List<Group> groups = groupRepository.getGroupsByMember(currentUser.getId());

        return groups.stream().map(group -> GroupDetailsWsTo.builder().groupId(group.getId()).groupName(group.getName()).groupDescription(group.getDescription()).build()).toList();
    }

    public GroupDetailsWsTo getGroupDetails(Long groupId) {

        Group group = groupRepository.findById(groupId).orElseThrow();

        return GroupDetailsWsTo
                .builder()
                .groupId(group.getId())
                .groupName(group.getName())
                .groupDescription(group.getDescription())
                .build();
    }

    public boolean isUserMember(UserEntity currentUser, Long groupId) {
        return groupRepository.existsByIdAndMember(groupId, currentUser.getId());
    }

    public List<Long> getMembers(Long groupId) {
        return groupRepository.getMembers(groupId);
    }

    public void updateGroupDetails(GroupDetailsUpdateWsTo updateWsTo) {

        Group group = groupRepository.findById(updateWsTo.getGroupId()).orElseThrow();

        if ( updateWsTo.getGroupName() != null ) {
            group.setName(updateWsTo.getGroupName());
        }

        if ( updateWsTo.getGroupDescription() != null ) {
            group.setDescription(updateWsTo.getGroupDescription());
        }

        groupRepository.save(group);
    }

    public Group getGroupById(Long groupId) {
        Optional<Group> byId = groupRepository.findById(groupId);

        if ( byId.isEmpty() ) {
            throw new NotFoundException("Group not found");
        }

        return byId.get();
    }

    public void deleteGroup(Long groupId) {
        groupRepository.deleteById(groupId);
    }
}
