package com.unimuenster.govlearnapi.user.service;

import com.unimuenster.govlearnapi.user.exception.UserExistsException;
import com.unimuenster.govlearnapi.user.service.dto.TokenDTO;
import com.unimuenster.govlearnapi.course.exception.NotFoundException;
import com.unimuenster.govlearnapi.user.controller.wsto.RegisterWsTo;
import com.unimuenster.govlearnapi.user.controller.wsto.UserWsTo;
import com.unimuenster.govlearnapi.user.entity.UserEntity;
import com.unimuenster.govlearnapi.user.repository.UserRepository;
import com.unimuenster.govlearnapi.user.service.dto.UserDTO;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

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

        boolean userExists = authenticationService.doesUserExist(userWsTo.email());
        //checkt ob email bereits vergeben ist.
        if (userExists) {
            throw new UserExistsException();
        }

        //Teste, ob Änderung für korrekten User

        String encode = passwordEncoder.encode(userWsTo.password());

        // Lade alten nutzer

        Optional<UserEntity> optionalUserEntity = userRepository.findUserById(userID);

        if(optionalUserEntity.isEmpty()){
            throw new NotFoundException();
        }

        UserEntity userEntity = optionalUserEntity.get();
        

        // Setze auf alten nutzer neue felder

        userEntity.setEmail(userWsTo.email());
        userEntity.setPassword(encode);
        userEntity.setName(userWsTo.name());

        // Speicher neuen Nutzer ab
        UserEntity save = userRepository.save(userEntity);

        UserWsTo UserFeedback = new UserWsTo(save.getEmail(), save.getName());
        UserDTO UserDTO = new UserDTO(UserFeedback.email(), userWsTo.password(), UserFeedback.name());

        TokenDTO authenticate = authenticationService.authenticate(UserDTO);

        return authenticate;
    }
}
