package com.unimuenster.govlearnapi.tags.service.mapper;

import com.unimuenster.govlearnapi.tags.entity.Tag;
import com.unimuenster.govlearnapi.tags.service.dto.TagsDTO;
import org.springframework.stereotype.Service;

@Service
public class ServiceTagMapper {

    public TagsDTO map(Tag tag) {
        return new TagsDTO(tag.getId(), tag.getName(), tag.getCategory());
    }
}
