package com.unimuenster.govlearnapi.tags.repository;

import com.unimuenster.govlearnapi.tags.entity.UserTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserTagRepository extends JpaRepository<UserTag, Integer> {

    @Query("""
        select ut From UserTag ut
        Where ut.user.id = :userId
        """)
    List<UserTag> getUserTagByUserId(@Param("userId") Long userId);
}
