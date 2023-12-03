package com.unimuenster.govlearnapi.bookmark.service;

import com.unimuenster.govlearnapi.bookmark.repository.BookmarkRepository;
import com.unimuenster.govlearnapi.course.entity.Course;
import com.unimuenster.govlearnapi.course.exception.NotFoundException;
import com.unimuenster.govlearnapi.course.repository.CourseRepository;
import com.unimuenster.govlearnapi.course.service.dto.CourseDTO;
import com.unimuenster.govlearnapi.course.service.mapper.ServiceCourseMapper;
import com.unimuenster.govlearnapi.user.entity.UserEntity;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final ServiceCourseMapper serviceCourseMapper;
    private final CourseRepository courseRepository;

    public List<CourseDTO> getBookmarks(UserEntity currentUser) {
        List<Course> bookmarks = bookmarkRepository.getBookmarksByUser(currentUser.getId());
        return bookmarks.stream().map(bookmark -> serviceCourseMapper.map(bookmark)).toList();
    }

    @Transactional
    public void addBookmark(UserEntity currentUser, Long courseId) {
        Optional<Course> course = courseRepository.findById(courseId);
        if (course.isEmpty()) { //catch if course does not exist
            throw new NotFoundException();
        }
        if (course.get().getBookmarkedBy().contains(currentUser)) { //catch if course is already bookmarked
            throw new IllegalArgumentException();
        }
        currentUser.addBookmark(course.get());
        bookmarkRepository.save(course.get());
    }
}
