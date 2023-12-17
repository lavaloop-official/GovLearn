package com.unimuenster.govlearnapi.group.repository;

import com.unimuenster.govlearnapi.group.entity.Member;
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
            JOIN group_table_members mg ON m.id = mg.members_id 
            WHERE mg.group_id = :groupId
        )
    """, nativeQuery = true)
    int deleteCourseForAllMembers(Long groupId, Long courseId);
}
