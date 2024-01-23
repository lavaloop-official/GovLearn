package com.unimuenster.govlearnapi.feedback.repository;

import com.unimuenster.govlearnapi.feedback.entity.Feedback_Report;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FeedbackReportRepository extends JpaRepository<Feedback_Report, Long> {

    @Query(value = """
        SELECT fr 
        FROM Feedback_Report fr 
        WHERE fr.id = :feedbackReportID
        """)
    Optional<Feedback_Report> findFeedbackReportByID(Long feedbackReportID);

    @Query(value = """
        SELECT fr 
        FROM Feedback_Report fr
        """)
    List<Feedback_Report> findAllFeedbackReport();
}