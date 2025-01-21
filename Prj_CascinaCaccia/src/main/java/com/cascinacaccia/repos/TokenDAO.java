package com.cascinacaccia.repos;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cascinacaccia.entities.PasswordResetToken;
import com.cascinacaccia.entities.User;

/*
 * The TokenDAO interface extends JpaRepository to provide standard CRUD operations for the PasswordResetToken entity.
 * It includes methods for saving, deleting, and querying password reset tokens with support for querying by:
 * - User
 * - Token
 * - Expiry date and time
 */
public interface TokenDAO extends JpaRepository<PasswordResetToken, Integer> {
	
	Optional<PasswordResetToken> findByUser(User user);
    PasswordResetToken findByToken(String token);
    void delete(PasswordResetToken token);
    List<PasswordResetToken> findByExpiryDateTimeBefore(LocalDateTime expiryDateTime);
}
