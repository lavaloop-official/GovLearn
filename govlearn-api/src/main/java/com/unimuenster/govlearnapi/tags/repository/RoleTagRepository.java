package com.unimuenster.govlearnapi.tags.repository;

import com.unimuenster.govlearnapi.tags.entity.RoleTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleTagRepository extends JpaRepository<RoleTag, Long> {

    @Query(value = """
        SELECT rt
        FROM RoleTag rt
        """)
    List<RoleTag> getAllRoleTags();

    @Query(value = """
        SELECT *
        FROM role_tag
        INNER JOIN role_role_tags ON role_tag.id = role_role_tags.role_tags_id
        WHERE role_role_tags.role_id = :roleID
        """, nativeQuery = true)
    List<RoleTag> getAllRoleTagsByRole(Long roleID);
}
