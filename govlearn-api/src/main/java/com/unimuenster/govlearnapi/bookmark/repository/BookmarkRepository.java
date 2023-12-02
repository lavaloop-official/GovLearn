package com.unimuenster.govlearnapi.bookmark.repository;

import com.unimuenster.govlearnapi.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<Course, Long> {

    @Query(value = """
        SELECT * from course where course.id IN (
        SELECT course_id FROM bookmark WHERE user_id = :currentUserId
        ) 
    """, nativeQuery = true)
    public List<Course> getBookmarksByUser(Long currentUserId);
}
