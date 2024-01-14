package com.unimuenster.govlearnapi.course.repository;

import com.unimuenster.govlearnapi.course.entity.CourseCompletion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseCompletionRepository extends JpaRepository<CourseCompletion, Long> {

    @Query(value = """
        SELECT cc
        FROM CourseCompletion cc
        WHERE cc.completee.id = :userId
    """)
    List<CourseCompletion> getCourseCompletionsByUserId(Long userId);

    @Query("""
        select case when (count(cc) > 0)
        then true else false end
        from CourseCompletion cc
        where cc.course.id = :courseId
        and cc.completee.id = :userId
    """)
    Boolean isCourseCompleted( Long userId, Long courseId );

    @Modifying
    @Query("""
        delete from CourseCompletion cc
        where cc.course.id = :courseId
        and cc.completee.id = :userId
    """)
    void deleteCourseCompletion(Long userId, Long courseId);
}