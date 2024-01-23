package com.unimuenster.govlearnapi.bookmark.entity;

import com.unimuenster.govlearnapi.course.entity.Course;
import com.unimuenster.govlearnapi.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = { "bookmarkee_id", "course_id" })
})
public class BookmarkedBy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @ManyToOne(optional = false)
    @ToString.Exclude
    private UserEntity bookmarkee;

    @ManyToOne(optional = false)
    @ToString.Exclude
    private Course course;
}
