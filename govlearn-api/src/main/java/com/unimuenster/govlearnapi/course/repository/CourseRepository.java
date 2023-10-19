package com.unimuenster.govlearnapi.course.repository;

import com.unimuenster.govlearnapi.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Integer> {

    Optional<Course> findById(Long courseId);

    @Query(value = """
      select c from Course c
      """)
    List<Course> findAllCourses();
}
