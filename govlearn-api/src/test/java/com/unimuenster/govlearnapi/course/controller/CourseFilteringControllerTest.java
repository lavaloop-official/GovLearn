package com.unimuenster.govlearnapi.course.controller;

import com.unimuenster.govlearnapi.AbstractIntegrationTest;
import com.unimuenster.govlearnapi.common.responsewrapper.Response;
import com.unimuenster.govlearnapi.initializer.InitializerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CourseFilteringControllerTest extends AbstractIntegrationTest {

    @Autowired
    private CourseFilteringController courseFilteringController;

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private InitializerService initializerService;

    @Test
    void filterCourses() {

        setCurrentUser();

        ResponseEntity<Response> responseResponseEntity = courseFilteringController.filterCourses(10, 0, Optional.empty(), List.of(1L, 2L, 3L));

        assertNotNull(responseResponseEntity);
    }

    private void setCurrentUser(){
        UserDetails userDetails = userDetailsService.loadUserByUsername(
                initializerService.getUser2().getEmail()
        );
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}