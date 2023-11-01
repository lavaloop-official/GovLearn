package com.unimuenster.govlearnapi.tags.repository;

import com.unimuenster.govlearnapi.course.entity.Course;
import com.unimuenster.govlearnapi.tags.entity.Tag;
import com.unimuenster.govlearnapi.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Integer> {

    Optional<Tag> findById(Long courseId);

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
