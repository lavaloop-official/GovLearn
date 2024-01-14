package com.unimuenster.govlearnapi.course.entity;

import com.unimuenster.govlearnapi.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = { "completee_id", "course_id" })
}
)
public class CourseCompletion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @ManyToOne(optional = false)
    @ToString.Exclude
    private UserEntity completee;

    @ManyToOne(optional = false)
    @ToString.Exclude
    private Course course;
}
