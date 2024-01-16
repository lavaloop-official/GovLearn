package com.unimuenster.govlearnapi.tags.service;

import com.unimuenster.govlearnapi.tags.entity.CourseTag;
import com.unimuenster.govlearnapi.tags.entity.Tag;
import com.unimuenster.govlearnapi.tags.entity.UserTag;
import com.unimuenster.govlearnapi.tags.exception.NotFoundException;
import com.unimuenster.govlearnapi.tags.repository.CourseTagRepository;
import com.unimuenster.govlearnapi.tags.repository.TagRepository;
import com.unimuenster.govlearnapi.tags.repository.UserTagRepository;
import com.unimuenster.govlearnapi.tags.service.dto.TagDTO;
import com.unimuenster.govlearnapi.tags.service.mapper.ServiceTagMapper;
import com.unimuenster.govlearnapi.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserTagService {

    private final UserTagRepository userTagRepository;
    private final TagRepository tagRepository;
    private final ServiceTagMapper serviceTagMapper;
    private final CourseTagRepository courseTagRepository;
    public List<UserTag> getUserTags(UserEntity user) {
        return userTagRepository.getUserTagByUserId(user.getId());
    }

    @Transactional
    public void addTagToUser(UserEntity currentUser, long tagId, int rating) {

        Optional<Tag> byId = tagRepository.findById(tagId);

        if ( byId.isEmpty() ){
            throw new NotFoundException();
        }

        UserTag userTag = new UserTag();
        userTag.setUser(currentUser);
        userTag.setRating(rating);
        userTag.setTag(byId.get());

        userTagRepository.save(userTag);
    }

    public double[] getUserTagRatingVector(List<UserTag> tags, List<TagDTO> allTags){
        double[] userTagRatingVector = new double[allTags.size()];

        for (int i = 0; i < allTags.size(); i++) {
            TagDTO currentTag = allTags.get(i);

            Optional<UserTag> userTag = findCurrentTagInUserTags(currentTag, tags);

            userTagRatingVector[i] = getUserTagRating(userTag);
        }

        return userTagRatingVector;
    }

    private int getUserTagRating(Optional<UserTag> foundUserTag){
        if ( foundUserTag.isPresent() ) {
            return foundUserTag.get().getRating();
        }

        return  0;
    }

    private Optional<UserTag> findCurrentTagInUserTags(TagDTO currentTag, List<UserTag> tags){
        return tags
                .stream()
                .filter(userTag ->
                        userTag.getTag().getName().equals(currentTag.name())
                )
                .findFirst();
    }

    public void adjustUserTags(UserEntity user, Long courseId){
        List<CourseTag> courseTags = courseTagRepository.getCourseTagsByCourseId(courseId);

        for ( CourseTag courseTag : courseTags ){
            updateUserTag(courseTag.getTag().getId(), user, courseTag.getRating());
        }
    }

    @Transactional
    protected void updateUserTag(Long tagId, UserEntity user, int improvement) {
        Optional<Tag> byId = tagRepository.findById(tagId);

        if (byId.isEmpty()) {
            throw new NotFoundException();
        }

        List<UserTag> userTags = userTagRepository.getUserTagByUserId(user.getId());

        TagDTO tagDTO = serviceTagMapper.map(byId.get());
        Optional<UserTag> userTag = findCurrentTagInUserTags(tagDTO, userTags);

        if (userTag.isEmpty()) {
            addTagToUser(user, tagId, improvement);
        } else {
            userTag.get().setRating(userTag.get().getRating() + improvement);
            userTagRepository.save(userTag.get());
        }
    }

}
