package com.unimuenster.govlearnapi.tags.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @OneToMany( fetch = FetchType.LAZY , mappedBy = "category")
    @ToString.Exclude
    private List<Tag> tags;

    @Column(nullable = false)
    private String name;
}
