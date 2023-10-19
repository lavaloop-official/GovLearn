package com.unimuenster.govlearnapi.core.repository;


import com.unimuenster.govlearnapi.core.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    @Query(value = """
      select t from Token t
      inner join UserEntity u
      on t.user.id = u.id
      where u.id = :adminId and (t.expired = false or t.revoked = false)
      """)
    List<Token> findAllValidTokenByUser(@Param("adminId") Integer id);

    @Modifying
    @Query(value = """
      Delete from Token t
      where t.user.id = :adminId and (t.expired = true or t.revoked = true)
      """)
    void deleteAllValidTokenByUser(@Param("adminId") Integer id);

    Optional<Token> findByToken(String token);
}
