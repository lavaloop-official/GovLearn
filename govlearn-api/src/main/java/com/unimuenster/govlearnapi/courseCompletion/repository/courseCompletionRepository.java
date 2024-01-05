package com.unimuenster.govlearnapi.courseCompletion.repository;

import com.unimuenster.govlearnapi.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface courseCompletionRepository extends JpaRepository<Course, Long> {

    @Query(value = """
        SELECT * from course where course.id IN (
        SELECT course_id FROM courseCompletion WHERE user_id = :currentUserId
        ) 
    """, nativeQuery = true)
    public List<Course> getCourseCompletionsByUserId(Long currentUserId);




    @Query("SELECT COUNT(ucc) > 0 FROM UserCourseCompletion ucc WHERE ucc.userId = :userId AND ucc.courseId = :courseId")
    public boolean isCourseCompleted(@Param("userId") Long userId, @Param("courseId") Long courseId);


}
