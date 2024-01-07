package com.unimuenster.govlearnapi.courseCompletion.repository;

import com.unimuenster.govlearnapi.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface courseCompletionRepository extends JpaRepository<Course, Long> {

    @Query(value = """
        SELECT course
        FROM Course course
        JOIN course.completedBy completedBy
        WHERE completedBy.id = :userId
    """)
    List<Course> getCourseCompletionsByUserId(Long userId);

    @Query("""
        SELECT COUNT(course) 
        FROM Course course 
        JOIN course.completedBy completedBy
        WHERE completedBy.id = :userId
        AND course.id = :courseId
    """)
    Long countCourseCompleted( Long userId, Long courseId );
}
