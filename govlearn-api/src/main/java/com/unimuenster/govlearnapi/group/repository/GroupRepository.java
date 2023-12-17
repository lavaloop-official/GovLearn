package com.unimuenster.govlearnapi.group.repository;

import com.unimuenster.govlearnapi.group.entity.Group;
import com.unimuenster.govlearnapi.group.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {

    @Query(value = """
        SELECT g FROM Group g WHERE g.admin.id = :id
    """)
    List<Group> findByAdmin(Long id);

    @Query(value = """
        SELECT CASE WHEN COUNT(g) > 0 THEN true ELSE false END  
        FROM Group g WHERE g.id = :groupId AND g.admin.id = :id
    """)
    boolean existsByIdAndAdmin(Long groupId, Long id);

    @Query(value = """
        SELECT g 
        FROM Group g 
        JOIN g.members m 
        WHERE m.id = :memberId
    """)
    Group findByMemberId(Long memberId);

    @Query(value = """
        SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END  
        FROM Group g 
        JOIN g.members m 
        WHERE m.id = :id AND g.id = :groupId
    """)
    boolean isMember(Long id, Long groupId);

    @Query(value = """
        SELECT m 
        FROM Group g 
        JOIN g.members m 
        WHERE m.user.id = :userId AND g.id = :groupId
    """)
    Member getMember(Long userId, Long groupId);

    @Query(value = """
        SELECT g 
        FROM Group g 
        WHERE g.admin.id = :id
    """)
    List<Group> getGroupsByAdmin(Long id);

    @Query(value = """
        SELECT g 
        FROM Group g 
        JOIN g.members m 
        WHERE m.user.id = :id
    """)
    List<Group> getGroupsByMember(Long id);
}
