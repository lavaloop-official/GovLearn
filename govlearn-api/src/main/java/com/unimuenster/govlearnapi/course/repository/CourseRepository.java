package com.unimuenster.govlearnapi.course.repository;

import com.unimuenster.govlearnapi.course.entity.Course;
import jakarta.persistence.TemporalType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;

import java.util.Date;
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

    @Query(value = """
        SELECT DISTINCT (Course.*)
        FROM Course
        INNER JOIN course_tag ON course.id = course_tag.course_id
        WHERE course_tag.tag_id IN :tagIDs
        AND course.provider IN :Providers
        AND course.skilllevel IN :Kompetenzstufe
        AND course.format IN :Format
        AND ((lower(course.name) LIKE lower(:nameSearch)) OR (lower(course.description) LIKE lower(:nameSearch)))
        AND (:kostenlos is null OR course.cost_free = :kostenlos)
        AND (:verwaltungsspezifisch is null OR course.domain_specific = :verwaltungsspezifisch)
        AND (:zertifikat is null OR course.certificate = :zertifikat)
        AND (:dauerInMinLaengerAls is null OR course.duration_in_minutes >= :dauerInMinLaengerAls)
        AND (:dauerInMinKuerzerAls is null OR course.duration_in_minutes <= :dauerInMinKuerzerAls)
        AND course.start_date <= COALESCE(:startdatum, course.start_date)
        LIMIT :limit
        OFFSET :offset
    """, nativeQuery = true)
    List<Course> findCoursesByAttributesAndTags(
            Integer limit,
            Integer offset,
            String nameSearch,
            List<String> Providers,
            List<Long> Format,
            List<Long> Kompetenzstufe,
            List<Long> tagIDs,
            Boolean kostenlos,
            Boolean verwaltungsspezifisch,
            Boolean zertifikat,
            @Temporal(TemporalType.DATE)
            Date startdatum,
            Integer dauerInMinLaengerAls,
            Integer dauerInMinKuerzerAls
    );


    @Query(value = """
      SELECT DISTINCT (c.provider) FROM Course c
      """)
    List<String> findAllCourseProviders();
}
