package com.unimuenster.govlearnapi.group.repository;

import com.unimuenster.govlearnapi.group.entity.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {

    @Query(value = """
        SELECT i FROM Invitation i WHERE i.user.id = :id
    """)
    List<Invitation> getAllInvitationsByUser(Long id);

    @Modifying
    @Query(value = """
            DELETE 
            FROM Invitation i 
            WHERE i.group.id = :groupID
            AND i.user.id = :userID
        """)
    void deleteInvitation(long groupID, long userID);
}
