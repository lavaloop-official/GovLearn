package com.unimuenster.govlearnapi.bookmark;

import com.unimuenster.govlearnapi.AbstractIntegrationTest;
import com.unimuenster.govlearnapi.bookmark.repository.BookmarkRepository;
import com.unimuenster.govlearnapi.bookmark.service.BookmarkService;
import com.unimuenster.govlearnapi.course.service.dto.CourseDTO;
import com.unimuenster.govlearnapi.initializer.InitializerService;
import com.unimuenster.govlearnapi.user.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@Transactional
public class BookmarkServiceTest extends AbstractIntegrationTest {
    @Autowired
    private BookmarkService bookmarkService;
    @Autowired
    private InitializerService initializer;



    @Test
    @Rollback
    void getBookmarksByUser() {
        UserEntity currentUser = initializer.getUser2();
        bookmarkService.addBookmark(currentUser, initializer.getCourse2().getId());
        List<CourseDTO> bookmarks = bookmarkService.getBookmarks(currentUser);

        assertEquals(bookmarks.size(),1);
    }

    @Test
    @Rollback
    void addBookmark() {
        UserEntity currentUser = initializer.getUser1();
        bookmarkService.addBookmark(currentUser, initializer.getCourse10().getId());
        // check if course10 got added to bookmarks
        assertTrue(currentUser.getBookmarked().stream().anyMatch(course -> Objects.equals(course.getId(), initializer.getCourse10().getId())));
    }

    @Test
    @Rollback
    void deleteBookmark() {
        UserEntity currentUser = initializer.getUser1();
        bookmarkService.addBookmark(currentUser, initializer.getCourse1().getId());
        bookmarkService.deleteBookmark(currentUser, initializer.getCourse1().getId());
        // check if course1 got deleted from bookmarks
        assertTrue(currentUser.getBookmarked().stream().noneMatch(course -> course.getId() == initializer.getCourse1().getId()));
    }
}
