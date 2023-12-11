package com.unimuenster.govlearnapi.course.service;

import com.unimuenster.govlearnapi.AbstractIntegrationTest;
import com.unimuenster.govlearnapi.course.service.dto.CourseDTO;
import com.unimuenster.govlearnapi.initializer.InitializerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CourseFilteringServiceTest extends AbstractIntegrationTest {

    @Autowired
    private CourseFilteringService courseFilteringService;
    @Autowired
    private InitializerService initializer;

    // @Test
    // void filterCourses() {

    //     List<CourseDTO> courseDTOS = courseFilteringService.filterCourses("1", new ArrayList<Long>());

    //     assertEquals(28, courseDTOS.size());
    //     assertEquals(courseDTOS.get(0).id(), initializer.getCourse10().getId());
    // }

    // @Test
    // void filterCourses_2() {

    //     List<CourseDTO> courseDTOS = courseFilteringService.filterCourses("2", new ArrayList<Long>());
      
    //     assertEquals(20, courseDTOS.size());
    //     assertEquals(courseDTOS.get(0).id(), initializer.getCourse12().getId());
    // }

    // @Test
    // void caseInsensitiveTest() {

    //     String lowerCaseSearchString = initializer.getCourse1().getName().toLowerCase();

    //     List<CourseDTO> courseDTOS = courseFilteringService.filterCourses(lowerCaseSearchString, new ArrayList<Long>());

    //     assertEquals(courseDTOS.get(0).id(), initializer.getCourse1().getId());
    // }
}