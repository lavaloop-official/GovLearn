package com.unimuenster.govlearnapi.courseCompletion.repository;

import com.unimuenster.govlearnapi.AbstractIntegrationTest;
import com.unimuenster.govlearnapi.initializer.InitializerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.*;

class CourseCompletionRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    courseCompletionRepository courseCompletionRepository;

    @Autowired
    InitializerService initializerService;

    @Test
    void testDoubleBookingInCompletedCourses() {

        assertThrows(DataIntegrityViolationException.class, () -> {

                    initializerService.getUser1().getCompleted().add(initializerService.getCourse1());
                    initializerService.getUser1().getCompleted().add(initializerService.getCourse1());

                    courseCompletionRepository.save(initializerService.getCourse1());
        });

    }

}