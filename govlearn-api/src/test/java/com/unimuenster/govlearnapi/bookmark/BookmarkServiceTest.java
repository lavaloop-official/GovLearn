package com.unimuenster.govlearnapi.bookmark;

import com.unimuenster.govlearnapi.AbstractIntegrationTest;
import com.unimuenster.govlearnapi.bookmark.service.BookmarkService;
import com.unimuenster.govlearnapi.initializer.InitializerService;
import com.unimuenster.govlearnapi.user.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookmarkServiceTest extends AbstractIntegrationTest {
    @Autowired
    private BookmarkService bookmarkService;
    @Autowired
    private InitializerService initializer;

    @Test
    void getBookmarksByUser() {
        UserEntity currentUser = initializer.getUser1();
        assertEquals(currentUser.getBookmarked().size(),1);
    }

    @Test
    void addBookmark() {
        UserEntity currentUser = initializer.getUser1();
        bookmarkService.addBookmark(currentUser, initializer.getCourse10().getId());
        // check if course10 got added to bookmarks
        assertTrue(currentUser.getBookmarked().stream().anyMatch(course -> Objects.equals(course.getId(), initializer.getCourse10().getId())));
    }

    @Test
    void deleteBookmark() {
        UserEntity currentUser = initializer.getUser1();
        bookmarkService.deleteBookmark(currentUser, initializer.getCourse1().getId());
        // check if course1 got deleted from bookmarks
        assertTrue(currentUser.getBookmarked().stream().noneMatch(course -> course.getId() == initializer.getCourse1().getId()));
    }
}
