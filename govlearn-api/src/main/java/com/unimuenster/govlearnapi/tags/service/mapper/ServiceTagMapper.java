package com.unimuenster.govlearnapi.tags.service.mapper;

import com.unimuenster.govlearnapi.tags.entity.Tag;
import com.unimuenster.govlearnapi.tags.service.dto.TagDTO;
import org.springframework.stereotype.Service;

@Service
public class ServiceTagMapper {

    public TagDTO map(Tag tag) {
        return new TagDTO(tag.getId(), tag.getName(), tag.getCategory());
    }
}
