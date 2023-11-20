package com.unimuenster.govlearnapi.course.entity;

import com.unimuenster.govlearnapi.feedback.entity.Feedback;
import com.unimuenster.govlearnapi.tags.entity.CourseTag;
import com.unimuenster.govlearnapi.tags.entity.Tag;
import com.unimuenster.govlearnapi.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils.Null;

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
    protected String description;

    @ManyToOne( fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @ToString.Exclude
    private UserEntity creator;
    protected Date createdAt;

    @OneToMany( fetch = FetchType.LAZY , mappedBy = "course")
    @ToString.Exclude
    private List<CourseTag> courseTags;

    @OneToMany( fetch = FetchType.LAZY , mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @Builder.Default
    private List<Feedback> feedback = new ArrayList<Feedback>();

    public void addFeedback(Feedback Postfeedback) {
        feedback.add(Postfeedback);
        Postfeedback.setCourse(this);
    }
 
    public void removeComment(Feedback Postfeedback) {
        feedback.remove(Postfeedback);
        Postfeedback.setCourse(null);
    }

    public List<Tag> getTags(){
        return courseTags
                .stream()
                .map(courseTag -> courseTag.getTag())
                .collect(Collectors.toList());
    }
    @PrePersist
    private void onCreate() {
        createdAt = new Date();
    }
}
