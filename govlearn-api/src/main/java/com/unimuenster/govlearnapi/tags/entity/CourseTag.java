package com.unimuenster.govlearnapi.tags.entity;

import com.unimuenster.govlearnapi.course.entity.Course;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tag_id", referencedColumnName = "id")
    private Tag tag;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;


    protected Date createdAt;

    @PrePersist
    private void onCreate() {
        createdAt = new Date();
    }
}
