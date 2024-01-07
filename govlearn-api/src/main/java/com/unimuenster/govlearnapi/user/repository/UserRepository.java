package com.unimuenster.govlearnapi.user.repository;

import com.unimuenster.govlearnapi.user.entity.UserEntity;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findByEmail(String email);

    @Query(value = """
        SELECT u FROM UserEntity u WHERE u.id = :userID
    """)
    Optional<UserEntity> findUserById(Long userID);

    @Query(value = """
        SELECT u FROM UserEntity u WHERE u.email IN :email
    """)
    List<UserEntity> findAllUsersByEmail(List<String> email);

    @Query(value = """
        SELECT u 
        FROM UserEntity u 
        WHERE u.id NOT IN (SELECT m.user.id FROM Member m WHERE m.group.id = :groupID)
        AND u.id NOT IN (SELECT i.user.id FROM Invitation i WHERE i.group.id = :groupID)
    """)
    List<UserEntity> findAllUserWithoutGroup(Long groupID);
}
