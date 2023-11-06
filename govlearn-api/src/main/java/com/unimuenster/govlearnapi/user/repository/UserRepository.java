package com.unimuenster.govlearnapi.user.repository;

import com.unimuenster.govlearnapi.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findByEmail(String email);
}
