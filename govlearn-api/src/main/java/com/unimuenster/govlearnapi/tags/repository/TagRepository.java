package com.unimuenster.govlearnapi.tags.repository;

import com.unimuenster.govlearnapi.tags.entity.Tag;
import com.unimuenster.govlearnapi.tags.entity.UserTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Integer> {
    @Query(value = """
SELECT t FROM Tag t where t.id = :tagId
""")
    Optional<Tag> findById(Long tagId);

    @Modifying
    @Query(value = """
      delete from user_tag
      WHERE user_id = :userId
      AND tag_id = :tagId
      """, nativeQuery = true)
    void deleteTagFromUser(@Param("userId") Long userId, @Param("tagId") Long tagId);

    @Query(value = """
      select ut from UserTag ut
      where ut.user.id = :userId
      """)
    List<UserTag> findAllTagsByUserId(@Param("userId") Long userId);
    @Query(value = """
select ct.tag from CourseTag ct
where ct.course.id = :courseId
""")
    List<Tag> findAllTagsByCourseId(@Param("courseId")Long courseId);

    @Query(value = """
      select c from Tag c
      """)
    List<Tag> findAllTags();

    @Modifying
    @Query(value = """
      delete from course_tag
      WHERE course_id = :courseId
      AND tag_id = :tagId
      """, nativeQuery = true)
    void deleteTagFromCourse(Long courseId, long tagId);
}
