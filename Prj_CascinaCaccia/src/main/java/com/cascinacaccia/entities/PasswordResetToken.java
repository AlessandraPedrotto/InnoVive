package com.cascinacaccia.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/*
 * The PasswordResetToken entity represents the tokens generated when a user
 * requests to reset their password after forgetting it. Each token is associated 
 * with a specific user and has an expiration date and time.
 */
@Entity
@Table(name = "password_reset")
public class PasswordResetToken {
	
	//characteristics of the entity PasswordResetToken
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String token;
	private LocalDateTime expiryDateTime;
	
	//relationship between PasswordResetToken and User
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", referencedColumnName = "id", nullable=false)
	private User user;
	
	//getters and setters
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public LocalDateTime getExpiryDateTime() {
		return expiryDateTime;
	}
	
	public void setExpiryDateTime(LocalDateTime expiryDateTime) {
		this.expiryDateTime = expiryDateTime;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
}
