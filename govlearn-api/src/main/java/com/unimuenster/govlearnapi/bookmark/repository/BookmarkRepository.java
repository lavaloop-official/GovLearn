package com.unimuenster.govlearnapi.bookmark.repository;

import com.unimuenster.govlearnapi.bookmark.entity.BookmarkedBy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<BookmarkedBy, Long> {

    @Query(value = """
        SELECT bookmarkedBy from BookmarkedBy bookmarkedBy
        WHERE bookmarkedBy.bookmarkee.id = :currentUserId      
    """)
    List<BookmarkedBy> getBookmarksByUser(Long currentUserId);

    @Modifying
    @Query(value = """
        DELETE from BookmarkedBy bookmarkedBy
        WHERE bookmarkedBy.course.id = :courseId 
        AND bookmarkedBy.bookmarkee.id = :currentUserId
    """)
    void deleteBookmark(Long courseId, Long currentUserId);
}
