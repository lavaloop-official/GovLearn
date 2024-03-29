package com.unimuenster.govlearnapi.user.controller;

import com.unimuenster.govlearnapi.common.responsewrapper.Message;
import com.unimuenster.govlearnapi.common.responsewrapper.Response;
import com.unimuenster.govlearnapi.mail.service.EmailService;
import com.unimuenster.govlearnapi.user.controller.wsto.AuthenticationWsTo;
import com.unimuenster.govlearnapi.user.controller.wsto.RegisterWsTo;
import com.unimuenster.govlearnapi.user.controller.wsto.RequestResetWsTo;
import com.unimuenster.govlearnapi.user.controller.wsto.ResetWsTo;
import com.unimuenster.govlearnapi.user.service.AuthenticationService;
import com.unimuenster.govlearnapi.user.service.CustomUserCrudService;
import com.unimuenster.govlearnapi.user.service.dto.TokenDTO;
import com.unimuenster.govlearnapi.user.service.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

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
    private final EmailService emailService;

    @Operation(
            description = "Create a persistent user and receive an auth-token."
    )
    @PostMapping("/users")
    public ResponseEntity<Response<TokenDTO>> createUser(@RequestBody RegisterWsTo registerRequest){
        UserDTO userDTO = new UserDTO(
                registerRequest.email(),
                registerRequest.password(),
                registerRequest.name()
        );

        TokenDTO newUser = customUserCrudService.createNewUser(userDTO);

        return ResponseEntity.ok(Response.of(newUser, new Message(Message.SUCCESS)));
    }

    @Operation(
            description = "Receive an auth-token, to authenticate with other rest calls."
    )
    @Transactional
    @PostMapping("/users/auth-token")
    public ResponseEntity<Response<TokenDTO>> authenticate(@RequestBody AuthenticationWsTo authenticationWsTo) {
        UserDTO userDTO = new UserDTO(authenticationWsTo.email(), authenticationWsTo.password(), authenticationWsTo.name());

        return ResponseEntity.ok( Response.of(authenticationService.authenticate(userDTO), new Message(Message.SUCCESS)));
    }


    @Operation(
        security = { @SecurityRequirement(name = "Authorization") },
        description = "Returns the users profil"
    )
    @PreAuthorize("hasAuthority('user')")
    @GetMapping("/users")
    public ResponseEntity<Response> getUser() {

        return ResponseEntity.ok(Response.of(customUserCrudService.UserProfil(), new Message(Message.SUCCESS)));
    }

    @Operation(
        security = { @SecurityRequirement(name = "Authorization") },
        description = "Returns all users"
    )
    @PreAuthorize("hasAuthority('user')")
    @GetMapping("/users/all")
    public ResponseEntity<Response> getAllUser(Optional<Long> groupID) {

        if(groupID.isPresent())
            return ResponseEntity.ok(Response.of(customUserCrudService.getAllUserWithoutGroup(groupID.get()), new Message(Message.SUCCESS)));

        return ResponseEntity.ok(Response.of(customUserCrudService.getAllUser(), new Message(Message.SUCCESS)));
    }

    @Operation(
        security = { @SecurityRequirement(name = "Authorization") },
        description = "Returns a user by ID"
    )
    @PreAuthorize("hasAuthority('user')")
    @GetMapping("/users/{userID}")
    public ResponseEntity<Response> getUserByID(@PathVariable Long userID) {

        return ResponseEntity.ok(Response.of(customUserCrudService.getUserByID(userID), new Message(Message.SUCCESS)));
    }

    
    
    @Operation(
        security= {@SecurityRequirement(name="Authorization")},
        description= "Update the username or usermail"
    )
    @PreAuthorize("hasAuthority('user')")

    @PutMapping("/users")
    public ResponseEntity<Response> updateNameOrEMail(@RequestBody RegisterWsTo user){
        
        TokenDTO tokenDTO = customUserCrudService.updateUser(authenticationService.getCurrentUser().getId(), user);

        // Da Email im Token gespeichert wird, muss der token neu generiert werden
        return ResponseEntity.ok(Response.of(tokenDTO, new Message(Message.SUCCESS)));

    };

    @Operation(
        security= {@SecurityRequirement(name="Authorization")},
        description= "Update User Password"
    )
    @PreAuthorize("hasAuthority('user')")
    @PutMapping("/users/password")
    public ResponseEntity<Response> updateUserPassword(@RequestBody RegisterWsTo user){
        
        TokenDTO tokenDTO = customUserCrudService.updateUserPassword(authenticationService.getCurrentUser().getId(), user);

        return ResponseEntity.ok(Response.of(tokenDTO, new Message(Message.SUCCESS)));
    };

    @Operation(
            description= "Request a password reset token."
    )
    @PostMapping("/users/reset/request")
    public ResponseEntity<Response> requestReset(@RequestBody RequestResetWsTo user){

        customUserCrudService.requestResetToken(user.email());

        return ResponseEntity.ok(Response.of(true));
    };

    @Operation(
            description= "Reset the password with the given token."
    )
    @PutMapping("/users/reset")
    public ResponseEntity<Response> resetPassword(@RequestBody ResetWsTo resetWsTo){

        TokenDTO tokenDTO = customUserCrudService.resetUserPassword(resetWsTo);

        return ResponseEntity.ok(Response.of(tokenDTO, new Message(Message.SUCCESS)));
    };
    
}
