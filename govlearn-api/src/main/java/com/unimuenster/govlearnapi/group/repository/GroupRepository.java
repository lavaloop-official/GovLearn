package com.unimuenster.govlearnapi.group.repository;

import com.unimuenster.govlearnapi.group.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
    @Query("SELECT g " +
            "FROM Group g " +
            "INNER JOIN g.members m " +
            "WHERE :memberId = m.id")
    List<Group> findAllByMember(@Param("memberId") Long userId);
}
