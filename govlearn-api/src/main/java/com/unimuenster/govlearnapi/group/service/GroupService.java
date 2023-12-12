package com.unimuenster.govlearnapi.group.service;

import com.unimuenster.govlearnapi.course.entity.Course;
import com.unimuenster.govlearnapi.group.entity.Group;
import com.unimuenster.govlearnapi.group.repository.GroupRepository;
import com.unimuenster.govlearnapi.group.service.dto.GroupDTO;
import com.unimuenster.govlearnapi.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;


    public void createGroup(GroupDTO groupDTO){

        Group group = new Group();

        UserEntity creator = new UserEntity();
        creator.setId(groupDTO.getCreatorId());
        group.setCreator(creator);

        group.setName(groupDTO.getName());

        List<UserEntity> members = groupDTO.getMembers().stream().map(memberId -> {
            UserEntity member = new UserEntity();
            member.setId(memberId);
            return member;
        }).toList();
        group.setMembers(members);

        List<Course> courses = groupDTO.getCourses().stream().map(courseId -> {
            Course course = new Course();
            course.setId(courseId);
            return course;
        }).toList();
        group.setCourses(courses);

        groupRepository.save(group);
    }

}
