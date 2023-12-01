package com.unimuenster.govlearnapi.category.service.mapper;

import com.unimuenster.govlearnapi.category.entity.Category;
import com.unimuenster.govlearnapi.category.service.dto.CategoryDTO;

import org.springframework.stereotype.Service;

@Service
public class ServiceCategoryMapper {

    public CategoryDTO map(Category category) {
        return new CategoryDTO(category.getId(), category.getName());
    }
}
