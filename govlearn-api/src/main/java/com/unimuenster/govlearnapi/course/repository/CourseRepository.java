package com.unimuenster.govlearnapi.course.repository;

import com.unimuenster.govlearnapi.course.controller.wsto.CourseUpdateWsTo;
import com.unimuenster.govlearnapi.course.entity.Course;
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
        WHERE (:Providers is null or course.provider IN :Providers)
        AND (:tagIDs is null or course_tag.tag_id IN :tagIDs or course_tag.tag_id is null)
        AND (:Kompetenzstufe is null or course.skilllevel IN :Kompetenzstufe or course.skilllevel is null)
        AND (:Format is null or course.format IN :Format or course.format is null)
        AND (:nameSearch is null OR (lower(course.name) LIKE lower(:nameSearch)) OR (lower(course.description) LIKE lower(:nameSearch)))
        AND (:kostenlos is null OR course.cost_free = :kostenlos or course.cost_free is null)
        AND (:verwaltungsspezifisch is null OR course.domain_specific = :verwaltungsspezifisch)
        AND (:zertifikat is null OR course.certificate = :zertifikat)
        AND (:dauerInMinLaengerAls is null OR course.duration_in_minutes >= :dauerInMinLaengerAls)
        AND (:dauerInMinKuerzerAls is null OR course.duration_in_minutes <= :dauerInMinKuerzerAls)
        AND (cast(:startdatum as date) is null or course.start_date >= :startdatum or course.start_date is null)
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
        SET name = :#{#courseUpdateWsTo.name}, image = :#{#courseUpdateWsTo.image}, link = :#{#courseUpdateWsTo.link}, description = :#{#courseUpdateWsTo.description}, provider = :#{#courseUpdateWsTo.provider}, instructor = :#{#courseUpdateWsTo.instructor}, certificate = :#{#courseUpdateWsTo.certificate}, skilllevel = :#{#courseUpdateWsTo.skilllevel}, durationInMinutes = :#{#courseUpdateWsTo.durationInMinutes}, format = :#{#courseUpdateWsTo.format}, startDate = :#{#courseUpdateWsTo.startDate}, costFree = :#{#courseUpdateWsTo.costFree}, domainSpecific = :#{#courseUpdateWsTo.domainSpecific}
        WHERE id = :#{#courseUpdateWsTo.id}
""")
    void updateCourse(CourseUpdateWsTo courseUpdateWsTo);

}
