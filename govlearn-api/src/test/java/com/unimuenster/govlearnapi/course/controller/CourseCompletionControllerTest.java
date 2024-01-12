package com.unimuenster.govlearnapi.course.controller;

import com.unimuenster.govlearnapi.AbstractIntegrationTest;
import com.unimuenster.govlearnapi.course.service.CourseCompletionService;
import com.unimuenster.govlearnapi.initializer.InitializerService;
import com.unimuenster.govlearnapi.user.service.AuthenticationService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CourseCompletionControllerTest extends AbstractIntegrationTest {

    @Autowired
    CourseCompletionService courseCompletionService;
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    InitializerService initializerService;
    @Autowired
    AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        setCurrentUser();
    }

    @Test
    void addCourseCompletion() {
        courseCompletionService.addCourseCompletion(authenticationService.getCurrentUser(), initializerService.getCourse1().getId());

        assertTrue(initializerService.getUser1().getCompleted().stream().anyMatch(course -> course.getId() == initializerService.getCourse1().getId()));
    }

    @Test
    void isCourseCompleted(){
        assertTrue(courseCompletionService.isCourseCompleted(initializerService.getUser1(),initializerService.getCourse1().getId()));
    }

    @Test
    void getUsersCourseCompletion(){
        assertTrue(courseCompletionService.getUsersCourseCompletion(initializerService.getUser1()).size() == 1);
    }

    @Test
    void deleteCourseCompletion(){
        courseCompletionService.deleteCourseCompletion(initializerService.getUser1(), initializerService.getCourse1().getId());
        assertTrue(initializerService.getUser1().getCompleted().stream().noneMatch(course -> course.getId() == initializerService.getCourse1().getId()));
    }

    private void setCurrentUser(){
        UserDetails userDetails = userDetailsService.loadUserByUsername(
                initializerService.getUser1().getEmail()
        );
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}