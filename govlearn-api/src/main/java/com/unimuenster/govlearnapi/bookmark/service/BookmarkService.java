package com.unimuenster.govlearnapi.bookmark.service;

import com.unimuenster.govlearnapi.bookmark.entity.BookmarkedBy;
import com.unimuenster.govlearnapi.bookmark.repository.BookmarkRepository;
import com.unimuenster.govlearnapi.course.entity.Course;
import com.unimuenster.govlearnapi.course.repository.CourseRepository;
import com.unimuenster.govlearnapi.course.service.CourseService;
import com.unimuenster.govlearnapi.course.service.dto.CourseDTO;
import com.unimuenster.govlearnapi.course.service.mapper.ServiceCourseMapper;
import com.unimuenster.govlearnapi.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final ServiceCourseMapper serviceCourseMapper;
    private final CourseService courseService;

    public List<CourseDTO> getBookmarks(UserEntity currentUser) {
        List<BookmarkedBy> bookmarks = bookmarkRepository.getBookmarksByUser(currentUser.getId());
        return bookmarks.stream().map(bookmark -> serviceCourseMapper.map(bookmark.getCourse())).toList();
    }

    @Transactional
    public void addBookmark(UserEntity currentUser, Long courseId) {
        CourseDTO courseById = courseService.getCourseById(courseId);

        isCourseBookmarked(courseById, currentUser);

        BookmarkedBy bookmarkedBy = new BookmarkedBy();
        bookmarkedBy.setBookmarkee(currentUser);
        bookmarkedBy.setCourse(Course.builder().id(courseById.id()).build());

        bookmarkRepository.save(bookmarkedBy);
    }


    private void isCourseBookmarked(CourseDTO course, UserEntity currentUser){
        if ( bookmarkRepository.isCourseBookmarked(course.id(), currentUser.getId()) ) {
            throw new IllegalArgumentException();
        }
    }

    @Transactional
    public void deleteBookmark(UserEntity currentUser, Long courseId) {
        bookmarkRepository.deleteBookmark(courseId, currentUser.getId());
    }
}
