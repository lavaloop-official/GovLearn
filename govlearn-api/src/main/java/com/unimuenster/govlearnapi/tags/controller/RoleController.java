package com.unimuenster.govlearnapi.tags.controller;

import com.unimuenster.govlearnapi.common.responsewrapper.Message;
import com.unimuenster.govlearnapi.common.responsewrapper.Response;
import com.unimuenster.govlearnapi.tags.controller.mapper.ControllerRoleMapper;
import com.unimuenster.govlearnapi.tags.controller.wsto.RoleCreationWsTo;
import com.unimuenster.govlearnapi.tags.controller.wsto.RoleTagCreationWsTo;
import com.unimuenster.govlearnapi.tags.controller.wsto.RoleTagWsTo;
import com.unimuenster.govlearnapi.tags.controller.wsto.RoleWsTo;
import com.unimuenster.govlearnapi.tags.service.RoleService;
import com.unimuenster.govlearnapi.tags.service.dto.RoleDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/roles")
@Slf4j
public class RoleController {
    private final RoleService roleService;
    private final ControllerRoleMapper controllerRoleMapper;

    @Operation(
            security = { @SecurityRequirement(name = "Authorization") },
            description = "Get all Roles"
    )
    @PreAuthorize("hasAuthority('user')")
    @GetMapping("")
    public ResponseEntity<Response> getAllRoles(){

        List<RoleDTO> roleDTOs = roleService.getAllRoles();

        List<RoleWsTo> roleWsTos = controllerRoleMapper.map(roleDTOs);

        return ResponseEntity.ok( Response.of(roleWsTos, new Message(Message.SUCCESS)));
    }

    @Operation(
            security = { @SecurityRequirement(name = "Authorization") },
            description = "Create a Role"
    )
    @PreAuthorize("hasAuthority('user')")
    @PostMapping("")
    public ResponseEntity<Response> createRole(@RequestBody RoleCreationWsTo roleCreationWsTo){

        roleService.createRole(roleCreationWsTo);

        return ResponseEntity.ok( Response.of("", new Message(Message.SUCCESS)));
    }

    @Operation(
            security = { @SecurityRequirement(name = "Authorization") },
            description = "Get all RoleTags from a Role"
    )
    @PreAuthorize("hasAuthority('user')")
    @GetMapping("/RoleTags/{roleID}")
    public ResponseEntity<Response> getAllRoleTags(@PathVariable Long roleID){

        List<RoleTagWsTo> roleTagWsTos = roleService.getAllRoleTags(roleID);

        return ResponseEntity.ok( Response.of(roleTagWsTos, new Message(Message.SUCCESS)));
    }

    @Operation(
            security = { @SecurityRequirement(name = "Authorization") },
            description = "Assign a Role a RoleTag"
    )
    @PreAuthorize("hasAuthority('user')")
    @PostMapping("/RoleTags/{roleTagID}/{roleID}")
    public ResponseEntity<Response> asignRoleTag(@PathVariable Long roleTagID, @PathVariable Long roleID){

        roleService.asignRoleTag(roleTagID,roleID);

        return ResponseEntity.ok( Response.of("", new Message(Message.SUCCESS)));
    }

    @Operation(
            security = { @SecurityRequirement(name = "Authorization") },
            description = "Create a RoleTag"
    )
    @PreAuthorize("hasAuthority('user')")
    @PostMapping("/RoleTags/")
    public ResponseEntity<Response> createRoleTag(@RequestBody RoleTagCreationWsTo roleTagCreationWsTo){

        roleService.createRoleTag(roleTagCreationWsTo);

        return ResponseEntity.ok( Response.of("", new Message(Message.SUCCESS)));
    }
}
