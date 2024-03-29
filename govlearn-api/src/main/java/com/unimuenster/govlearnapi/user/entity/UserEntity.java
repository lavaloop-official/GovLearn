package com.unimuenster.govlearnapi.user.entity;
import com.unimuenster.govlearnapi.bookmark.entity.BookmarkedBy;
import com.unimuenster.govlearnapi.courseCompletion.entity.CourseCompletion;
import com.unimuenster.govlearnapi.feedback.entity.Feedback;
import com.unimuenster.govlearnapi.feedback.entity.Feedback_Report;
import com.unimuenster.govlearnapi.group.entity.Member;
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

    @Column(nullable = true)
    @ToString.Exclude
    private String resetToken;

    @ToString.Exclude
    @OneToMany( fetch = FetchType.LAZY , mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Member> members = new ArrayList<>();

    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany( fetch = FetchType.LAZY, mappedBy = "user")
    @ToString.Exclude
    private List<Token> tokens;

    @OneToMany( fetch = FetchType.LAZY, mappedBy = "user")
    @ToString.Exclude
    private List<UserTag> userTags;

    @OneToMany( fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @Builder.Default
    private List<Feedback> feedback = new ArrayList<Feedback>();

    @OneToMany( fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @Builder.Default
    private List<Feedback_Report> feedback_report = new ArrayList<Feedback_Report>();

    @OneToMany( fetch = FetchType.LAZY , mappedBy = "bookmarkee", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<BookmarkedBy> bookmarkedBy;

    @OneToMany( fetch = FetchType.LAZY , mappedBy = "completee", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<CourseCompletion> completedBy;

    @PrePersist
    private void onCreate() {
        createdAt = new Date();
    }

    public List<Tag> getTags(){
        return userTags.stream().map(userTag -> userTag.getTag()).collect(Collectors.toList());
    }

    public UserDTO getDTO(){
        return new UserDTO(email, password, name);
    }

    public String toString(){
        return "";
    }
}
