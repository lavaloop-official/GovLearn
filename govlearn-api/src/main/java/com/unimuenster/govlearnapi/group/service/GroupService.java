package com.unimuenster.govlearnapi.group.service;

import com.unimuenster.govlearnapi.course.entity.Course;
import com.unimuenster.govlearnapi.course.repository.CourseRepository;
import com.unimuenster.govlearnapi.group.controller.wsto.GroupAdminWsTo;
import com.unimuenster.govlearnapi.group.controller.wsto.GroupContentWsTo;
import com.unimuenster.govlearnapi.group.entity.Group;
import com.unimuenster.govlearnapi.group.repository.GroupRepository;
import com.unimuenster.govlearnapi.group.service.dto.GroupDTO;
import com.unimuenster.govlearnapi.user.entity.UserEntity;
import com.unimuenster.govlearnapi.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;


    public void createGroup(GroupDTO groupDTO) {
        Group group = new Group();

        // Fetch creator from the repository
        UserEntity creator = userRepository.findById(groupDTO.getCreatorId()).orElseThrow(EntityNotFoundException::new);
        group.setCreator(creator);

        group.setName(groupDTO.getName());

        // Fetch members from the repository
        List<UserEntity> members = userRepository.findAllById(groupDTO.getMembers());
        group.setMembers(members);

        // Fetch courses from the repository
        List<Course> courses = courseRepository.findAllById(groupDTO.getCourses());
        group.setCourses(courses);

        groupRepository.save(group);
    }

    public List<GroupContentWsTo> getGroupsByMember(UserEntity currentUser) {
        // Find all groups which the current user is a member of
        List<Group> groups = groupRepository.findAllByMember(currentUser.getId());

        // Map the groups to the GroupContentWsTo
        List<GroupContentWsTo> groupContents = groups.stream()
                .map(group -> {
                    GroupContentWsTo groupContent = new GroupContentWsTo();
                    groupContent.setName(group.getName());
                    groupContent.setCourseId(group.getCourses().stream().map(Course::getId).toList());
                    return groupContent;
                })
                .toList();

        return groupContents;
    }

    public List<GroupAdminWsTo> getGroupsByAdmin(UserEntity currentUser) {
        // Find all groups which the current user is a member of
        List<Group> groups = groupRepository.findAllByAdmin(currentUser.getId());

        // Map the groups to the GroupContentWsTo
        List<GroupAdminWsTo> groupAdmins = groups.stream()
                .map(group -> {
                    GroupAdminWsTo groupAdmin = new GroupAdminWsTo();
                    groupAdmin.setName(group.getName());
                    groupAdmin.setCourseIds(group.getCourses().stream().map(Course::getId).toList());
                    groupAdmin.setUserIds(group.getMembers().stream().map(UserEntity::getId).toList());
                    return groupAdmin;
                })
                .toList();

        return groupAdmins;
    }
}
