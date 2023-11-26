package com.unimuenster.govlearnapi.course.repository;

import com.unimuenster.govlearnapi.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Integer> {

    @Query(value = """
        SELECT c FROM Course c WHERE c.id = :courseId
    """)
    Optional<Course> findById(Long courseId);

    @Query(value = """
      SELECT c FROM Course c
      """)
    List<Course> findAllCourses();

    @Query(value = """
        SELECT c FROM CourseTag c JOIN c.tag t WHERE t.id = :tagId
    """)
    List<Course> findAllCoursesByTagId(Long tagId);


    // May be used for filtering courses by attributes
    @Query(value = """
        SELECT c FROM Course c WHERE (c.name LIKE %:nameSearch%) OR (c.description LIKE %:nameSearch%)
    """)
    List<Course> findCoursesByAttributes(String nameSearch);
}
