package com.unimuenster.govlearnapi.course.repository;

import com.unimuenster.govlearnapi.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Integer> {

    @Query(value = """
select c from Course c where c.id = :courseId
""")
    Optional<Course> findById(Long courseId);

    @Query(value = """
      select c from Course c
      """)
    List<Course> findAllCourses();

    @Query(value = """
SELECT c FROM CourseTag c JOIN c.tag t WHERE t.id = :tagId
    """)
    List<Course> findAllCoursesByTagId(Long tagId);


    // May be used for filtering courses by attributes
    @Query(value = """
        select c 
        from Course c 
        where c.description like %:nameSearch%
    """)
    List<Course> findCoursesByAttributes(String nameSearch);
}
