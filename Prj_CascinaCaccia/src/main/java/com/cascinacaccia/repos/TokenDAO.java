package com.cascinacaccia.repos;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cascinacaccia.entities.PasswordResetToken;

public interface TokenDAO extends JpaRepository<PasswordResetToken, Integer> {
	PasswordResetToken findByToken(String token);
	 List<PasswordResetToken> findByExpiryDateTimeBefore(LocalDateTime expiryDateTime);
}
