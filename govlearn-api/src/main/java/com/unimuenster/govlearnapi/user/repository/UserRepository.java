package com.unimuenster.govlearnapi.user.repository;

import com.unimuenster.govlearnapi.user.entity.UserEntity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findByEmail(String email);

    @Query(value = """
        SELECT u FROM UserEntity u WHERE u.id = :userID
    """)
    Optional<UserEntity> findUserById(Long userID);
}
