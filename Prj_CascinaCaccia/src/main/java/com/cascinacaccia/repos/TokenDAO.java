package com.cascinacaccia.repos;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cascinacaccia.entities.PasswordResetToken;
import com.cascinacaccia.entities.User;

/*
 * The TokenDAO interface extends JpaRepository to provide standard CRUD operations for the PasswordResetToken entity.
 * It includes methods for saving, deleting, and querying password reset tokens.
 */
public interface TokenDAO extends JpaRepository<PasswordResetToken, Integer> {
	
	Optional<PasswordResetToken> findByUser(User user);  // Find token by user
    PasswordResetToken findByToken(String token);        // Find token by token value
    void delete(PasswordResetToken token);               // Delete token
    List<PasswordResetToken> findByExpiryDateTimeBefore(LocalDateTime expiryDateTime);
}
