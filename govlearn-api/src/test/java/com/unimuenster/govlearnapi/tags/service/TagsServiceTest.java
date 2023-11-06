package com.unimuenster.govlearnapi.tags.service;

import com.unimuenster.govlearnapi.AbstractIntegrationTest;
import com.unimuenster.govlearnapi.Initializer;
import com.unimuenster.govlearnapi.tags.entity.Tag;
import com.unimuenster.govlearnapi.tags.repository.TagRepository;
import com.unimuenster.govlearnapi.tags.service.dto.TagDTO;
import com.unimuenster.govlearnapi.user.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    @Test
    void getTagsById(){

        TagDTO tagsById = tagsService.getTagsById(initializer.getCourse1().getId());

        assertEquals(tagsById.id(), initializer.getTag1().getId());
    }

    @Test
    void getTagsByUserId(){

        List<TagDTO> tagsByUser = tagsService.getTagsByUser(initializer.getUser2().getId());

        assertEquals(tagsByUser.size(), 1);
        assertEquals(tagsByUser.get(0).id(), initializer.getTag2().getId());
    }

    @Test
    @Transactional
    void addTagToUser(){

        // Add tag 2 to user 1
        tagsService.addTagToUser(initializer.getUser1(), initializer.getTag2().getId());

        Optional<Tag> loadedTag = tagRepository.findById(initializer.getTag2().getId());
        List<UserEntity> userEntities =  new ArrayList<>();
        userEntities.addAll(loadedTag.get().getUsers());

        assertTrue(userEntities.stream().anyMatch( userEntity -> userEntity.getId() == initializer.getUser1().getId()));
    }
}