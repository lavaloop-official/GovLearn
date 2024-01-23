package com.unimuenster.govlearnapi.feedback.entity;

import com.unimuenster.govlearnapi.core.config.enums.ReportReason;
import com.unimuenster.govlearnapi.user.entity.UserEntity;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Feedback_Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected String report_message;

    protected ReportReason report_reason;

    protected Date createdAt;

    @ManyToOne( fetch = FetchType.LAZY )
    @Cascade(CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne( fetch = FetchType.LAZY )
    @Cascade(CascadeType.MERGE)
    @JoinColumn(name = "feedback_id")
    private Feedback feedback;

    @PrePersist
    private void onCreate() {
        createdAt = new Date();
    }
}
