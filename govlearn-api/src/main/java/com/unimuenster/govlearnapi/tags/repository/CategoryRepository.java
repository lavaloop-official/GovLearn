package com.unimuenster.govlearnapi.tags.repository;

import com.unimuenster.govlearnapi.tags.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query(value = "SELECT c.* FROM category c " +
            "INNER JOIN tag t ON c.id = t.category_id " +
            "INNER JOIN user_tag ut ON t.id = ut.tag_id " +
            "WHERE ut.user_id = ?1 " +
            "GROUP BY c.id " +
            "ORDER BY COUNT(c.id) DESC " +
            "LIMIT ?2", nativeQuery = true)
    List<Category> getMostCommonCategories(int userId, int limit);
}
