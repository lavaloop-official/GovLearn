package com.unimuenster.govlearnapi.group.repository;

import com.unimuenster.govlearnapi.group.entity.Invitation;
import com.unimuenster.govlearnapi.group.entity.Member;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @Modifying
    @Query(value = """
        DELETE FROM member_courses m 
        WHERE m.courses_id = :courseId 
        AND m.member_id IN (
            SELECT m.id 
            FROM member m 
            WHERE m.group_id = :groupId
        )
    """, nativeQuery = true)
    int deleteCourseForAllMembers(Long groupId, Long courseId);

    @Modifying
    @Query(value = """
        DELETE FROM member_courses m 
        WHERE m.courses_id = :courseID 
        AND m.member_id = :memberID
    """, nativeQuery = true)
    void deleteCourseForMember(long courseID, long memberID);

    @Modifying
    @Query(value = """
        DELETE FROM member_courses mc
        WHERE mc.member_id = :memberID ;
        DELETE FROM member m
        WHERE m.id = :memberID ;
    """, nativeQuery = true)
    void removeMemberFromGroup(Long memberID);

    @Query(value = """
        SELECT m
        FROM Member m
        WHERE m.group.id = :groupID
        AND m.user.id = :userID
        """)
    Optional<Member> findByUserIDAndGroupID(Long userID, Long groupID);


    @Query(value = """
        SELECT i
        FROM Invitation i
        WHERE i.group.id = :groupID
        """)
    List<Invitation> getInvitedMembers(Long groupID);
}
