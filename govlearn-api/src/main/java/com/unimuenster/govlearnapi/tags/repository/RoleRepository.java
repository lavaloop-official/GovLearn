package com.unimuenster.govlearnapi.tags.repository;

import com.unimuenster.govlearnapi.tags.entity.Role;
import com.unimuenster.govlearnapi.tags.entity.RoleTag;
import com.unimuenster.govlearnapi.tags.entity.Tag;
import com.unimuenster.govlearnapi.tags.entity.UserTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoleRepository  extends JpaRepository<Role, Long> {

    @Query(value = """
        SELECT r
        FROM Role r
      """)
    List<Role> getAllRoles();

    // @Query("""
    //     SELECT rt
    //     FROM Role r
    //     INNER JOIN RoleTag rt ON rt.id == r.roleTags.id
    //     WHERE r.id = :roleID
    //     """)
    // List<RoleTag> getAllRoleTagsByRoleID(Long roleID);
}
