package com.unimuenster.govlearnapi.course.entity;

import com.unimuenster.govlearnapi.core.config.enums.Format;
import com.unimuenster.govlearnapi.core.config.enums.Skilllevel;
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
    protected String name;
    protected String image;

    @Column(nullable = false)
    protected String description;
    protected Date createdAt;
    @Column(nullable = false)
    protected String provider;
    protected String instructor;
    protected Boolean certificate;
    protected Skilllevel skilllevel;
    protected Double durationInHours;
    protected Format format;
    @Column(nullable = false)
    protected Date startDate;
    protected Boolean costFree;
    protected Boolean domainSpecific;

    @ManyToOne( fetch = FetchType.LAZY )
    @ToString.Exclude
    private UserEntity creator;


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

