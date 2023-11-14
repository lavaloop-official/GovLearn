package com.unimuenster.govlearnapi.tags.entity;

import com.unimuenster.govlearnapi.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @Column(nullable = false)
    protected String name;
    @Column(nullable = false)
    protected String category;
    protected Date createdAt;

    @OneToMany( fetch = FetchType.LAZY , mappedBy = "tag")
    @ToString.Exclude
    private List<CourseTag> courseTags;

    @OneToMany( fetch = FetchType.LAZY , mappedBy = "tag")
    @ToString.Exclude
    private List<UserTag> userTags;

    public List<UserEntity> getUsers(){
        return userTags
                .stream()
                .map(userTag -> userTag.getUser())
                .collect(Collectors.toList());
    }

    @PrePersist
    private void onCreate() {
        createdAt = new Date();
    }
    public String toString(){
        return "";
    }
}
