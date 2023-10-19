package com.unimuenster.govlearnapi.user.controller;

import com.unimuenster.govlearnapi.common.responsewrapper.Message;
import com.unimuenster.govlearnapi.common.responsewrapper.Response;
import com.unimuenster.govlearnapi.user.controller.wsto.RegisterWsTo;
import com.unimuenster.govlearnapi.user.entity.UserEntity;
import com.unimuenster.govlearnapi.user.service.AuthenticationService;
import com.unimuenster.govlearnapi.user.service.CustomUserCrudService;
import com.unimuenster.govlearnapi.user.service.dto.TokenDTO;
import com.unimuenster.govlearnapi.user.service.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
@Slf4j
@Validated
public class UserController {

    private final AuthenticationService authenticationService;
    private final CustomUserCrudService customUserCrudService;

    @Operation(
            description = "Create a persistent user and receive an auth-token."
    )
    @PostMapping("/users")
    public ResponseEntity<Response<TokenDTO>> createUser(@RequestBody RegisterWsTo registerRequest){
        UserDTO userDTO = new UserDTO(
                registerRequest.email(),
                registerRequest.password()
        );

        TokenDTO newUser = customUserCrudService.createNewUser(userDTO);

        return ResponseEntity.ok(Response.of(newUser, new Message(Message.SUCCESS)));
    }

    @Operation(
            description = "Receive an auth-token, to authenticate with other rest calls."
    )
    @Transactional
    @GetMapping("/users/auth-token")
    public ResponseEntity<Response<TokenDTO>> authenticate(String email, String password) {
        UserDTO userDTO = new UserDTO(email, password);

        return ResponseEntity.ok( Response.of(authenticationService.authenticate(userDTO), new Message(Message.SUCCESS)));
    }


    @Operation(
            security = { @SecurityRequirement(name = "Authorization") },
            description = "Allows the user to check, whether the auth-token is still valid and whether te user is a content-creator."
    )
    @PreAuthorize("hasAuthority('user')")
    @GetMapping("/users")
    public ResponseEntity<Response> getUser() {
        UserEntity currentUser = authenticationService.getCurrentUser();
        log.info(currentUser.getEmail());

        return ResponseEntity.ok( Response.of(true) );
    }
}
