package com.unimuenster.govlearnapi.tags.service.mapper;

import com.unimuenster.govlearnapi.tags.controller.wsto.RoleCreationWsTo;
import com.unimuenster.govlearnapi.tags.entity.Role;
import com.unimuenster.govlearnapi.tags.entity.RoleTag;
import com.unimuenster.govlearnapi.tags.entity.Tag;
import com.unimuenster.govlearnapi.tags.service.dto.RoleCreationDTO;
import com.unimuenster.govlearnapi.tags.service.dto.RoleDTO;
import com.unimuenster.govlearnapi.tags.service.dto.RoleTagDTO;
import com.unimuenster.govlearnapi.tags.service.dto.TagDTO;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class ServiceRoleMapper {

    public RoleDTO map(Role role) {
        return new RoleDTO(role.getId(), role.getName(),role.getDescription(), role.getVerantwortungsbereich(), mapRoleTags(role.getRoleTags()));
    }

    public RoleCreationDTO map(RoleCreationWsTo roleCreationWsTo) {
        return new RoleCreationDTO(roleCreationWsTo.name(),roleCreationWsTo.description(), roleCreationWsTo.verantwortungsbereich());
    }

    public Role map(RoleCreationDTO roleCreationDTO) {
        return Role
            .builder()
            .name(roleCreationDTO.name())
            .description(roleCreationDTO.description())
            .verantwortungsbereich(roleCreationDTO.verantwortungsbereich())
            .build();
    }

    public List<RoleDTO> map(List<Role> roles) {
        return roles
                .stream()
                .map(role -> map(role))
                .collect(Collectors.toList());
    }

    public RoleTagDTO map(RoleTag roleTag) {
        return new RoleTagDTO(roleTag.getId(), roleTag.getTag().getId(), roleTag.getRating(), roleTag.getTag().getName());
    }

    public List<RoleTagDTO> mapRoleTags(List<RoleTag> roleTags) {
            return roleTags
                .stream()
                .map(roleTag -> map(roleTag))
                .collect(Collectors.toList());
    }
}