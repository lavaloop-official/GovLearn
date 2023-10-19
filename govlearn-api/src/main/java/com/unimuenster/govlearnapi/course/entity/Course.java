package com.unimuenster.govlearnapi.course.entity;

import com.unimuenster.govlearnapi.user.entity.Token;
import com.unimuenster.govlearnapi.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

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

    @ManyToOne( fetch = FetchType.LAZY )
    @ToString.Exclude
    private UserEntity creator;
    protected Date createdAt;

    @PrePersist
    private void onCreate() {
        createdAt = new Date();
    }
}
