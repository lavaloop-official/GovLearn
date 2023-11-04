package com.unimuenster.govlearnapi.tags.controller.mapper;

import com.unimuenster.govlearnapi.tags.controller.wsto.TagWsTo;
import com.unimuenster.govlearnapi.tags.controller.wsto.TagsCreationWsTo;
import com.unimuenster.govlearnapi.tags.entity.Tag;
import com.unimuenster.govlearnapi.tags.service.dto.TagCreationDTO;
import com.unimuenster.govlearnapi.tags.service.dto.TagDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ControllerTagMapper {

    public TagCreationDTO map(TagsCreationWsTo tagsCreationWsTo){
        return new TagCreationDTO(tagsCreationWsTo.name(), tagsCreationWsTo.category());
    }

    public TagWsTo map(Tag tag){
        return new TagWsTo(tag.getName(), tag.getCategory());
    }

    public TagWsTo map(TagDTO tag) {
        return TagWsTo
                .builder()
                .name(tag.name())
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
}
