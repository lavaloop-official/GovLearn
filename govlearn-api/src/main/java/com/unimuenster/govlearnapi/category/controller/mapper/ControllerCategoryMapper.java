package com.unimuenster.govlearnapi.category.controller.mapper;

import com.unimuenster.govlearnapi.category.controller.wsto.CategoryWsTo;
import com.unimuenster.govlearnapi.category.entity.Category;
import com.unimuenster.govlearnapi.category.service.dto.CategoryDTO;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ControllerCategoryMapper {

    public CategoryWsTo map(Category category){
        return new CategoryWsTo(category.getId(), category.getName());
    }

    public CategoryWsTo map(CategoryDTO category) {
        return CategoryWsTo
                .builder()
                .id(category.id())
                .name(category.name())
                .build();
    }

    public List<CategoryWsTo> mapList(List<CategoryDTO> categoryDTOs) {
        return categoryDTOs
                .stream()
                .map(category -> map(category))
                .collect(Collectors.toList());
    }
}
