package com.unimuenster.govlearnapi.tags.entity;

import com.unimuenster.govlearnapi.feedback.entity.Feedback_Report;
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
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @ManyToMany
    @JoinColumn(name = "role_tag_id")
    @Builder.Default
    private List<RoleTag> roleTags = new ArrayList<RoleTag>();

    protected Date createdAt;

    @PrePersist
    private void onCreate() {
        createdAt = new Date();
    }

    public void addRoleTag(RoleTag roleTag) {
        roleTags.add(roleTag);
    }
}
