package com.unimuenster.govlearnapi.tags.service;

import com.unimuenster.govlearnapi.tags.entity.CourseTag;
import com.unimuenster.govlearnapi.tags.controller.wsto.AddTagToUserWsTo;
import com.unimuenster.govlearnapi.tags.entity.Tag;
import com.unimuenster.govlearnapi.tags.entity.UserTag;
import com.unimuenster.govlearnapi.tags.entity.VectorTag;
import com.unimuenster.govlearnapi.tags.repository.CourseTagRepository;
import com.unimuenster.govlearnapi.tags.repository.TagRepository;
import com.unimuenster.govlearnapi.tags.repository.UserTagRepository;
import com.unimuenster.govlearnapi.tags.service.dto.TagDTO;
import com.unimuenster.govlearnapi.tags.service.dto.TagRatingVector;
import com.unimuenster.govlearnapi.tags.service.mapper.ServiceTagMapper;
import com.unimuenster.govlearnapi.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public void addTagToUser(UserEntity currentUser, Tag tag, int rating) {

        UserTag userTag = new UserTag();
        userTag.setUser(currentUser);
        userTag.setRating(rating);
        userTag.setTag(tag);

        userTagRepository.save(userTag);
    }

    @Transactional
    public void addTagsToUser(UserEntity currentUser, List<AddTagToUserWsTo> addTagToUserWsTo) {

        List<Tag> existingTags = tagRepository.findAllById(addTagToUserWsTo.stream().map(addTag -> addTag.tagId()).collect(Collectors.toList()));

        List<Long> userTagIDs = userTagRepository.getUserTagByUserId(currentUser.getId()).stream().map(element -> element.getId()).collect(Collectors.toList());

        List<Tag> filteredTags = existingTags.stream().filter(e -> !userTagIDs.contains(e.getId())).collect(Collectors.toList());

        filteredTags.stream().forEach(tag -> {
            UserTag userTag = new UserTag();
            userTag.setUser(currentUser);
            userTag.setRating(addTagToUserWsTo.stream().filter(element -> element.tagId() == tag.getId()).findFirst().get().rating());
            userTag.setTag(tag);
            userTagRepository.save(userTag);
        });
    }

    public TagRatingVector computeUserTagVector(List<UserTag> userTags, List<TagDTO> allTags){
        TagRatingVector courseTagVector = new TagRatingVector(allTags.size());
        courseTagVector.computeUserTagVector(
                userTags.stream().map(tag -> (VectorTag) tag).collect(Collectors.toList()),
                allTags
        );

        return courseTagVector;
    }

    public void adjustUserTags(UserEntity user, Long courseId, boolean isAdd){
        List<CourseTag> courseTags = courseTagRepository.getCourseTagsByCourseId(courseId);

        for ( CourseTag courseTag : courseTags ){
            if (isAdd) {
                updateUserTag(courseTag.getTag(), user, courseTag.getRating());
            } else{
                updateUserTag(courseTag.getTag(), user, -1 * courseTag.getRating());
            }
        }

    }

    @Transactional
    protected void updateUserTag(Tag tag, UserEntity user, int ratingChange) {

        Optional<UserTag> optionalUserTag = userTagRepository.findByUserAndTag(user.getId(), tag.getId());

        if (doesNotExist(optionalUserTag)) {
            addTagToUser(user, tag, ratingChange);

            return;
        }

        UserTag userTag = optionalUserTag.get();

        if ( isFutureRatingInvalid(ratingChange, userTag) ) {

            userTagRepository.delete(userTag);
        } else {

            userTag.setRating(userTag.getRating() + ratingChange);
            userTagRepository.save(userTag);
        }
    }

    private static boolean doesNotExist(Optional<UserTag> userTag) {
        return userTag.isEmpty();
    }

    private static boolean isFutureRatingInvalid(int ratingChange, UserTag userTag) {
        return userTag.getRating() + ratingChange <= 0;
    }

}
