package com.unimuenster.govlearnapi.user.service;

import com.unimuenster.govlearnapi.core.config.security.CustomUserDetails;
import com.unimuenster.govlearnapi.core.config.security.JwtService;
import com.unimuenster.govlearnapi.group.controller.wsto.InvitationWsTo;
import com.unimuenster.govlearnapi.user.controller.wsto.UserWsTo;
import com.unimuenster.govlearnapi.user.entity.Token;
import com.unimuenster.govlearnapi.user.entity.TokenType;
import com.unimuenster.govlearnapi.user.entity.UserEntity;
import com.unimuenster.govlearnapi.user.repository.TokenRepository;
import com.unimuenster.govlearnapi.user.repository.UserRepository;
import com.unimuenster.govlearnapi.user.service.dto.TokenDTO;
import com.unimuenster.govlearnapi.user.service.dto.UserDTO;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    public TokenDTO authenticate(UserDTO clearPassword) {
        UserEntity userByEmail = getUserByEmail(clearPassword.email());

        if ( notFound( userByEmail ) ){
            throw new UsernameNotFoundException("Username " + clearPassword.email() + " not found in db");
        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                clearPassword.email(),
                clearPassword.password()
        );
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        return createUserToken(userByEmail);
    }

    public TokenDTO createUserToken(UserEntity userByEmail){
        CustomUserDetails customUserDetails = new CustomUserDetails(userByEmail);

        String jwtToken = jwtService.generateToken(customUserDetails);
        revokeAllUserTokens(userByEmail);
        saveUserToken(userByEmail, jwtToken);

        return new TokenDTO(jwtToken);
    }

    private void saveUserToken(UserEntity user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(UserEntity user) {
        tokenRepository.deleteAllValidTokenByUser(Math.toIntExact(user.getId()));
    }

    public UserEntity getUserByEmail(String email){
        UserEntity byEmail = userRepository.findByEmail(email);

        if ( notFound(byEmail) ){
            throw new UsernameNotFoundException("Username not found!");
        }

        return byEmail;
    }

    public List<UserEntity> getListOfUsersByEmail(List<InvitationWsTo> invitationsWsTos){

        List<UserEntity> userEntities = userRepository.findAllUsersByEmail(invitationsWsTos.stream().map(element -> element.getUserEmail()).toList());

        return userEntities;
    }

    public boolean doesUserExist(String email) {
        UserEntity byEmail = userRepository.findByEmail(email);

        return ! notFound(byEmail);
    }

    public UserEntity getCurrentUser(){
        String emailOfCurrentUser = SecurityContextHolder.getContext().getAuthentication().getName();

        return getUserByEmail(emailOfCurrentUser);
    }

    private static boolean notFound(UserEntity user){
        return user == null;
    }
}
