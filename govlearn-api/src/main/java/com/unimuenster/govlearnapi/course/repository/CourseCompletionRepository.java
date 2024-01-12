package com.unimuenster.govlearnapi.course.repository;

import com.unimuenster.govlearnapi.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseCompletionRepository extends JpaRepository<Course, Long> {

    @Query(value = """
        SELECT c
        FROM Course c
        JOIN c.completedBy completedBy
        WHERE completedBy.id = :userId
    """)
    List<Course> getCourseCompletionsByUserId(Long userId);

    @Query("""
        SELECT CASE WHEN EXISTS (
            SELECT c
            FROM Course c 
            JOIN c.completedBy completedBy
            WHERE completedBy.id = :userId
            AND c.id = :courseId
        )
        THEN CAST(1 AS boolean)
        ELSE CAST(0 AS boolean) END
    """)
    Boolean isCourseCompleted( Long userId, Long courseId );
}