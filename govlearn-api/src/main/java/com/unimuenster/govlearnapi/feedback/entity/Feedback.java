package com.unimuenster.govlearnapi.feedback.entity;

import com.unimuenster.govlearnapi.course.entity.Course;
import com.unimuenster.govlearnapi.user.entity.UserEntity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.util.*;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

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

    @ManyToOne( fetch = FetchType.LAZY )
    @Cascade(CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne( fetch = FetchType.LAZY )
    @Cascade(CascadeType.MERGE)
    @JoinColumn(name = "course_id")
    private Course course;

    @PrePersist
    private void onCreate() {
        createdAt = new Date();
    }

    public String toString(){
        return "";
    }
}
