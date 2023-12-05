package com.unimuenster.govlearnapi.course.service;

import com.unimuenster.govlearnapi.category.entity.Category;
import com.unimuenster.govlearnapi.category.repository.CategoryRepository;
import com.unimuenster.govlearnapi.course.entity.Course;
import com.unimuenster.govlearnapi.course.repository.CourseRepository;
import com.unimuenster.govlearnapi.course.service.dto.CourseDTO;
import com.unimuenster.govlearnapi.course.service.mapper.ServiceCourseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseFilteringService {
    private final CourseRepository courseRepository;
    private final ServiceCourseMapper serviceCourseMapper;

    public List<CourseDTO> filterCourses(Integer limit, Integer offset, Optional<String> nameSearch, List<Long> tagIDs) {

        String search = "%%";
        if(nameSearch.isPresent())
        {
            search = "%"+nameSearch.get()+"%";
        }
        if(tagIDs.isEmpty())
        {
            List<Course> allCourses = courseRepository.findCoursesByAttributes(limit, offset, search);
            return allCourses.stream().map(course -> serviceCourseMapper.map(course)).collect(Collectors.toList());
        }
        else
        {
            List<Course> allCourses = courseRepository.findCoursesByAttributesAndTags(limit, offset, search, tagIDs);
            return allCourses.stream().map(course -> serviceCourseMapper.map(course)).collect(Collectors.toList());
        }
    }
}
