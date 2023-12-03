package com.unimuenster.govlearnapi.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.unimuenster.govlearnapi.category.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query(value = "SELECT c.* FROM category c " +
            "INNER JOIN tag t ON c.id = t.category_id " +
            "INNER JOIN user_tag ut ON t.id = ut.tag_id " +
            "WHERE ut.user_id = ?1 " +
            "GROUP BY c.id " +
            "ORDER BY COUNT(c.id) DESC " +
            "LIMIT ?2", nativeQuery = true)
    List<Category> getMostCommonCategories(int userId, int limit);

    @Query(value = "SELECT c FROM Category c")
    List<Category> findAllCategories();

    @Query(value = "SELECT c FROM Category c WHERE c.id = :categoryID")
    Optional<Category> findCategoryByID(Long categoryID);
}
