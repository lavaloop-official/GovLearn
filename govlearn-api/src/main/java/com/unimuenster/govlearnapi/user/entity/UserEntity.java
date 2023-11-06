package com.unimuenster.govlearnapi.user.entity;

import com.unimuenster.govlearnapi.course.entity.Course;
import com.unimuenster.govlearnapi.tags.entity.Tag;
import com.unimuenster.govlearnapi.tags.entity.UserTag;
import com.unimuenster.govlearnapi.user.service.dto.UserDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @Column(nullable = false)
    protected String password;
    @Column(nullable = false, unique = true)
    protected String email;
    protected String name;
    @Column(nullable = true)
    protected boolean activated;
    protected Date createdAt;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany( fetch = FetchType.LAZY, mappedBy = "user")
    @ToString.Exclude
    private List<Token> tokens;

    @OneToMany( fetch = FetchType.LAZY )
    @ToString.Exclude
    private List<Course> courses;

    @OneToMany( fetch = FetchType.LAZY )
    @ToString.Exclude
    private List<UserTag> userTags;

    @PrePersist
    private void onCreate() {
        createdAt = new Date();
    }

    public List<Tag> getTags(){
        return userTags.stream().map(userTag -> userTag.getTag()).collect(Collectors.toList());
    }

    public UserDTO getDTO(){
        return new UserDTO(email, password);
    }

    public String toString(){
        return "";
    }
}
