package com.unimuenster.govlearnapi.tags.service;

import com.unimuenster.govlearnapi.course.entity.Course;
import com.unimuenster.govlearnapi.course.repository.CourseRepository;
import com.unimuenster.govlearnapi.tags.controller.wsto.AddTagToCourseWsTo;
import com.unimuenster.govlearnapi.tags.entity.CourseTag;
import com.unimuenster.govlearnapi.tags.entity.Tag;
import com.unimuenster.govlearnapi.tags.entity.VectorTag;
import com.unimuenster.govlearnapi.tags.exception.NotFoundException;
import com.unimuenster.govlearnapi.tags.repository.CourseTagRepository;
import com.unimuenster.govlearnapi.tags.repository.TagRepository;
import com.unimuenster.govlearnapi.tags.service.dto.TagDTO;
import com.unimuenster.govlearnapi.tags.service.dto.TagRatingVector;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseTagService {

    private final CourseTagRepository courseTagRepository;
    private  final CourseRepository courseRepository;
    private final TagRepository tagRepository;

    public List<CourseTag> getCourseTags(Course course) {
        return courseTagRepository.getCourseTagsByCourseId(course.getId());
    }

    @Transactional
    public void addTagToCourse(AddTagToCourseWsTo addTagToCourseWsTo) {

        Optional<Tag> tag = tagRepository.findById(addTagToCourseWsTo.tagId());
        Optional<Course> course = courseRepository.findById(addTagToCourseWsTo.courseId());
        if ( tag.isEmpty() || course.isEmpty() ){
            throw new NotFoundException();
        }

        CourseTag courseTag = new CourseTag();
        courseTag.setCourse(course.get());
        courseTag.setTag(tag.get());
        courseTag.setRating(addTagToCourseWsTo.rating());
        courseTag.setCreatedAt(new Date());

        courseTagRepository.save(courseTag);
    }

    public TagRatingVector computeCourseTagVector(List<CourseTag> courseTags, List<TagDTO> allTags){

        TagRatingVector courseTagVector = new TagRatingVector(allTags.size());
        courseTagVector.computeUserTagVector(
                courseTags.stream().map(tag -> (VectorTag) tag).collect(Collectors.toList()),
                allTags
        );

        return courseTagVector;
    }

    public TagRatingVector getCourseTagBinaryVector(Course course, List<TagDTO> allTags){

        List<CourseTag> courseTags = getCourseTags(course);

        return computeCourseTagVector(courseTags, allTags);
    }
}
