package com.unimuenster.govlearnapi.tags.repository;

import com.unimuenster.govlearnapi.tags.entity.Tag;
import com.unimuenster.govlearnapi.tags.entity.UserTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    @Query(value = """
      SELECT t 
      FROM Tag t 
      WHERE t.id = :tagId
    """)
    Optional<Tag> findById(Long tagId);

    @Modifying
    @Query(value = """
      DELETE 
      FROM user_tag
      WHERE user_id = :userId
      AND tag_id = :tagId
      """, nativeQuery = true)
    void deleteTagFromUser(@Param("userId") Long userId, @Param("tagId") Long tagId);

    @Query(value = """
      SELECT ut 
      FROM UserTag ut
      WHERE ut.user.id = :userId
      """)
    List<UserTag> findAllTagsByUserId(@Param("userId") Long userId);

    @Query(value = """
      SELECT ct.tag
      FROM CourseTag ct
      WHERE ct.course.id = :courseId
      """)
    List<Tag> findAllTagsByCourseId(@Param("courseId")Long courseId);

    @Query(value = """
      SELECT t 
      FROM Tag t
      WHERE t.category.id = :categoryID
      """)
    List<Tag> findAllTagsByCategoryID(@Param("categoryID") Long categoryID);

    @Query(value = """
      SELECT c 
      FROM Tag c
      """)
    List<Tag> findAllTags();

    @Modifying
    @Query(value = """
      DELETE
      FROM course_tag
      WHERE course_id = :courseId
      AND tag_id = :tagId
      """, nativeQuery = true)
    void deleteTagFromCourse(Long courseId, long tagId);
}
