package com.cascinacaccia.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.cascinacaccia.entities.PasswordResetToken;
import com.cascinacaccia.entities.User;
import com.cascinacaccia.repos.TokenDAO;

import jakarta.transaction.Transactional;

@Service
/*
 * ForgotPasswordService handles the operations related to password reset functionality.
 * It is responsible for:
 * - Generating a password reset token and sending an email to the user with the reset link.
 * - Checking if a reset token has expired.
 * - Retrieving a reset token by its value.
 * - Cleaning up expired reset tokens periodically.
 */
public class ForgotPasswordService {
	
	@Autowired
	private TokenDAO tokenDAO;
	@Autowired
	private JavaMailSender javaMailSender;
	
	//method to generate the password reset link and send the email
    public String sendResetEmail(User user) {
        try {
            String resetLink = generateResetToken(user);
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom("innovive2024@gmail.com");
            msg.setTo(user.getEmail());
            msg.setSubject("Forgotten Password");
            msg.setText("Hello, \n\nPlease click on this link to reset your password: " 
                    + resetLink + "\n\nRegards, \nCascina Caccia");
            javaMailSender.send(msg);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    //method to generate reset token and create a reset link
	private String generateResetToken(User user) {
    	
        UUID uuid = UUID.randomUUID();
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime expiryDateTime = currentDateTime.plusMinutes(15);
        
        Optional<PasswordResetToken> oldToken = tokenDAO.findByUser(user);
        if (oldToken.isPresent()) {
        	deleteAndFlushPasswordResetToken(oldToken.get());
        }
        
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setUser(user);
        resetToken.setToken(uuid.toString());
        resetToken.setExpiryDateTime(expiryDateTime);

        //save the token in the database
        tokenDAO.save(resetToken);

        //generate the reset link
        String endpointUrl = "http://localhost:8080/resetPassword/";
        return endpointUrl + resetToken.getToken();
    }
	
	//method to delete the old password reset token
	@Transactional
	public void deleteAndFlushPasswordResetToken(PasswordResetToken token) {
		tokenDAO.delete(token); 
	}
    //method to check if a token has expired
    public boolean hasExpired(LocalDateTime expiryDateTime) {
        return expiryDateTime.isBefore(LocalDateTime.now());
    }

    //method to find a reset token by its value
    public PasswordResetToken findResetTokenByToken(String token) {
        return tokenDAO.findByToken(token);
    }
	
    //method to clean expired tokens every 1 second
    @Scheduled(fixedRate = 1000)
    public void cleanExpiredTokens() {
        LocalDateTime now = LocalDateTime.now();
        
        //fetch tokens that have expired
        List<PasswordResetToken> expiredTokens = tokenDAO.findByExpiryDateTimeBefore(now);
        
        //if there are expired tokens, delete them immediately
        if (!expiredTokens.isEmpty()) {
            tokenDAO.deleteAll(expiredTokens);
            System.out.println("Expired tokens cleaned up: " + expiredTokens.size());
        }
    }
}
