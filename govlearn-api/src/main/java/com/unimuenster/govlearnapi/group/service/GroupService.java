package com.unimuenster.govlearnapi.group.service;

import com.unimuenster.govlearnapi.core.config.enums.Role;
import com.unimuenster.govlearnapi.core.globalExceptions.NotFoundException;
import com.unimuenster.govlearnapi.core.globalExceptions.UnauthorizedException;
import com.unimuenster.govlearnapi.course.controller.mapper.ControllerCourseMapper;
import com.unimuenster.govlearnapi.course.controller.wsto.CourseWsTo;
import com.unimuenster.govlearnapi.course.entity.Course;
import com.unimuenster.govlearnapi.course.repository.CourseRepository;
import com.unimuenster.govlearnapi.course.service.mapper.ServiceCourseMapper;
import com.unimuenster.govlearnapi.group.controller.wsto.*;
import com.unimuenster.govlearnapi.group.entity.Group;
import com.unimuenster.govlearnapi.group.entity.Member;
import com.unimuenster.govlearnapi.group.repository.GroupRepository;
import com.unimuenster.govlearnapi.group.repository.MemberRepository;
import com.unimuenster.govlearnapi.user.controller.wsto.UserWsTo;
import com.unimuenster.govlearnapi.user.entity.UserEntity;
import com.unimuenster.govlearnapi.user.service.AuthenticationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupService {

    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;
    private final CourseRepository courseRepository;
    private final AuthenticationService authenticationService;
    private final ServiceCourseMapper serviceCourseMapper;
    private final ControllerCourseMapper controllerCourseMapper;

    public void createGroup(UserEntity user, GroupCreationWsTo groupCreationWsTo) {

        Group group = Group
                .builder()
                .description(groupCreationWsTo.getGroupDescription())
                .name(groupCreationWsTo.getGroupName())
                .build();

        Group newGroup = groupRepository.save(group);

        addMember(user.getId(), newGroup.getId(), Role.Admin);
    }

    public boolean isUserAdmin(UserEntity user, Long groupId) {
        return groupRepository.existsByIdAndAdmin(groupId, user.getId());
    }

    public Group findGroupByMemberId(Long memberId) {
        return groupRepository.findByMemberId(memberId);
    }

    public Long addMember(Long userId, Long groupId, Role role) {
        Group group = groupRepository.findById(groupId).orElseThrow();

        UserEntity user = UserEntity
                .builder()
                .id(userId)
                .build();

        Member member = Member
                .builder()
                .user(user)
                .group(group)
                .role(role)
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
                .courses(
                    controllerCourseMapper.mapList(serviceCourseMapper.mapListCourse(member.getCourses()))
                )
                .userId(currentUser.getId())
                .build();
    }

    public GroupContentWsTo getContentOfUser(Long groupId, Long memberId) {

        groupRepository.findById(groupId).orElseThrow(() -> new NotFoundException("Group not found"));

        boolean isAdmin = groupRepository.existsByIdAndAdmin(groupId, authenticationService.getCurrentUser().getId());

        if ( !isAdmin ) {
            throw new UnauthorizedException("Current user is not an admin of this group!");
        }

        Member member = groupRepository.getMemberByMemberId(memberId);

        if (member == null)
            throw new NotFoundException("User could not be found!");

        return GroupContentWsTo
                .builder()
                .groupId(groupId)
                .courses(
                    controllerCourseMapper.mapList(serviceCourseMapper.mapListCourse(member.getCourses()))
                )
                .userId(member.getUser().getId())
                .build();
    }

    public List<GroupContentWsTo> getContentOfAllUsers(Long groupId) {

        groupRepository.findById(groupId).orElseThrow(() -> new NotFoundException("Group not found"));

        boolean isAdmin = groupRepository.existsByIdAndAdmin(groupId, authenticationService.getCurrentUser().getId());

        if ( !isAdmin ) {
            throw new UnauthorizedException("Current user is not an admin of this group!");
        }

        List<Member> member = groupRepository.getMembers(groupId);

        List<GroupContentWsTo> groupContentWsTos = member.stream().map(element -> GroupContentWsTo
            .builder()
            .groupId(groupId)
            .courses(controllerCourseMapper.mapList(serviceCourseMapper.mapListCourse(element.getCourses())))
            .userId(element.getUser().getId())
            .build()
        ).collect(Collectors.toList());

        return groupContentWsTos;
    }

    public int deleteContentForGroup(DeleteContentForGroupWsTo deleteContentForGroupWsTo) {
        Group group = groupRepository.findById(deleteContentForGroupWsTo.getGroupId()).orElseThrow();

        return memberRepository.deleteCourseForAllMembers(group.getId(), deleteContentForGroupWsTo.getCourseId());
    }

    @Transactional
    public void deleteContentForUser(long courseID, long memberID, long groupID) {

        groupRepository.findById(groupID).orElseThrow(() -> new NotFoundException("Group could not be found!"));
        
        UserEntity currentUser = authenticationService.getCurrentUser();

        boolean userAdmin = isUserAdmin(currentUser, groupID);

        if (!userAdmin) {
            throw new UnauthorizedException("User is not an admin of this group!");
        }

        memberRepository.deleteCourseForMember(courseID, memberID);
    }

    public List<GroupDetailsWsTo> getAdminGroups(UserEntity currentUser) {

        List<Group> groups = groupRepository.getGroupsByAdmin(currentUser.getId());

        return groups.stream().map(group -> GroupDetailsWsTo.builder().groupId(group.getId()).groupName(group.getName()).groupDescription(group.getDescription()).build()).collect(Collectors.toList());
    }

    public List<GroupDetailsWsTo> getMemberGroups(UserEntity currentUser) {
        List<Group> groups = groupRepository.getGroupsByMember(currentUser.getId());
        for (Group group2 : groups) {
            groupRepository.getAdmins(group2.getId());
        }

        return groups.stream().map(group -> {
            Boolean isAdmin = isUserAdmin(currentUser, group.getId());

            Role role = Role.Member;

            if (isAdmin)
                role = Role.Admin;

            GroupDetailsWsTo groupDetailsWsTo = GroupDetailsWsTo
                .builder()
                .groupId(group.getId())
                .groupName(group.getName())
                .groupDescription(group.getDescription())
                .role(role)
                .build();
            return groupDetailsWsTo;
            }
        ).collect(Collectors.toList());
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

    public List<MemberDetailsWsTo> getMembers(Long groupId) {
        List<MemberDetailsWsTo> memberDetailsWsTos = groupRepository.getMembers(groupId).stream().map(member -> {
            MemberDetailsWsTo memberDetailsWsTo = MemberDetailsWsTo
                .builder()
                .memberId(member.getId())
                .name(member.getUser().getName())
                .email(member.getUser().getEmail())
                .role(member.getRole())
                .build();
            return memberDetailsWsTo;
        }).collect(Collectors.toList());
        return memberDetailsWsTos;
    }

    public List<MemberDetailsWsTo> getAdmins(Long groupId) {
        List<MemberDetailsWsTo> admins = groupRepository.getAdmins(groupId).stream().map(user -> {
            MemberDetailsWsTo memberDetailsWsTo = MemberDetailsWsTo
                .builder()
                .memberId(user.getId())
                .name(user.getUser().getName())
                .email(user.getUser().getEmail())
                .memberSince(user.getMemberSince())
                .role(user.getRole())
                .build();
            return memberDetailsWsTo;
        }).collect(Collectors.toList());
        return admins;
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

    @Transactional
    public void deleteGroup(Long groupId) {
        groupRepository.deleteGroup(groupId);
    }
}
