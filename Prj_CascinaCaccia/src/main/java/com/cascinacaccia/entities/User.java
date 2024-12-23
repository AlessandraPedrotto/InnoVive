package com.cascinacaccia.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="user")
public class User {
	
	//characteristics of the entity user
	@Id
    private String id;  
    private String name;
    private String surname;
    private String email;
    private String password;
    
    //relationship between user and password reset
    @OneToOne(mappedBy="user", fetch=FetchType.EAGER)
    private PasswordResetToken passwordResetToken;
    
    //relationship between user and user image
    @ManyToOne
    @JoinColumn(name = "userImg_id")
    private UserImage userImage;
    
    //relationship between user and role
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    
    //a user can have multiple roles
    private List<Role> roles;
    
    //getters and setters
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public UserImage getUserImage() {
		return userImage;
	}
	public void setUserImage(UserImage userImage) {
		this.userImage = userImage;
	}
	public List<Role> getRoles() {
        return roles;
    }
    public void setRoles(List<Role> roles) {
        if (roles.size() < 1 || roles.size() > 2) {
            throw new IllegalArgumentException("User must have at least 1 role and a maximum of 2 roles.");
        }
        this.roles = roles;
    }
	public PasswordResetToken getPasswordResetToken() {
		return passwordResetToken;
	}
	public void setPasswordResetToken(PasswordResetToken passwordResetToken) {
		this.passwordResetToken = passwordResetToken;
	}
}
