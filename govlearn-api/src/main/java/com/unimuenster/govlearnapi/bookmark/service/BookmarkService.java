package com.unimuenster.govlearnapi.bookmark.service;

import com.unimuenster.govlearnapi.bookmark.repository.BookmarkRepository;
import com.unimuenster.govlearnapi.course.entity.Course;
import com.unimuenster.govlearnapi.course.service.dto.CourseDTO;
import com.unimuenster.govlearnapi.course.service.mapper.ServiceCourseMapper;
import com.unimuenster.govlearnapi.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final ServiceCourseMapper serviceCourseMapper;

    public List<CourseDTO> getBookmarks(UserEntity currentUser) {
        List<Course> bookmarks = bookmarkRepository.getBookmarksByUser(currentUser.getId());
        return bookmarks.stream().map(bookmark -> serviceCourseMapper.map(bookmark)).toList();
    }
}
