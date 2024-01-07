package com.unimuenster.govlearnapi.user.service;

import com.unimuenster.govlearnapi.user.exception.NothingChangedException;
import com.unimuenster.govlearnapi.user.exception.SamePasswordException;
import com.unimuenster.govlearnapi.user.exception.UserExistsException;
import com.unimuenster.govlearnapi.user.service.dto.TokenDTO;
import com.unimuenster.govlearnapi.course.exception.NotFoundException;
import com.unimuenster.govlearnapi.user.controller.wsto.RegisterWsTo;
import com.unimuenster.govlearnapi.user.controller.wsto.UserWsTo;
import com.unimuenster.govlearnapi.user.entity.UserEntity;
import com.unimuenster.govlearnapi.user.repository.UserRepository;
import com.unimuenster.govlearnapi.user.service.dto.UserDTO;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class CustomUserCrudService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationService authenticationService;

    @Transactional
    public TokenDTO createNewUser(UserDTO userDTO){

        boolean userExists = authenticationService.doesUserExist(userDTO.email());

        if (userExists) {
            throw new UserExistsException();
        }

        String encode = passwordEncoder.encode(userDTO.password());

        UserEntity user = UserEntity
                .builder()
                .email(userDTO.email())
                .password(encode)
                .name(userDTO.name())
                .activated(true)
                .build();

        UserEntity save = userRepository.save(user);

        if ( save.isActivated() ){
            TokenDTO userToken = authenticationService.createUserToken(save);

            return userToken;
        }

        return null;
    }

    public UserWsTo UserProfil(){

        UserWsTo userWsTo = new UserWsTo(authenticationService.getCurrentUser().getEmail(),authenticationService.getCurrentUser().getName());

        return userWsTo;
    }

    public List<UserWsTo> getAllUser(){
        List<UserEntity> userEntities = userRepository.findAll();
        List<UserWsTo> userWsTos = userEntities.stream().map(element -> {
            UserWsTo userWsTo = new UserWsTo(element.getEmail(), element.getName());
            return userWsTo;
        }).collect(Collectors.toList());

        return userWsTos;
    }

    public List<UserWsTo> getAllUserWithoutGroup(Long groupID){
        List<UserEntity> userEntities = userRepository.findAllUserWithoutGroup(groupID);
        List<UserWsTo> userWsTos = userEntities.stream().map(element -> {
            UserWsTo userWsTo = new UserWsTo(element.getEmail(), element.getName());
            return userWsTo;
        }).collect(Collectors.toList());

        return userWsTos;
    }

    public UserWsTo getUserByID(Long userID){

        Optional<UserEntity> userEntity = userRepository.findUserById(userID);

        if(userEntity.isEmpty())
        {
            throw new NotFoundException();
        }

        UserWsTo userWsTo = new UserWsTo(userEntity.get().getEmail(), userEntity.get().getName());

        return userWsTo;
    }
    
    @Transactional
    public TokenDTO updateUser(Long userID, RegisterWsTo userWsTo){


        boolean emailExists = authenticationService.doesUserMailExistExceptOwnMail(userWsTo.email());
        //checkt ob email bereits vergeben ist.
        if (emailExists) {
            throw new UserExistsException();
        }

        // Lade alten nutzer
        Optional<UserEntity> optionalUserEntity = userRepository.findUserById(userID);

        if(optionalUserEntity.isEmpty()){
            throw new NotFoundException();
        }

        UserEntity userEntity = optionalUserEntity.get();

        if(userWsTo.email().equals(userEntity.getEmail()) && userWsTo.name().equals(userEntity.getName()))
            throw new NothingChangedException();

        // Setze auf alten nutzer neue felder
        if(userWsTo.email() != userEntity.getEmail())
            userEntity.setEmail(userWsTo.email());
        if(userWsTo.name() != userEntity.getName())
            userEntity.setName(userWsTo.name());

        // Speicher neuen Nutzer ab
        UserEntity save = userRepository.save(userEntity);

        UserDTO UserDTO = new UserDTO(save.getEmail(), userWsTo.password(), save.getName());

        TokenDTO authenticate = authenticationService.authenticate(UserDTO);

        return authenticate;
    }

    @Transactional
    public TokenDTO updateUserPassword(Long userID, RegisterWsTo userWsTo){

        // Lade alten nutzer
        Optional<UserEntity> optionalUserEntity = userRepository.findUserById(userID);

        if(optionalUserEntity.isEmpty()){
            throw new NotFoundException();
        }

        UserEntity userEntity = optionalUserEntity.get();

        if(passwordEncoder.matches(userWsTo.password(), userEntity.getPassword()))
            throw new SamePasswordException();
        
        String encode = passwordEncoder.encode(userWsTo.password());

        userEntity.setPassword(encode);
        
        // Speicher neuen Nutzer ab
        UserEntity save = userRepository.save(userEntity);

        UserDTO UserDTO = new UserDTO(save.getEmail(), userWsTo.password(), save.getName());

        TokenDTO authenticate = authenticationService.authenticate(UserDTO);

        return authenticate;

    }
}
