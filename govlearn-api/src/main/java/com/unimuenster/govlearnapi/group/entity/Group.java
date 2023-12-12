package com.unimuenster.govlearnapi.group.entity;

import com.unimuenster.govlearnapi.course.entity.Course;
import com.unimuenster.govlearnapi.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "group_table")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected Date createdAt;

    private String name;

    @ManyToMany( fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Course> courses;

    @ManyToOne( fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private UserEntity creator;

    @ManyToMany( fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<UserEntity> members;

    @PrePersist
    private void onCreate() {
        createdAt = new Date();
    }
}
