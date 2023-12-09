package com.unimuenster.govlearnapi.category.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import com.unimuenster.govlearnapi.tags.entity.Tag;

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
