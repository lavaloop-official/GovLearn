package com.unimuenster.govlearnapi.tags.service;

import com.unimuenster.govlearnapi.AbstractIntegrationTest;
import com.unimuenster.govlearnapi.course.entity.Course;
import com.unimuenster.govlearnapi.course.service.CourseService;
import com.unimuenster.govlearnapi.course.service.dto.CourseDTO;
import com.unimuenster.govlearnapi.course.service.mapper.ServiceCourseMapper;
import com.unimuenster.govlearnapi.initializer.InitializerService;
import com.unimuenster.govlearnapi.tags.controller.wsto.AddTagToCourseWsTo;
import com.unimuenster.govlearnapi.tags.controller.wsto.AddTagToUserWsTo;
import com.unimuenster.govlearnapi.tags.entity.CourseTag;
import com.unimuenster.govlearnapi.tags.entity.UserTag;
import com.unimuenster.govlearnapi.tags.entity.VectorTag;
import com.unimuenster.govlearnapi.tags.repository.TagRepository;
import com.unimuenster.govlearnapi.tags.repository.UserTagRepository;
import com.unimuenster.govlearnapi.tags.service.dto.TagDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class TagsServiceTest extends AbstractIntegrationTest {

    @Autowired
    InitializerService initializer;
    @Autowired
    TagService tagService;
    @Autowired
    CourseService courseService;
    @Autowired
    ServiceCourseMapper serviceCourseMapper;
    @Autowired
    TagRepository tagRepository;
    @Autowired
    UserTagService userTagService;
    @Autowired
    UserTagRepository userTagRepository;
    @Autowired
    CourseTagService courseTagService;


    @Test
    void getTagsById(){

        TagDTO tagsById = tagService.getTagsById(initializer.getCourse1().getId());

        assertEquals(tagsById.id(), initializer.getTag1().getId());
    }

    @Test
    void getTagsByUserId(){

        List<TagDTO> tagsByUser = tagService.getTagsByUser(initializer.getUser2().getId());

        assertEquals(3, tagsByUser.size());
        assertEquals(tagsByUser.get(0).id(), initializer.getTag2().getId());
    }

    @Test
    @Transactional
    void addTagToUser(){
        List<AddTagToUserWsTo> addTagToUserWsTos = new ArrayList<AddTagToUserWsTo>();

        addTagToUserWsTos.add(new AddTagToUserWsTo(initializer.getTag2().getId(), 1));

        // Add tag 2 to user 1
        userTagService.addTagsToUser(initializer.getUser1(), addTagToUserWsTos);

        List<UserTag> userTagByUserId = userTagRepository.getUserTagByUserId(initializer.getUser1().getId());

        assertTrue( userTagByUserId.stream().anyMatch(userTag -> Objects.equals(userTag.getTag().getId(), initializer.getTag2().getId())));
    }

    @Test
    @Transactional
    void addTagToCourse(){
        CourseDTO courseDTO = courseService.getCourseById(initializer.getCourse1().getId());

        // Add tag 2 to course 1
        AddTagToCourseWsTo addTagToCourseWsTo = new AddTagToCourseWsTo(
                courseDTO.id(), initializer.getTag2().getId(), 1
        );

        courseTagService.addTagToCourse(addTagToCourseWsTo);

        Course course = serviceCourseMapper.map(courseDTO);
        List<VectorTag> courseTags = courseTagService.getCourseTags(course);

        assertTrue(courseTags.stream().anyMatch(courseTag -> Objects.equals(courseTag.getTag().getId(), initializer.getTag2().getId())));
    }
}