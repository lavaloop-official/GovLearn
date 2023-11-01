package com.unimuenster.govlearnapi.tags.service;

import com.unimuenster.govlearnapi.tags.entity.Tag;
import com.unimuenster.govlearnapi.tags.exception.NotFoundException;
import com.unimuenster.govlearnapi.tags.repository.TagRepository;
import com.unimuenster.govlearnapi.tags.service.dto.TagsCreationDTO;
import com.unimuenster.govlearnapi.tags.service.dto.TagsDTO;
import com.unimuenster.govlearnapi.tags.service.mapper.ServiceTagMapper;
import com.unimuenster.govlearnapi.user.entity.UserEntity;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagsService {

    private final TagRepository tagRepository;
    private final ServiceTagMapper serviceTagMapper;
    private final EntityManager entityManager;

    public TagsDTO getTagsById(Long courseId){
        Optional<Tag> tagById = tagRepository.findById(courseId);

        if ( tagById.isEmpty() ) {
            throw new NotFoundException();
        }

        TagsDTO map = serviceTagMapper.map(
                tagById.get()
        );

        return map;
    }

    public List<TagsDTO> getTagsByUser(Long userId) {
        List<Tag> allTagsByUserId = tagRepository.findAllTagsByUserId(userId);

        return mapTags(allTagsByUserId);
    }
    
    public void createTag(TagsCreationDTO tagsDTO) {
        
        Tag tag = Tag
                .builder()
                .name(tagsDTO.name())
                .category(tagsDTO.category())
                .build();
        
        tagRepository.save(tag);
    }

    public List<TagsDTO> getTags() {

        List<Tag> allTags = tagRepository.findAllTags();

        return mapTags(allTags);
    }

    private List<TagsDTO> mapTags(List<Tag> tags) {
        return tags
                .stream()
                .map(course -> serviceTagMapper.map(course))
                .collect(Collectors.toList());
    }

    @Transactional
    public void addTagToUser(UserEntity currentUser, long tagId) {

        Optional<Tag> byId = tagRepository.findById(tagId);

        if ( byId.isEmpty() ){
            throw new NotFoundException();
        }

        currentUser.getTags().add(byId.get());

        entityManager.merge(currentUser);
    }
}
