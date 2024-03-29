package com.unimuenster.govlearnapi.group.entity;

import com.unimuenster.govlearnapi.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "group_table")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    private String name;
    @Column(length = 4096)
    private String description;

    @OneToMany( fetch = FetchType.LAZY , mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Member> members = new ArrayList<>();

    protected Date createdAt;

    @PrePersist
    private void onCreate() {
        createdAt = new Date();
    }
}
