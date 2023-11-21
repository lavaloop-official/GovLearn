package com.unimuenster.govlearnapi.course.service;

import com.unimuenster.govlearnapi.AbstractIntegrationTest;
import com.unimuenster.govlearnapi.course.service.dto.CourseDTO;
import com.unimuenster.govlearnapi.initializer.InitializerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CourseFilteringServiceTest extends AbstractIntegrationTest {

    @Autowired
    private CourseFilteringService courseFilteringService;
    @Autowired
    private InitializerService initializer;

    @Test
    void filterCourses() {

        List<CourseDTO> courseDTOS = courseFilteringService.filterCourses("1");

        assertEquals(7, courseDTOS.size());
        assertEquals(courseDTOS.get(0).id(), initializer.getCourse1().getId());
    }

    @Test
    void filterCourses_2() {

        List<CourseDTO> courseDTOS = courseFilteringService.filterCourses("2");

        assertEquals(2, courseDTOS.size());
        assertEquals(courseDTOS.get(0).id(), initializer.getCourse2().getId());
    }
}