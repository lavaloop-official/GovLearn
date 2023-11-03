package com.unimuenster.govlearnapi;

import com.unimuenster.govlearnapi.user.entity.UserEntity;
import com.unimuenster.govlearnapi.user.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile("test")
@Getter
@Slf4j
@RequiredArgsConstructor
public class Initializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner appReady(){
        return args -> {
            System.out.println("Initializing database");
            insertUser();
        };
    }

    public void insertUser(){
        String test = passwordEncoder.encode("test");

        UserEntity user = new UserEntity();
        user.setActivated(true);
        user.setName("test");
        user.setEmail("test");
        user.setPassword(test);

        userRepository.save(user);
    }
}
