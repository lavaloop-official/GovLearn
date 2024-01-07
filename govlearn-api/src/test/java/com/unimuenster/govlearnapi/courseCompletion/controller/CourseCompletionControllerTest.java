package com.unimuenster.govlearnapi.courseCompletion.controller;

import com.unimuenster.govlearnapi.AbstractIntegrationTest;
import com.unimuenster.govlearnapi.common.responsewrapper.Message;
import com.unimuenster.govlearnapi.common.responsewrapper.Response;
import com.unimuenster.govlearnapi.initializer.InitializerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CourseCompletionControllerTest extends AbstractIntegrationTest {

    @Autowired
    courseCompletionCotroller courseCompletionCotroller;
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    InitializerService initializerService;

    @BeforeEach
    void setUp() {
        setCurrentUser();
    }

    @Test
    void addCourseCompletion() {


        ResponseEntity<Response> responseResponseEntity =
                courseCompletionCotroller
                        .addCourseCompletion(
                                initializerService.getCourse13().getId()
                        );

        Message[] messages = responseResponseEntity.getBody().getMessages();

        assertEquals(Message.SUCCESS, messages[0].getMessage());
    }

    private void setCurrentUser(){
        UserDetails userDetails = userDetailsService.loadUserByUsername(
                initializerService.getUser2().getEmail()
        );
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}