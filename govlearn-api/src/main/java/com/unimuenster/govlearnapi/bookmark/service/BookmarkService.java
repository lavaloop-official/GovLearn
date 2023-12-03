package com.unimuenster.govlearnapi.bookmark.service;

import com.unimuenster.govlearnapi.bookmark.repository.BookmarkRepository;
import com.unimuenster.govlearnapi.course.entity.Course;
import com.unimuenster.govlearnapi.course.exception.NotFoundException;
import com.unimuenster.govlearnapi.course.repository.CourseRepository;
import com.unimuenster.govlearnapi.course.service.dto.CourseDTO;
import com.unimuenster.govlearnapi.course.service.mapper.ServiceCourseMapper;
import com.unimuenster.govlearnapi.user.entity.UserEntity;
import com.unimuenster.govlearnapi.user.repository.UserRepository;
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
    private final UserRepository userRepository;

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
        currentUser.getBookmarked().add(course.get());
        // Get Reference to the course in the list of bookmarks
        Course courseInList = currentUser.getBookmarked().stream().filter(course1 -> course1.getId() == course.get().getId()).findFirst().get();
        courseInList.getBookmarkedBy().add(currentUser);

        // Save changes to both entities
        userRepository.save(currentUser);
        courseRepository.save(course.get());
    }

    @Transactional
    public void deleteBookmark(UserEntity currentUser, Long courseId) {
        Optional<Course> course = courseRepository.findById(courseId);
        if (course.isEmpty()) { //catch if course does not exist
            throw new NotFoundException();
        }
        if (currentUser.getBookmarked().stream().noneMatch(course1 -> course1.getId() == course.get().getId())) { //catch if course is not bookmarked
            throw new IllegalArgumentException();
        }
        // Get Reference to the course in the list of bookmarks
        Course courseInList = currentUser.getBookmarked().stream().filter(course1 -> course1.getId() == course.get().getId()).findFirst().get();

        currentUser.getBookmarked().remove(courseInList);
        courseInList.getBookmarkedBy().remove(currentUser);

        // Save changes to both entities
        userRepository.save(currentUser);
        courseRepository.save(course.get());
    }
}
