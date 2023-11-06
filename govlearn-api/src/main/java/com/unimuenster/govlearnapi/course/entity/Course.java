package com.unimuenster.govlearnapi.course.entity;

import com.unimuenster.govlearnapi.tags.entity.CourseTag;
import com.unimuenster.govlearnapi.tags.entity.Tag;
import com.unimuenster.govlearnapi.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;
import java.util.stream.Collectors;

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

    @OneToMany( fetch = FetchType.LAZY )
    @ToString.Exclude
    private List<CourseTag> courseTags;

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
