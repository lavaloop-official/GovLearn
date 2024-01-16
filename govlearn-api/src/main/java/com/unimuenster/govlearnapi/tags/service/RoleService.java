package com.unimuenster.govlearnapi.tags.service;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unimuenster.govlearnapi.tags.controller.mapper.ControllerRoleMapper;
import com.unimuenster.govlearnapi.tags.controller.wsto.RoleCreationWsTo;
import com.unimuenster.govlearnapi.tags.controller.wsto.RoleTagCreationWsTo;
import com.unimuenster.govlearnapi.tags.controller.wsto.RoleTagWsTo;
import com.unimuenster.govlearnapi.tags.entity.Role;
import com.unimuenster.govlearnapi.tags.entity.RoleTag;
import com.unimuenster.govlearnapi.tags.entity.Tag;
import com.unimuenster.govlearnapi.tags.exception.NotFoundException;
import com.unimuenster.govlearnapi.tags.repository.RoleRepository;
import com.unimuenster.govlearnapi.tags.repository.RoleTagRepository;
import com.unimuenster.govlearnapi.tags.repository.TagRepository;
import com.unimuenster.govlearnapi.tags.service.dto.RoleDTO;
import com.unimuenster.govlearnapi.tags.service.mapper.ServiceRoleMapper;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final ServiceRoleMapper serviceRoleMapper;
    private final ControllerRoleMapper controllerRoleMapper;
    private final TagRepository tagRepository;
    private final RoleTagRepository roleTagRepository;

    public List<RoleDTO> getAllRoles(){
        List<Role> roles = roleRepository.getAllRoles();

        return serviceRoleMapper.map(roles);
    }

    public void createRole(RoleCreationWsTo roleCreationWsTo){
        roleRepository.save(serviceRoleMapper.map(serviceRoleMapper.map(roleCreationWsTo)));
    }

    @Transactional
    public void createRoleTag(RoleTagCreationWsTo roleTagCreationWsTo) {

        Optional<Tag> byId = tagRepository.findById(roleTagCreationWsTo.tagID());

        if ( byId.isEmpty() ){
            throw new NotFoundException();
        }

        RoleTag roleTag = new RoleTag();
        roleTag.setRating(roleTagCreationWsTo.rating());
        roleTag.setTag(byId.get());

        roleTagRepository.save(roleTag);
    }

    @Transactional
    public void asignRoleTag( Long roleTagID, Long roleID){
        Optional<RoleTag> byId = roleTagRepository.findById(roleTagID);

        if ( byId.isEmpty() ){
            throw new NotFoundException();
        }

        Optional<Role> roleByID = roleRepository.findById(roleID);

        if ( roleByID.isEmpty() ){
            throw new NotFoundException();
        }

        Role role = roleByID.get();
        role.addRoleTag(byId.get());

        roleRepository.save(role);
    }

    public List<RoleTagWsTo> getAllRoleTags(Long roleID){
        List<RoleTag> roleTags = roleTagRepository.getAllRoleTagsByRole(roleID);

        return controllerRoleMapper.mapRoleTagDTOs(serviceRoleMapper.mapRoleTags(roleTags));
    }
}
