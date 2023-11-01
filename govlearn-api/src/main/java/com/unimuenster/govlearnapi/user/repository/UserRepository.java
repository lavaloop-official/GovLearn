package com.unimuenster.govlearnapi.user.repository;

import com.unimuenster.govlearnapi.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findByEmail(String email);

    List<UserEntity> findUsersByTagsId(Long tagId);
}
