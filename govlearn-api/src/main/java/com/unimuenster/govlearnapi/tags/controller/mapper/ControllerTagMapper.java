package com.unimuenster.govlearnapi.tags.controller.mapper;

import com.unimuenster.govlearnapi.tags.controller.wsto.TagWsTo;
import com.unimuenster.govlearnapi.tags.controller.wsto.TagCreationWsTo;
import com.unimuenster.govlearnapi.tags.controller.wsto.UserTagWsTo;
import com.unimuenster.govlearnapi.tags.entity.Tag;
import com.unimuenster.govlearnapi.tags.entity.UserTag;
import com.unimuenster.govlearnapi.tags.service.dto.TagCreationDTO;
import com.unimuenster.govlearnapi.tags.service.dto.TagDTO;
import com.unimuenster.govlearnapi.tags.service.dto.UserTagDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ControllerTagMapper {

    public TagCreationDTO map(TagCreationWsTo tagCreationWsTo){
        return new TagCreationDTO(tagCreationWsTo.name(),null);
    }

    public TagWsTo map(Tag tag){
        return new TagWsTo(tag.getId(), tag.getName(), tag.getCategory().getId(),tag.getCategory().getName());
    }

    public TagWsTo map(TagDTO tag) {
        return TagWsTo
                .builder()
                .id(tag.id())
                .name(tag.name())
                .categoryID(tag.categoryID())
                .category(tag.category())
                .build();
    }

    public List<TagWsTo> mapList(List<TagDTO> tags) {
        return tags
                .stream()
                .map(courseDTO1 -> map(courseDTO1))
                .collect(Collectors.toList());
    }

    public List<TagWsTo> mapListOfEntities(List<Tag> tags) {
        return tags
                .stream()
                .map(tag -> map(tag))
                .collect(Collectors.toList());
    }

    public UserTagWsTo mapUserTag(UserTagDTO userTagDTO) {
        return UserTagWsTo
                .builder()
                .id(userTagDTO.id())
                .name(userTagDTO.name())
                .rating(userTagDTO.rating())
                .categoryID(userTagDTO.categoryID())
                .category(userTagDTO.category())
                .build();
    }

    public List<UserTagWsTo> mapListOfUserTags(List<UserTagDTO> userTags) {
        return userTags
                .stream()
                .map(userTagDTO -> mapUserTag(userTagDTO))
                .collect(Collectors.toList());
    }
}
