package com.unimuenster.govlearnapi.course.controller;

import com.unimuenster.govlearnapi.AbstractIntegrationTest;
import com.unimuenster.govlearnapi.common.responsewrapper.Response;
import com.unimuenster.govlearnapi.core.config.enums.Skilllevel;
import com.unimuenster.govlearnapi.core.config.enums.Format;
import com.unimuenster.govlearnapi.course.controller.wsto.CourseFilterWsTo;
import com.unimuenster.govlearnapi.course.controller.wsto.CourseWsTo;
import com.unimuenster.govlearnapi.course.repository.CourseRepository;
import com.unimuenster.govlearnapi.initializer.InitializerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.*;

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

        CourseFilterWsTo courseFilterWsTo = CourseFilterWsTo
                .builder()
                .tagIDs(new ArrayList<Long>())
                .Anbieter(new ArrayList<String>())
                .Verwaltungsspezifisch(false)
                .Zertifikat(false)
                .Kompetenzstufe(new ArrayList<Skilllevel>())
                .DauerInMinLaengerAls(105)
                .DauerInMinKuerzerAls(150)
                .Format(new ArrayList<Format>())
                .Startdatum(null)
                .Kostenlos(false)
                .build();

        ResponseEntity<Response> responseResponseEntity = courseFilteringController.filterCourses(10, 0, Optional.empty(), courseFilterWsTo);

        assertNotNull(responseResponseEntity);
    }

    @Test
    void test_verwaltungsspezifisch(){
        setCurrentUser();

        CourseFilterWsTo courseFilterWsTo = CourseFilterWsTo
                .builder()
                .Verwaltungsspezifisch(true)
                .build();

        ResponseEntity<Response> responseResponseEntity = courseFilteringController
                .filterCourses(
                        10,
                        0,
                        Optional.empty(),
                        courseFilterWsTo
                );

        List<CourseWsTo> payload = ((Response<List<CourseWsTo>>) responseResponseEntity.getBody()).getPayload();

        assertEquals(1, payload.size());
        assertEquals(initializerService.getCourse2().getId(), payload.get(0).getId());
    }

    @Test
    void test_dauerInMinuten(){
        setCurrentUser();

        CourseFilterWsTo courseFilterWsTo = CourseFilterWsTo
                .builder()
                .DauerInMinKuerzerAls(11)
                .DauerInMinLaengerAls(9)
                .build();

        ResponseEntity<Response> responseResponseEntity = courseFilteringController
                .filterCourses(
                        10,
                        0,
                        Optional.empty(),
                        courseFilterWsTo
                );

        List<CourseWsTo> payload = ((Response<List<CourseWsTo>>) responseResponseEntity.getBody()).getPayload();

        assertEquals(1, payload.size());
        assertEquals(initializerService.getCourse15().getId(), payload.get(0).getId());
    }

    @Test
    void test_Format(){
        setCurrentUser();

        CourseFilterWsTo courseFilterWsTo = CourseFilterWsTo
                .builder()
                .Format(Arrays.asList(Format.Hybrid))
                .DauerInMinKuerzerAls(121)
                .DauerInMinLaengerAls(121)
                .build();

        ResponseEntity<Response> responseResponseEntity = courseFilteringController
                .filterCourses(
                        10,
                        0,
                        Optional.empty(),
                        courseFilterWsTo
                );

        List<CourseWsTo> payload = ((Response<List<CourseWsTo>>) responseResponseEntity.getBody()).getPayload();

        assertEquals(1, payload.size());
        assertEquals(initializerService.getCourse4().getId(), payload.get(0).getId());
    }

    @Test
    void test_Kostenlos(){
        setCurrentUser();

        CourseFilterWsTo courseFilterWsTo = CourseFilterWsTo
                .builder()
                .Kostenlos(true)
                .DauerInMinKuerzerAls(125)
                .DauerInMinLaengerAls(125)
                .build();

        ResponseEntity<Response> responseResponseEntity = courseFilteringController
                .filterCourses(
                        10,
                        0,
                        Optional.empty(),
                        courseFilterWsTo
                );

        List<CourseWsTo> payload = ((Response<List<CourseWsTo>>) responseResponseEntity.getBody()).getPayload();

        assertEquals(1, payload.size());
        assertEquals(initializerService.getCourse5().getId(), payload.get(0).getId());
    }

    @Test
    void test_Anbieter(){
        setCurrentUser();

        CourseFilterWsTo courseFilterWsTo = CourseFilterWsTo
                .builder()
                .Anbieter(Arrays.asList("provider 14"))
                .DauerInMinKuerzerAls(120)
                .DauerInMinLaengerAls(120)
                .build();

        ResponseEntity<Response> responseResponseEntity = courseFilteringController
                .filterCourses(
                        10,
                        0,
                        Optional.empty(),
                        courseFilterWsTo
                );

        List<CourseWsTo> payload = ((Response<List<CourseWsTo>>) responseResponseEntity.getBody()).getPayload();

        assertEquals(1, payload.size());
        assertEquals(initializerService.getCourse14().getId(), payload.get(0).getId());
    }

    @Test
    void test_Kompetenzstufe(){
        setCurrentUser();

        CourseFilterWsTo courseFilterWsTo = CourseFilterWsTo
                .builder()
                .Kompetenzstufe(Arrays.asList(Skilllevel.Anfaenger))
                .Kostenlos(false)
                .Format(Arrays.asList(Format.Hybrid))
                .DauerInMinKuerzerAls(120)
                .DauerInMinLaengerAls(120)
                .build();

        ResponseEntity<Response> responseResponseEntity = courseFilteringController
                .filterCourses(
                        10,
                        0,
                        Optional.empty(),
                        courseFilterWsTo
                );

        List<CourseWsTo> payload = ((Response<List<CourseWsTo>>) responseResponseEntity.getBody()).getPayload();

        assertEquals(1, payload.size());
        assertEquals(initializerService.getCourse1().getId(), payload.get(0).getId());
    }

    @Test
    void test_Zertifikat(){
        setCurrentUser();

        CourseFilterWsTo courseFilterWsTo = CourseFilterWsTo
                .builder()
                .Zertifikat(true)
                .build();

        ResponseEntity<Response> responseResponseEntity = courseFilteringController
                .filterCourses(
                        10,
                        0,
                        Optional.empty(),
                        courseFilterWsTo
                );

        List<CourseWsTo> payload = ((Response<List<CourseWsTo>>) responseResponseEntity.getBody()).getPayload();

        assertEquals(1, payload.size());
        assertEquals(initializerService.getCourse6().getId(), payload.get(0).getId());
    }

    @Test
    void test_Startdatum(){
        setCurrentUser();

        CourseFilterWsTo courseFilterWsTo = CourseFilterWsTo
                .builder()
                // Test funktioniert für die nächsten 2943 Jahre
                .Startdatum(new Date(94583443543244L-1))
                .build();

        ResponseEntity<Response> responseResponseEntity = courseFilteringController
                .filterCourses(
                        10,
                        0,
                        Optional.empty(),
                        courseFilterWsTo
                );

        List<CourseWsTo> payload = ((Response<List<CourseWsTo>>) responseResponseEntity.getBody()).getPayload();

        assertEquals(1, payload.size());
        assertEquals(initializerService.getCourse11().getId(), payload.get(0).getId());
    }

    @Test
    void test_Tags(){
        setCurrentUser();

        CourseFilterWsTo courseFilterWsTo = CourseFilterWsTo
                .builder()
                .tagIDs(
                        Arrays.asList(
                                initializerService.getTag4().getId(),
                                initializerService.getTag5().getId(),
                                initializerService.getTag6().getId()
                        )
                )
                .build();

        ResponseEntity<Response> responseResponseEntity = courseFilteringController
                .filterCourses(
                        10,
                        0,
                        Optional.empty(),
                        courseFilterWsTo
                );

        List<CourseWsTo> payload = ((Response<List<CourseWsTo>>) responseResponseEntity.getBody()).getPayload();

        assertEquals(5, payload.size());
    }

    private void setCurrentUser(){
        UserDetails userDetails = userDetailsService.loadUserByUsername(
                initializerService.getUser2().getEmail()
        );
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}