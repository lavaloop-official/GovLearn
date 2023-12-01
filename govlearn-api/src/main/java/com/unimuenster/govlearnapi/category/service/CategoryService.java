package com.unimuenster.govlearnapi.category.service;

import com.unimuenster.govlearnapi.category.entity.Category;
import com.unimuenster.govlearnapi.category.repository.CategoryRepository;
import com.unimuenster.govlearnapi.category.service.dto.CategoryDTO;
import com.unimuenster.govlearnapi.category.service.mapper.ServiceCategoryMapper;
import com.unimuenster.govlearnapi.category.exception.NotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ServiceCategoryMapper serviceCategoryMapper;

    public CategoryDTO getCategoryByID(Long categoryID) {
        Optional<Category> category = categoryRepository.findCategoryByID(categoryID);

        if (category.isEmpty())
            throw new NotFoundException();

        CategoryDTO categoryDTO = CategoryDTO
                .builder()
                .id(category.get().getId())
                .name(category.get().getName())
                .build();
        return categoryDTO;
    }

    public List<CategoryDTO> getAllCategories() {
        List<Category> allCategories = categoryRepository.findAllCategories();

        return mapCategories(allCategories);
    }

    private List<CategoryDTO> mapCategories(List<Category> categories) {
        return categories
                .stream()
                .map(category -> serviceCategoryMapper.map(category))
                .collect(Collectors.toList());
    }
}
