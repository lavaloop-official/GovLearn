package com.unimuenster.govlearnapi.feedback.entity;

import com.unimuenster.govlearnapi.course.entity.Course;
import com.unimuenster.govlearnapi.user.entity.UserEntity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.util.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String title;
    protected String description;
    @Min(1)
    @Max(5)
    protected Integer rating;
    protected Date createdAt;

    @ManyToOne( fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne( fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany( fetch = FetchType.LAZY , mappedBy = "feedback", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @Builder.Default
    private List<Feedback_Report> feedback_report = new ArrayList<Feedback_Report>();

    @PrePersist
    private void onCreate() {
        createdAt = new Date();
    }

    public void addFeedbackReport(Feedback_Report PostfeedbackReport) {
        feedback_report.add(PostfeedbackReport);
        PostfeedbackReport.setFeedback(this);
    }
}
