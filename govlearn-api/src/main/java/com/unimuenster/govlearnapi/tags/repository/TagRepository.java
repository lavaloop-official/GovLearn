package com.unimuenster.govlearnapi.tags.repository;

import com.unimuenster.govlearnapi.tags.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Integer> {

    Optional<Tag> findById(Long tagId);

    @Modifying
    @Query(value = """
      delete from user_tag
      WHERE user_id = :userId
      AND tag_id = :tagId
      """, nativeQuery = true)
    void deleteTagFromUser(@Param("userId") Long userId, @Param("tagId") Long tagId);

    @Query(value = """
      select u.tags from UserEntity u
      where u.id = :userId
      """)
    List<Tag> findAllTagsByUserId(@Param("userId") Long userId);

    @Query(value = """
      select c from Tag c
      """)
    List<Tag> findAllTags();
}
