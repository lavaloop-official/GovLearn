package com.unimuenster.govlearnapi.course.service;

import com.unimuenster.govlearnapi.AbstractIntegrationTest;
import com.unimuenster.govlearnapi.Initializer;
import com.unimuenster.govlearnapi.course.service.dto.CourseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CourseFilteringServiceTest extends AbstractIntegrationTest {

    @Autowired
    private CourseFilteringService courseFilteringService;
    @Autowired
    private Initializer initializer;

    @Test
    void filterCourses() {

        List<CourseDTO> courseDTOS = courseFilteringService.filterCourses("1");

        assertEquals(1, courseDTOS.size());
        assertEquals(courseDTOS.get(0).id(), initializer.getCourse1().getId());
    }

    @Test
    void filterCourses_2() {

        List<CourseDTO> courseDTOS = courseFilteringService.filterCourses("2");

        assertEquals(1, courseDTOS.size());
        assertEquals(courseDTOS.get(0).id(), initializer.getCourse2().getId());
    }
}