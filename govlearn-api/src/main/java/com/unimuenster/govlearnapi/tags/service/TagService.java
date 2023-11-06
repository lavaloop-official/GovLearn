package com.unimuenster.govlearnapi.tags.service;

import com.unimuenster.govlearnapi.course.controller.wsto.CourseWsTo;
import com.unimuenster.govlearnapi.course.entity.Course;
import com.unimuenster.govlearnapi.course.repository.CourseRepository;
import com.unimuenster.govlearnapi.tags.entity.Tag;
import com.unimuenster.govlearnapi.tags.entity.UserTag;
import com.unimuenster.govlearnapi.tags.exception.NotFoundException;
import com.unimuenster.govlearnapi.tags.repository.TagRepository;
import com.unimuenster.govlearnapi.tags.service.dto.TagCreationDTO;
import com.unimuenster.govlearnapi.tags.service.dto.TagDTO;
import com.unimuenster.govlearnapi.tags.service.mapper.ServiceTagMapper;
import com.unimuenster.govlearnapi.user.entity.UserEntity;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;
    private final ServiceTagMapper serviceTagMapper;
    private final EntityManager entityManager;
    private final CourseRepository courseRepository;

    public TagDTO getTagsById(Long courseId){
        Optional<Tag> tagById = tagRepository.findById(courseId);

        if ( tagById.isEmpty() ) {
            throw new NotFoundException();
        }

        TagDTO map = serviceTagMapper.map(
                tagById.get()
        );

        return map;
    }

    public List<TagDTO> getTagsByUser(Long userId) {
        List<UserTag> allTagsByUserId = tagRepository.findAllTagsByUserId(userId);

        List<Tag> collect = allTagsByUserId
                .stream()
                .filter(userTag -> userTag.getTag() != null)
                .map(userTag -> userTag.getTag())
                .collect(Collectors.toList());

        return mapTags(collect);
    }

    public List<TagDTO> getTagsByCourse(Long courseId) {
        List<Tag> allTagsByCourseId = tagRepository.findAllTagsByCourseId(courseId);

        return mapTags(allTagsByCourseId);
    }
    
    public void createTag(TagCreationDTO tagsDTO) {
        
        Tag tag = Tag
                .builder()
                .name(tagsDTO.name())
                .category(tagsDTO.category())
                .build();
        
        tagRepository.save(tag);
    }

    public List<TagDTO> getTags() {

        List<Tag> allTags = tagRepository.findAllTags();

        return mapTags(allTags);
    }

    private List<TagDTO> mapTags(List<Tag> tags) {
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
        byId.get().getUsers().add(currentUser);

        tagRepository.save(byId.get());
    }

    @Transactional
    public void addTagToCourse(long courseId, long tagId) {

        Optional<Tag> tag = tagRepository.findById(tagId);
        Optional<Course> course = courseRepository.findById(courseId);
        if ( tag.isEmpty() || course.isEmpty() ){
            throw new NotFoundException();
        }

        course.get().getTags().add(tag.get());

        entityManager.merge(course.get());
    }

    @Transactional
    public void deleteTagFromUser(UserEntity currentUser, long tagId) {

        tagRepository.deleteTagFromUser(currentUser.getId(), tagId);
    }

    @Transactional
    public void deleteTagFromCourse(CourseWsTo course, long tagId) {
        tagRepository.deleteTagFromCourse(course.getId(), tagId);
    }
}
