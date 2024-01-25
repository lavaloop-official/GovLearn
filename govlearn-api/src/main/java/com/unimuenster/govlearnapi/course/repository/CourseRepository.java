package com.unimuenster.govlearnapi.course.repository;

import com.unimuenster.govlearnapi.course.entity.Course;
import com.unimuenster.govlearnapi.course.service.dto.CourseUpdateDTO;

import jakarta.persistence.TemporalType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query(value = """
        SELECT c FROM Course c WHERE c.id = :courseId
    """)
    Optional<Course> findById(Long courseId);

    @Query(value = """
      SELECT c FROM Course c
      """)
    List<Course> findAllCourses();

    @Query(value = """
      SELECT c FROM Course c
      WHERE c.id <> :courseId
      """)
    List<Course> findAllCoursesUnequal(Long courseId);

    @Query(value = """
        SELECT c
        FROM Course c
        WHERE c NOT IN (SELECT m.courses FROM Member m WHERE m.id = :groupmemberID)
        """)
    List<Course> getCoursesWithoutGroupmember(Long groupmemberID);

    @Query(value = """
        SELECT c FROM CourseTag c JOIN c.tag t WHERE t.id = :tagId
    """)
    List<Course> findAllCoursesByTagId(Long tagId);

    @Query(value = """
        SELECT c 
        FROM Course c 
        INNER JOIN c.courseTags t 
        INNER JOIN t.tag tag
        INNER JOIN tag.category cat
        WHERE cat.id = :categoryId
    """)
    List<Course> findCoursesByCategory(int categoryId);

    @Query(value = """
    SELECT c FROM Course c where c.creator.id = :userId
    """)
    List<Course> getCreatedCourses(Long userId);

    @Query(value = """
      SELECT DISTINCT (c.provider) FROM Course c
      """)
    List<String> findAllCourseProviders();

    @Modifying
    @Query(value = """
    UPDATE Course
    SET name = :#{#courseUpdateDTO.name},
    image = :#{#courseUpdateDTO.image},
    link = :#{#courseUpdateDTO.link},
    description = :#{#courseUpdateDTO.description},
    provider = :#{#courseUpdateDTO.provider},
    instructor = :#{#courseUpdateDTO.instructor},
    certificate = :#{#courseUpdateDTO.certificate},
    skilllevel = :#{#courseUpdateDTO.skilllevel},
    durationInMinutes = :#{#courseUpdateDTO.durationInMinutes},
    format = :#{#courseUpdateDTO.format},
    startDate = :#{#courseUpdateDTO.startDate},
    costFree = :#{#courseUpdateDTO.costFree},
    domainSpecific = :#{#courseUpdateDTO.domainSpecific}
    WHERE id = :#{#courseUpdateDTO.id}
    """)
    void updateCourse(CourseUpdateDTO courseUpdateDTO);

}
