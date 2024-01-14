package com.unimuenster.govlearnapi.course.entity;

import com.unimuenster.govlearnapi.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseCompletion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @ManyToOne()
    @ToString.Exclude
    private UserEntity completee;

    @ManyToOne()
    @ToString.Exclude
    private Course course;
}
