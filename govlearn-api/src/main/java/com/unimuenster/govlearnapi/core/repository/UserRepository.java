package com.unimuenster.govlearnapi.core.repository;

import com.unimuenster.govlearnapi.core.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findByEmail(String email);
}
