package com.unimuenster.govlearnapi.course.service;
import com.unimuenster.govlearnapi.AbstractIntegrationTest;
import com.unimuenster.govlearnapi.course.entity.Course;
import com.unimuenster.govlearnapi.course.service.dto.CourseDTO;
import com.unimuenster.govlearnapi.user.entity.UserEntity;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.unimuenster.govlearnapi.initializer.InitializerService;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CourseServiceTest extends AbstractIntegrationTest{
    @Autowired
    private CourseService courseService;
    @Autowired
    private InitializerService initializer;

    @Test
    void getCourseById() {
        CourseDTO courseDTO = courseService.getCourseById(initializer.getCourse1().getId());
        assertEquals(courseDTO.id(), initializer.getCourse1().getId());
    }

    // Fall: Nutzer, welcher der Ersteller des Kurses ist, löscht diesen
    @Transactional
    @Test
    void deleteCourse() {
        UserEntity creator = initializer.getCourse1().getCreator();
        courseService.deleteCourse(initializer.getCourse1().getId(), creator.getId() );
        List<Course> courses = initializer.getCourseRepository().findAllCourses();
        assertTrue(courses.stream().noneMatch(course -> course.getId().equals(initializer.getCourse1().getId())));
    }

    // Fall: Nutzer, welcher nicht der Ersteller des Kurses ist, versucht diesen zu löschen
    @Test
    void deleteCourse_2() {
        UserEntity notCreator = initializer.getUser2();
        Assertions.assertThatExceptionOfType(RuntimeException.class).isThrownBy(() -> {
            courseService.deleteCourse(initializer.getCourse1().getId(), notCreator.getId() );
        }).withMessage("keine Berechtigung den Kurs zu löschen");
    }

    @Test
    void getCreatedCourses() {
        UserEntity creator = initializer.getCourse1().getCreator();
        List<CourseDTO> courseDTOs = courseService.getCreatedCourses(creator.getId());
        // Kurs 1 sollte in der Liste sein (Ersteller)
        assertTrue(courseDTOs.stream().anyMatch(courseDTO -> courseDTO.id().equals(initializer.getCourse1().getId())));
        // Kurs 2 sollte nicht in der Liste sein (nicht Ersteller)
        assertTrue(courseDTOs.stream().noneMatch(courseDTO -> courseDTO.id().equals(initializer.getCourse2().getId())));
    }
}
