package com.unimuenster.govlearnapi.tags.entity;

import com.unimuenster.govlearnapi.tags.service.dto.TagDTO;
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
public class UserTag extends VectorTag{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private int rating;

    protected Date createdAt;

    @PrePersist
    private void onCreate() {
        createdAt = new Date();
    }

    public boolean equals(TagDTO tagDTO){
        return this
            .getTag()
            .getName()
            .equals(tagDTO.name());
    }

    public boolean equals(Tag tag){
        return this
                .getTag()
                .getName()
                .equals(tag.getName());
    }
}
