package com.unimuenster.govlearnapi.tags.repository;

import com.unimuenster.govlearnapi.tags.entity.UserTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserTagRepository extends JpaRepository<UserTag, Integer> {

    @Query("""
        select ut From UserTag ut
        Where ut.user.id = :userId
        """)
    List<UserTag> getUserTagByUserId(@Param("userId") Long userId);

    @Query("""
        select ut From UserTag ut
        Where ut.user.id = :userId
        AND ut.tag.id = :tagId
        """)
    Optional<UserTag> findByUserAndTag(Long userId, Long tagId);
}
