package com.unimuenster.govlearnapi.group.controller.mapper;

import com.unimuenster.govlearnapi.group.controller.wsto.GroupCreationWsTo;
import com.unimuenster.govlearnapi.group.service.dto.GroupDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupMapper {

    public GroupDTO mapToDto(GroupCreationWsTo groupCreationWsTo, Integer creatorId){

        GroupDTO groupDTO = new GroupDTO();

        groupDTO.setCreatorId(
                creatorId
        );

        groupDTO.setName(
                groupCreationWsTo.getName()
        );

        groupDTO.setMembers(
                groupCreationWsTo.getUserIds()
        );

        groupDTO.setCourses(
                groupCreationWsTo.getCourseIds()
        );

        return groupDTO;
    }

}
