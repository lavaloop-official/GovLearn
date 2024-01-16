package com.unimuenster.govlearnapi.tags.service;

import com.unimuenster.govlearnapi.course.entity.Course;
import com.unimuenster.govlearnapi.course.repository.CourseRepository;
import com.unimuenster.govlearnapi.tags.controller.wsto.AddTagToCourseWsTo;
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

    public double[] computeCourseTagVector(List<CourseTag> courseTags, List<TagDTO> allTags){
        double[] courseTagVector = new double[allTags.size()];

        for ( int i = 0; i < allTags.size(); i++ ){
            TagDTO currentTagDTO = allTags.get(i);

            Optional<CourseTag> courseTag = isTagInCourseTags(courseTags, currentTagDTO);

            courseTagVector[i] = getTagValue(courseTag);
        }

        return courseTagVector;
    }

    public double[] getCourseTagBinaryVector(Course course, List<TagDTO> allTags){

        List<CourseTag> courseTags = getCourseTags(course);

        return computeCourseTagVector(courseTags, allTags);
    }

    public Optional<CourseTag> isTagInCourseTags(List<CourseTag> courseTags, TagDTO currentTagDTO){
        Optional<CourseTag> optTag = Optional.empty();
        for ( CourseTag courseTag : courseTags ){
            if ( courseTag.getTag().getId().equals(currentTagDTO.id()) ){
                optTag = Optional.of(courseTag);
                break;
            }
        }

        return optTag;
    }

    private double getTagValue (Optional<CourseTag> courseTag){
        if ( courseTag.isEmpty() ) {
            return 0;
        }else {
            return courseTag.get().getRating();
        }
    }
}
