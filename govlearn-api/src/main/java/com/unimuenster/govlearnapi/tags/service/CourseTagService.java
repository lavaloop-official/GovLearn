package com.unimuenster.govlearnapi.tags.service;

import com.unimuenster.govlearnapi.course.entity.Course;
import com.unimuenster.govlearnapi.course.repository.CourseRepository;
import com.unimuenster.govlearnapi.tags.entity.CourseTag;
import com.unimuenster.govlearnapi.tags.entity.Tag;
import com.unimuenster.govlearnapi.tags.exception.NotFoundException;
import com.unimuenster.govlearnapi.tags.repository.CourseTagRepository;
import com.unimuenster.govlearnapi.tags.repository.TagRepository;
import com.unimuenster.govlearnapi.tags.service.dto.TagDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    public void addTagToCourse(long courseId, long tagId) {

        Optional<Tag> tag = tagRepository.findById(tagId);
        Optional<Course> course = courseRepository.findById(courseId);
        if ( tag.isEmpty() || course.isEmpty() ){
            throw new NotFoundException();
        }

        CourseTag courseTag = new CourseTag();
        courseTag.setCourse(course.get());
        courseTag.setTag(tag.get());
        courseTag.setCreatedAt(new Date());

        courseTagRepository.save(courseTag);
    }

    public double[] computeCourseTagVector(List<CourseTag> courseTags, List<TagDTO> allTags){
        double[] courseTagVector = new double[allTags.size()];

        for ( int i = 0; i < allTags.size(); i++ ){
            TagDTO currentTagDTO = allTags.get(i);

            boolean containsTag = isTagInCourseTags(courseTags, currentTagDTO);

            courseTagVector[i] = getTagValue(containsTag);
        }

        return courseTagVector;
    }

    public double[] getCourseTagBinaryVector(Course course, List<TagDTO> allTags){

        List<CourseTag> courseTags = getCourseTags(course);

        return computeCourseTagVector(courseTags, allTags);
    }

    public boolean isTagInCourseTags(List<CourseTag> courseTags, TagDTO currentTagDTO){
        return courseTags.stream().anyMatch(courseTag -> courseTag.getTag().getId() == currentTagDTO.id());
    }

    private int getTagValue (boolean containsTag){
        if ( containsTag ) {
            return 1;
        }else {
            return 0;
        }
    }
}
