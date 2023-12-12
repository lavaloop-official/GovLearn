package com.unimuenster.govlearnapi.group.service;

import com.unimuenster.govlearnapi.course.entity.Course;
import com.unimuenster.govlearnapi.course.repository.CourseRepository;
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

}
