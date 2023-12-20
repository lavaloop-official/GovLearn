package com.unimuenster.govlearnapi.category.controller;

import com.unimuenster.govlearnapi.category.controller.mapper.ControllerCategoryMapper;
import com.unimuenster.govlearnapi.category.controller.wsto.CategoryWsTo;
import com.unimuenster.govlearnapi.category.service.CategoryService;
import com.unimuenster.govlearnapi.category.service.dto.CategoryDTO;
import com.unimuenster.govlearnapi.common.responsewrapper.Message;
import com.unimuenster.govlearnapi.common.responsewrapper.Response;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/category")
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;
    private final ControllerCategoryMapper controllerCategoryMapper;

    @Operation(
            security = { @SecurityRequirement(name = "Authorization") },
            description = "Get a category by id."
    )
    @PreAuthorize("hasAuthority('user')")
    @GetMapping("/{id}")
    public ResponseEntity<Response> getCategoryById(@PathVariable Long id) {

        CategoryDTO categoryById = categoryService.getCategoryByID(id);

        CategoryWsTo map = controllerCategoryMapper.map(categoryById);

        return ResponseEntity.ok( Response.of(map, new Message(Message.SUCCESS)));
    }

    @Operation(
            security = { @SecurityRequirement(name = "Authorization") },
            description = "Get all categories."
    )
    @PreAuthorize("hasAuthority('user')")
    @GetMapping("")
    public ResponseEntity<Response> getAllCategories() {

        List<CategoryDTO> categories = categoryService.getAllCategories();

        List<CategoryWsTo> map = controllerCategoryMapper.mapList(categories);

        return ResponseEntity.ok( Response.of(map, new Message(Message.SUCCESS)));
    }
}
