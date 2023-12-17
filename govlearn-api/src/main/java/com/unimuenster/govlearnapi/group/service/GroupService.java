package com.unimuenster.govlearnapi.group.service;

import com.unimuenster.govlearnapi.course.entity.Course;
import com.unimuenster.govlearnapi.course.repository.CourseRepository;
import com.unimuenster.govlearnapi.group.entity.Group;
import com.unimuenster.govlearnapi.group.entity.Member;
import com.unimuenster.govlearnapi.group.repository.GroupRepository;
import com.unimuenster.govlearnapi.group.repository.MemberRepository;
import com.unimuenster.govlearnapi.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
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
}
