package com.unimuenster.govlearnapi.course.repository;

import com.unimuenster.govlearnapi.course.entity.Course;
import jakarta.persistence.TemporalType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;

import java.util.Date;
import java.util.List;

public interface CourseFilterRepository extends JpaRepository<Course, Long> {

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
}
