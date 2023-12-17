package com.unimuenster.govlearnapi.feedback.repository;

import com.unimuenster.govlearnapi.feedback.entity.Feedback;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    @Query(value = """
        select f from Feedback f where f.course.id = :courseID and f.user.id = :userID
        """)
    List<Feedback> findFeedbackByCourseAndUser(Long courseID, Long userID);

    @Query(value = """
        select f from Feedback f
        """)
    List<Feedback> findAllFeedbacks();

    @Query(value = """
        SELECT * FROM feedback WHERE course_id = :courseID LIMIT :limit OFFSET :offset
        """, nativeQuery = true)
    List<Feedback> findAllFeedbackByCourseIdWithLimitAndOffset(Long courseID, Long limit, Long offset);

    @Query(value = """
        SELECT f FROM Feedback f WHERE f.user.id = :userID
        """)
    List<Feedback> findAllFeedbackByUserId(Long userID);

    @Query(value = """
        SELECT AVG(rating) FROM feedback WHERE course_id = :courseID
        """, nativeQuery = true)
    Float findAverageFeedbackByCourseId(Long courseID);

    @Query(value = """
        SELECT COUNT(rating) FROM feedback WHERE course_id = :courseID
        """, nativeQuery = true)
    Long findAmountFeedbackByCourseId(Long courseID);

    @Modifying
    @Query(value = """
        DELETE FROM Feedback WHERE id = :feedbackID
        """)
    void deleteFeedbackFromCourse(Long feedbackID);

    @Modifying
    @Query(value = """
        UPDATE Feedback 
        SET title = :title, description = :description, rating = :rating
        WHERE id = :feedbackID
        """)
    void updateFeedbackFromCourse(Long feedbackID, String title, String description, Integer rating);
}