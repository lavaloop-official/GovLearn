package com.unimuenster.govlearnapi.courseCompletion.controller;

import com.unimuenster.govlearnapi.AbstractIntegrationTest;
import com.unimuenster.govlearnapi.common.responsewrapper.Message;
import com.unimuenster.govlearnapi.common.responsewrapper.Response;
import com.unimuenster.govlearnapi.course.service.dto.CourseDTO;
import com.unimuenster.govlearnapi.initializer.InitializerService;
import com.unimuenster.govlearnapi.user.entity.UserEntity;

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
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

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

    @Test
    void isCourseCompleted(){
        ResponseEntity<Response> responseResponseEntity = courseCompletionCotroller.isCourseCompleted(initializerService.getCourse1().getId());
        ResponseEntity<Response> controll = ResponseEntity.ok(Response.of(true , true));
        assertEquals(responseResponseEntity, controll);
    }

     @Test
    void deleteCourseCompletion(){
        courseCompletionCotroller.deleteCourseCompletion(initializerService.getCourse1().getId());
        assertTrue(initializerService.getUser1().getCompleted().stream().noneMatch(course -> course.getId() == initializerService.getCourse1().getId()));
    }

     @Test
    void getUsersCourseCompletion(){

        ResponseEntity<Response> responseResponseEntity  = courseCompletionCotroller.getUsersCourseCompletion();
        //Response completedCourseDTOs = responseResponseEntity.getBody();
        //List<CourseDTO> ids= responseResponseEntity.getBody().getPayload().stream().map(courseDTO.getId()->.....)
        //Wie kann ich hier testen, ob die richtige Liste zurückgegeben wird?
        Message[] messages = responseResponseEntity.getBody().getMessages();

        assertEquals(Message.SUCCESS, messages[0].getMessage());
    }

    private void setCurrentUser(){
        UserDetails userDetails = userDetailsService.loadUserByUsername(
                initializerService.getUser1().getEmail()
        );
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}