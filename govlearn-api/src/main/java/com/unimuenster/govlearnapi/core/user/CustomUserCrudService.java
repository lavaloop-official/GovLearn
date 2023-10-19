package com.unimuenster.govlearnapi.core.user;

import com.unimuenster.govlearnapi.core.entity.UserEntity;
import com.unimuenster.govlearnapi.core.repository.UserRepository;
import com.unimuenster.govlearnapi.core.user.dto.TokenDTO;
import com.unimuenster.govlearnapi.core.user.dto.UserDTO;
import lombok.RequiredArgsConstructor;
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

        String encode = passwordEncoder.encode(userDTO.password());

        UserEntity user = UserEntity
                .builder()
                .email(userDTO.email())
                .password(encode)
                .activated(true)
                .build();

        UserEntity save = userRepository.save(user);

        if ( save.isActivated() ){
            TokenDTO userToken = authenticationService.createUserToken(save);

            return userToken;
        }

        return null;
    }
}
