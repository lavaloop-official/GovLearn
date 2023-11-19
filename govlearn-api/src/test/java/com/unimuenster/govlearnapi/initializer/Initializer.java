package com.unimuenster.govlearnapi.initializer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
@Getter
@Slf4j
@RequiredArgsConstructor
public class Initializer {

    private final InitializerService initializerService;

    @Bean
    public CommandLineRunner appReady(){
        return args -> {
            System.out.println("Initializing database");
            initializerService.init();
        };
    }
}
