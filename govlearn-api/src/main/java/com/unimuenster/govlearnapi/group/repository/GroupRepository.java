package com.unimuenster.govlearnapi.group.repository;

import com.unimuenster.govlearnapi.group.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Integer> {

    @Query(value = """
        SELECT g FROM Group g WHERE g.admin.id = :id
    """)
    List<Group> findByAdmin(Long id);
}
