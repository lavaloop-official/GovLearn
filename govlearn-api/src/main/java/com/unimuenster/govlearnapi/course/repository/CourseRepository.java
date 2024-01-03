package com.unimuenster.govlearnapi.course.repository;

import com.unimuenster.govlearnapi.course.controller.wsto.CourseUpdateWsTo;
import com.unimuenster.govlearnapi.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

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
        SELECT c FROM CourseTag c JOIN c.tag t WHERE t.id = :tagId
    """)
    List<Course> findAllCoursesByTagId(Long tagId);


    // May be used for filtering courses by attributes
    @Query(value = """
        SELECT *
        FROM Course
        WHERE (lower(course.name) LIKE lower(:nameSearch)) OR (lower(course.description) LIKE lower(:nameSearch))
        LIMIT :limit
        OFFSET :offset
    """, nativeQuery = true)
    List<Course> findCoursesByAttributes(Integer limit, Integer offset, String nameSearch);

    @Query(value = """
        SELECT c 
        FROM Course c 
        INNER JOIN c.courseTags t 
        INNER JOIN t.tag tag
        INNER JOIN tag.category cat
        WHERE cat.id = :categoryId
    """)
    List<Course> findCoursesByCategory(int categoryId);

    // @Query(value = """
    //     SELECT c 
    //     FROM Course c 
    //     INNER JOIN c.courseTags t 
    //     INNER JOIN t.tag tag
    //     WHERE tag.id IN :tagIDs
    //     AND ((lower(c.name) LIKE lower(concat('%', :nameSearch,'%'))) OR (lower(c.description) LIKE lower(concat('%', :nameSearch,'%'))))
    // """)
    @Query(value = """
        SELECT DISTINCT (Course.*)
        FROM Course
        INNER JOIN course_tag ON course.id = course_tag.course_id
        WHERE course_tag.tag_id IN :tagIDs
        AND course.provider IN :Providers
        AND course.skilllevel IN :Kompetenzstufe
        AND course.format IN :Format
        AND course.cost_free IN :Kosten
        AND ((lower(course.name) LIKE lower(:nameSearch)) OR (lower(course.description) LIKE lower(:nameSearch)))
        LIMIT :limit
        OFFSET :offset
    """, nativeQuery = true)
    List<Course> findCoursesByAttributesAndTags(Integer limit, Integer offset, String nameSearch,List<String> Providers, List<Long> Format, List<Long> Kompetenzstufe, List<Boolean> Kosten, List<Long> tagIDs);


    @Query(value = """
      SELECT DISTINCT (c.provider) FROM Course c
      """)
    List<String> findAllCourseProviders();

    @Modifying
    @Query(value = """
    UPDATE Course
        SET name = :#{#courseUpdateWsTo.name}, image = :#{#courseUpdateWsTo.image}, link = :#{#courseUpdateWsTo.link}, description = :#{#courseUpdateWsTo.description}, provider = :#{#courseUpdateWsTo.provider}, instructor = :#{#courseUpdateWsTo.instructor}, certificate = :#{#courseUpdateWsTo.certificate}, skilllevel = :#{#courseUpdateWsTo.skilllevel}, duration = :#{#courseUpdateWsTo.durationInHours}, format = :#{#courseUpdateWsTo.format}, startDate = :#{#courseUpdateWsTo.startDate}, costFree = :#{#courseUpdateWsTo.costFree}, domainSpecific = :#{#courseUpdateWsTo.domainSpecific}
        WHERE id = :#{#courseUpdateWsTo.id}
""")
    void updateCourse(CourseUpdateWsTo courseUpdateWsTo);
}
