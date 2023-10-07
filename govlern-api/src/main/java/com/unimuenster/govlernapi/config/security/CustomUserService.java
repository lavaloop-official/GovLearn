package com.unimuenster.govlernapi.config.security;


import com.unimuenster.govlernapi.entity.UserEntity;
import com.unimuenster.govlernapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomUserService {
    private final UserRepository userRepository;
    public Optional<UserEntity> findEntityByEmail(String email) {
        UserEntity byEmail = userRepository.findByEmail(email);

        if ( byEmail == null )
            return Optional.empty();

        return Optional.of(byEmail);
    }

    public UserEntity findByEmail(String email){
        Optional<UserEntity> entityByEmail = findEntityByEmail(email);

        if (entityByEmail.isEmpty()){
            return null;
        }

        return entityByEmail.get();
    }
}
