package com.unimuenster.govlearnapi.tags.entity;

import com.unimuenster.govlearnapi.category.entity.Category;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @Column(nullable = false)
    protected String name;
    protected Date createdAt;

    @OneToMany( fetch = FetchType.LAZY , mappedBy = "tag")
    @ToString.Exclude
    private List<CourseTag> courseTags;

    @OneToMany( fetch = FetchType.LAZY , mappedBy = "tag")
    @ToString.Exclude
    private List<UserTag> userTags;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    
    @PrePersist
    private void onCreate() {
        createdAt = new Date();
    }

}
