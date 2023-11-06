package com.unimuenster.govlearnapi.user.entity;

import com.unimuenster.govlearnapi.course.entity.Course;
import com.unimuenster.govlearnapi.tags.entity.Tag;
import com.unimuenster.govlearnapi.user.service.dto.UserDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.*;

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
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name= "user_tag",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    Set<Tag> tags = new HashSet<>();

    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany( fetch = FetchType.LAZY, mappedBy = "user")
    @ToString.Exclude
    private List<Token> tokens;

    @OneToMany( fetch = FetchType.LAZY )
    @ToString.Exclude
    private List<Course> courses;

    @PrePersist
    private void onCreate() {
        createdAt = new Date();
    }

    public UserDTO getDTO(){
        return new UserDTO(email, password);
    }

    public void removeTag(long tagId){
        Tag tag = this.tags.stream().filter(t -> t.getId() == tagId).findFirst().orElse(null);
        if(tag != null){
            this.tags.remove(tag);
            tag.getUsers().remove(this);
        }
    }

    public String toString(){
        return "";
    }
}
