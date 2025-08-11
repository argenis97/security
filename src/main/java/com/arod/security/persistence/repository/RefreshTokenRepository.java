package com.arod.security.persistence.repository;

import com.arod.security.persistence.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    List<RefreshToken> findByUser_Id(Long userID);

    Optional<RefreshToken> findByToken(String token);
}
