package com.example.book_api.repository;

import com.example.book_api.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByContenu(String contenu);

    List<RefreshToken> findByUserId_AndRevokedFalse(Long userId);

    //void deleteByToken(Long tokenId);
}
