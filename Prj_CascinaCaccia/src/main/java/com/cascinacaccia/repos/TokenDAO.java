package com.cascinacaccia.repos;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cascinacaccia.entities.PasswordResetToken;
import com.cascinacaccia.entities.User;

//the TokenDAO interface provides standard methods for CRUD operations (e.g., save, delete)
public interface TokenDAO extends JpaRepository<PasswordResetToken, Integer> {
	
	Optional<PasswordResetToken> findByUser(User user);
	PasswordResetToken findByToken(String token);
	void deleteById(Integer id);
	
	List<PasswordResetToken> findByExpiryDateTimeBefore(LocalDateTime expiryDateTime);
}
