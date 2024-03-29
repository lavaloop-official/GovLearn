package com.unimuenster.govlearnapi.group.repository;

import com.unimuenster.govlearnapi.group.entity.Group;
import com.unimuenster.govlearnapi.group.entity.Member;
import com.unimuenster.govlearnapi.user.entity.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {

    @Query(value = """
        SELECT g 
        FROM Group g 
        JOIN g.members m
        WHERE m.id = :id 
        AND m.role = 2
    """)
    List<Group> findByAdmin(Long id);

    @Query(value = """
        SELECT CASE WHEN COUNT(g) > 0 THEN true ELSE false END  
        FROM Group g 
        JOIN g.members m
        WHERE g.id = :groupId 
        AND m.user.id = :id 
        AND m.role = 2
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
        SELECT m 
        FROM Group g 
        JOIN g.members m 
        WHERE m.user.id = :userId AND g.id = :groupId
    """)
    Member getMember(Long userId, Long groupId);

    @Query(value = """
        SELECT m 
        FROM Member m
        WHERE m.id = :memberId
    """)
    Member getMemberByMemberId(Long memberId);

    @Query(value = """
        SELECT g 
        FROM Group g 
        JOIN g.members m
        WHERE m.user.id = :id
        AND m.role = 2
    """)
    List<Group> getGroupsByAdmin(Long id);

    @Query(value = """
        SELECT g 
        FROM Group g 
        JOIN g.members m 
        WHERE m.user.id = :id
    """)
    List<Group> getGroupsByMember(Long id);

    @Query(value = """
        SELECT CASE WHEN COUNT(g) > 0 THEN true ELSE false END  
        FROM Group g 
        JOIN g.members m
        WHERE g.id = :groupId AND m.user.id = :memberId
    """)
    boolean existsByIdAndMember(Long groupId, Long memberId);

    @Query(value = """
        SELECT m
        FROM Group g 
        JOIN g.members m
        WHERE g.id = :groupId
    """)
    List<Member> getMembers(Long groupId);

    @Query(value = """
        SELECT m
        FROM Group g 
        JOIN g.members m
        WHERE g.id = :groupId
        AND m.role = 2
    """)
    List<Member> getAdmins(Long groupId);

    @Modifying
    @Query(value = """
        DELETE FROM invitation i
        WHERE i.group_id = :groupId ;
        DELETE FROM member m
        WHERE m.group_id = :groupId ;
        DELETE FROM group_table g
        WHERE g.id = :groupId ;
    """, nativeQuery = true)
    void deleteGroup(Long groupId);
}
