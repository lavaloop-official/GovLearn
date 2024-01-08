package com.unimuenster.govlearnapi.course.controller.mapper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControllerCourseMapperTest {


    @Test
    void convertToMinutes() {

        String oneHour = "1 Stunde";

        int minutes = ControllerCourseMapper.convertToMinutes(oneHour);

        assertEquals(60, minutes);
    }

    @Test
    void convertToMinutes_II() {

        String twoHours = "2.6 Stunden";

        int minutes = ControllerCourseMapper.convertToMinutes(twoHours);

        assertEquals(156, minutes);
    }

    @Test
    void convertToStunden() {
        int minutes = 156;

        String hours = ControllerCourseMapper.convertToHours(minutes);

        assertEquals("2.6 Stunden", hours);
    }

    @Test
    void convertToStunden_II() {
        int minutes = 60;

        String hours = ControllerCourseMapper.convertToHours(minutes);

        assertEquals("1 Stunde", hours);
    }

    @Test
    void convertToStunden_III() {
        int minutes = 59;

        String hours = ControllerCourseMapper.convertToHours(minutes);

        assertEquals("1 Stunde", hours);
    }
}