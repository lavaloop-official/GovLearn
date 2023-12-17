package com.unimuenster.govlearnapi.group.service;

import com.unimuenster.govlearnapi.group.entity.Group;
import com.unimuenster.govlearnapi.group.repository.GroupRepository;
import com.unimuenster.govlearnapi.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;

    public void createGroup(UserEntity admin) {

        Group group = Group
                .builder()
                .admin(admin)
                .build();

        groupRepository.save(group);
    }
}
