package com.unimuenster.govlearnapi.tags.service;

import com.unimuenster.govlearnapi.AbstractIntegrationTest;
import com.unimuenster.govlearnapi.Initializer;
import com.unimuenster.govlearnapi.tags.entity.UserTag;
import com.unimuenster.govlearnapi.tags.repository.TagRepository;
import com.unimuenster.govlearnapi.tags.repository.UserTagRepository;
import com.unimuenster.govlearnapi.tags.service.dto.TagDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TagsServiceTest extends AbstractIntegrationTest {

    @Autowired
    Initializer initializer;
    @Autowired
    TagService tagsService;
    @Autowired
    TagRepository tagRepository;
    @Autowired
    UserTagService userTagService;
    @Autowired
    UserTagRepository userTagRepository;

    @Test
    void getTagsById(){

        TagDTO tagsById = tagsService.getTagsById(initializer.getCourse1().getId());

        assertEquals(tagsById.id(), initializer.getTag1().getId());
    }

    @Test
    void getTagsByUserId(){

        List<TagDTO> tagsByUser = tagsService.getTagsByUser(initializer.getUser2().getId());

        assertEquals(1, tagsByUser.size());
        assertEquals(tagsByUser.get(0).id(), initializer.getTag2().getId());
    }

    @Test
    @Transactional
    void addTagToUser(){

        // Add tag 2 to user 1
        userTagService.addTagToUser(initializer.getUser1(), initializer.getTag2().getId());

        List<UserTag> userTagByUserId = userTagRepository.getUserTagByUserId(initializer.getUser1().getId());

        assertTrue( userTagByUserId.stream().anyMatch(userTag -> userTag.getTag().getId() == initializer.getTag2().getId()));
    }
}