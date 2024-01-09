package com.unimuenster.govlearnapi.tags.service;

import com.unimuenster.govlearnapi.tags.controller.wsto.AddTagToUserWsTo;
import com.unimuenster.govlearnapi.tags.entity.Tag;
import com.unimuenster.govlearnapi.tags.entity.UserTag;
import com.unimuenster.govlearnapi.tags.exception.NotFoundException;
import com.unimuenster.govlearnapi.tags.repository.TagRepository;
import com.unimuenster.govlearnapi.tags.repository.UserTagRepository;
import com.unimuenster.govlearnapi.tags.service.dto.TagDTO;
import com.unimuenster.govlearnapi.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserTagService {

    private final UserTagRepository userTagRepository;
    private final TagRepository tagRepository;

    public List<UserTag> getUserTags(UserEntity user) {
        return userTagRepository.getUserTagByUserId(user.getId());
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
}
