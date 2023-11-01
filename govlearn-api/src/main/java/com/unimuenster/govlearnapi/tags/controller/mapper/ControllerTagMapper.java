package com.unimuenster.govlearnapi.tags.controller.mapper;

import com.unimuenster.govlearnapi.tags.controller.wsto.TagWsTo;
import com.unimuenster.govlearnapi.tags.controller.wsto.TagsCreationWsTo;
import com.unimuenster.govlearnapi.tags.service.dto.TagsCreationDTO;
import com.unimuenster.govlearnapi.tags.service.dto.TagsDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ControllerTagMapper {

    public TagsCreationDTO map(TagsCreationWsTo tagsCreationWsTo){
        return new TagsCreationDTO(tagsCreationWsTo.name(), tagsCreationWsTo.category());
    }

    public TagWsTo map(TagsDTO tag) {
        return TagWsTo
                .builder()
                .name(tag.name())
                .category(tag.category())
                .build();
    }

    public List<TagWsTo> mapList(List<TagsDTO> courseDTO) {
        return courseDTO
                .stream()
                .map(courseDTO1 -> map(courseDTO1))
                .collect(Collectors.toList());
    }
}
