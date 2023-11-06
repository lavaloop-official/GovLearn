package com.unimuenster.govlearnapi.tags.repository;

import com.unimuenster.govlearnapi.tags.entity.CourseTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseTagRepository extends JpaRepository<CourseTag, Integer> {
    @Query("""
        select ct From CourseTag ct
        Where ct.course.id = :courseId
        """)
    List<CourseTag> getCourseTagsByCourseId(@Param("courseId") Long courseId);
}
