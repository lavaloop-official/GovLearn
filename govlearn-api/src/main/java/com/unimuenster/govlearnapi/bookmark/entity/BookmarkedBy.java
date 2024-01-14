package com.unimuenster.govlearnapi.bookmark.entity;

import com.unimuenster.govlearnapi.course.entity.Course;
import com.unimuenster.govlearnapi.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookmarkedBy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @ManyToOne()
    @ToString.Exclude
    private UserEntity bookmarkee;

    @ManyToOne()
    @ToString.Exclude
    private Course course;
}
