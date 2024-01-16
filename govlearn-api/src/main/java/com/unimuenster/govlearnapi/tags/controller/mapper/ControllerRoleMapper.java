package com.unimuenster.govlearnapi.tags.controller.mapper;

import com.unimuenster.govlearnapi.tags.controller.wsto.RoleTagWsTo;
import com.unimuenster.govlearnapi.tags.controller.wsto.RoleWsTo;
import com.unimuenster.govlearnapi.tags.service.dto.RoleDTO;
import com.unimuenster.govlearnapi.tags.service.dto.RoleTagDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ControllerRoleMapper {

    public RoleWsTo map(RoleDTO roleDTO){
        return new RoleWsTo(roleDTO.id(),roleDTO.name(),roleDTO.description(), roleDTO.verantwortungsbereich(), mapRoleTagDTOs(roleDTO.roleTagDTOs()));
    }

    public List<RoleWsTo> map(List<RoleDTO> roleDTOs) {
        return roleDTOs
                .stream()
                .map(roleDTO -> map(roleDTO))
                .collect(Collectors.toList());
    }

    public RoleTagWsTo map(RoleTagDTO roleTagDTO){
        return new RoleTagWsTo(roleTagDTO.ID(),roleTagDTO.tagID(),roleTagDTO.rating(), roleTagDTO.tagName());
    }

    public List<RoleTagWsTo> mapRoleTagDTOs(List<RoleTagDTO> roleTagDTOs) {
        return roleTagDTOs
                .stream()
                .map(roleTagDTO -> map(roleTagDTO))
                .collect(Collectors.toList());
    }
}
