package com.unimuenster.govlearnapi.bookmark;

import com.unimuenster.govlearnapi.AbstractIntegrationTest;
import com.unimuenster.govlearnapi.bookmark.repository.BookmarkRepository;
import com.unimuenster.govlearnapi.bookmark.service.BookmarkService;
import com.unimuenster.govlearnapi.course.service.dto.CourseDTO;
import com.unimuenster.govlearnapi.initializer.InitializerService;
import com.unimuenster.govlearnapi.user.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
public class BookmarkServiceTest extends AbstractIntegrationTest {
    @Autowired
    private BookmarkService bookmarkService;
    @Autowired
    private BookmarkRepository bookmarkRepository;
    @Autowired
    private InitializerService initializer;

    @Test
    void getBookmarksByUser() {
        UserEntity currentUser = initializer.getUser2();
        List<CourseDTO> bookmarks = bookmarkService.getBookmarks(currentUser);

        UserEntity user = bookmarkRepository
                .findAll()
                .stream()
                .filter(bookmarkedBy -> bookmarkedBy.getCourse().getId() == initializer.getCourse2().getId())
                .toList()
                .get(0)
                .getBookmarkee();

        assertEquals(currentUser.getEmail(), user.getEmail());

        assertEquals(1, bookmarks.size());
    }

    @Test
    void addBookmark() {
        UserEntity currentUser = initializer.getUser1();
        bookmarkService.addBookmark(currentUser, initializer.getCourse10().getId());
        // check if course10 got added to bookmarks

        UserEntity user = bookmarkRepository
                .findAll()
                .stream()
                .filter(bookmarkedBy -> bookmarkedBy.getCourse().getId() == initializer.getCourse10().getId())
                .toList()
                .get(0)
                .getBookmarkee();

        assertEquals(currentUser.getEmail(), user.getEmail());
    }

    @Test
    void deleteBookmark() {
        UserEntity currentUser = initializer.getUser2();
        bookmarkService.deleteBookmark(currentUser, initializer.getCourse2().getId());

        int listLength = bookmarkRepository
                .findAll()
                .stream()
                .filter(bookmarkedBy -> bookmarkedBy.getCourse().getId() == initializer.getCourse2().getId())
                .toList()
                .size();

        assertEquals(0, listLength);
    }
}
