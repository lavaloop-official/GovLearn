package com.unimuenster.govlearnapi.course.service;

import com.unimuenster.govlearnapi.AbstractIntegrationTest;
import com.unimuenster.govlearnapi.course.service.dto.CourseDTO;
import com.unimuenster.govlearnapi.group.controller.GroupTestBase;
import com.unimuenster.govlearnapi.initializer.InitializerService;
import com.unimuenster.govlearnapi.tags.service.TagService;
import com.unimuenster.govlearnapi.tags.service.dto.TagDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CourseSimilarityServiceTest extends GroupTestBase {
    @Autowired
    private CourseSimilarityService courseSimilarityService;
    @Autowired
    private InitializerService initializer;
    @Autowired
    private TagService tagService;

    @Test
    void getMostSimilarCourses(){
        setCurrentUser(initializer.getUser1());
        List<TagDTO> tags = tagService.getTags();
        List<CourseDTO> courseDTOs = courseSimilarityService.getMostSimilarCourses(initializer.getCourse6().getId(), tags);
        // Kurs mit welchem verglichen wird, sollte nicht in der Liste sein
        assertTrue(courseDTOs.stream().noneMatch(courseDTO -> courseDTO.id().equals(initializer.getCourse6().getId())));
        // Kurs 11 sollte die gleichen Tags wie Kurs 6 besitzen und dem entsprechend angezeigt werden
        assertTrue(courseDTOs.stream().anyMatch(courseDTO -> courseDTO.id().equals(initializer.getCourse11().getId())));
    }
}
