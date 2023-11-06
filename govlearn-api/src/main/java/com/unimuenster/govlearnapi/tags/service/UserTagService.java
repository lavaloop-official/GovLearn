package com.unimuenster.govlearnapi.tags.service;

import com.unimuenster.govlearnapi.tags.entity.UserTag;
import com.unimuenster.govlearnapi.tags.repository.UserTagRepository;
import com.unimuenster.govlearnapi.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserTagService {

    private final UserTagRepository userTagRepository;

    public List<UserTag> getUserTags(UserEntity user) {
        return userTagRepository.getUserTagByUserId(user.getId());
    }

}
