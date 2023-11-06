package com.unimuenster.govlearnapi.tags.service;

import com.unimuenster.govlearnapi.course.entity.Course;
import com.unimuenster.govlearnapi.tags.entity.CourseTag;
import com.unimuenster.govlearnapi.tags.repository.CourseTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseTagService {

    private final CourseTagRepository courseTagRepository;

    public List<CourseTag> getCourseTags(Course course) {
        return courseTagRepository.getCourseTagsByCourseId(course.getId());
    }
}
