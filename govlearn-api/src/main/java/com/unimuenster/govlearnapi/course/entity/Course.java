package com.unimuenster.govlearnapi.course.entity;

import com.unimuenster.govlearnapi.bookmark.entity.BookmarkedBy;
import com.unimuenster.govlearnapi.courseCompletion.entity.CourseCompletion;
import com.unimuenster.govlearnapi.feedback.entity.Feedback;
import com.unimuenster.govlearnapi.core.config.enums.Format;
import com.unimuenster.govlearnapi.core.config.enums.Skilllevel;
import com.unimuenster.govlearnapi.tags.entity.CourseTag;
import com.unimuenster.govlearnapi.tags.entity.Tag;
import com.unimuenster.govlearnapi.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;
import java.util.stream.Collectors;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(nullable = false)
    protected String name;
    @Column(length = 1024)
    protected String image;
    @Column(length = 1024)
    protected String link;
    @Column(nullable = false, length = 4096)
    protected String description;
    protected Date createdAt;
    @Column(nullable = false)
    protected String provider;
    protected String instructor;
    protected Boolean certificate;
    protected Skilllevel skilllevel;
    protected int durationInMinutes;
    protected Format format;
    @Temporal(TemporalType.DATE)
    protected Date startDate;
    protected Boolean costFree;
    protected Boolean domainSpecific;

    @ManyToOne( fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @ToString.Exclude
    private UserEntity creator;

    @OneToMany( fetch = FetchType.LAZY , mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<CourseTag> courseTags;

    @OneToMany( fetch = FetchType.LAZY , mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @Builder.Default
    private List<Feedback> feedback = new ArrayList<>();

    @OneToMany( fetch = FetchType.LAZY , mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<BookmarkedBy> bookmarkedBy;

    @OneToMany( fetch = FetchType.LAZY , mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<CourseCompletion> completedBy;

    public void addFeedback(Feedback Postfeedback) {
        feedback.add(Postfeedback);
        Postfeedback.setCourse(this);
    }

    @PrePersist
    private void onCreate() {
        createdAt = new Date();
    }
}

