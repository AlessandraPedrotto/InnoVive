package com.cascinacaccia.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cascinacaccia.entities.PasswordResetToken;
import com.cascinacaccia.entities.Role;
import com.cascinacaccia.entities.User;
import com.cascinacaccia.entities.UserImage;
import com.cascinacaccia.repos.TokenDAO;
import com.cascinacaccia.repos.UserDAO;
import com.cascinacaccia.repos.UserImageDAO;

import jakarta.persistence.EntityNotFoundException;

@Service
/*the UserService class implements UserDetailsService, 
 *which is used for loading user-specific data during authentication in Spring Security
*/
public class UserService implements UserDetailsService{
	
	@Autowired
	UserDAO userDAO;
	@Autowired
	UserImageDAO userImageDAO;
	@Autowired
	TokenDAO tokenDAO;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	JavaMailSender javaMailSender;
	
	//method to register a new user
	public void register(User user) { 
		
        String id = UUID.randomUUID().toString();
        String name = user.getName();
        String email = user.getEmail();
        String password = passwordEncoder.encode(user.getPassword());
        user.setId(id);
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        
     // Fetch the default profile image from the database (assuming ID 1 for default image)
        UserImage defaultProfileImage = userImageDAO.findById(1L)
                .orElseThrow(() -> new RuntimeException("Default profile image not found"));
        
        // Set the default profile image for the new user
        user.setUserImage(defaultProfileImage);
        
        //create a list of roles for the user
        List<Role> roles = new ArrayList<>();
        //roles.add(new Role("2", "ROLE_ADMIN")); //uncomment this line to add an admin 
        roles.add(new Role("1", "ROLE_EMPLOYEE"));
        
        //save the user and the user roles in the database
        user.setRoles(roles);
        userDAO.save(user);
    }
	
	//method to assign roles to a user
	public void assignRoles(User user, List<Role> roles) {
        
		//check if the number of roles assigned is between 1 and 2
        if (roles.size() < 1 || roles.size() > 2) {
            throw new IllegalArgumentException("User must have at least 1 role and a maximum of 2 roles.");
        }
        
        user.setRoles(roles);
        userDAO.save(user);
    }
	
	@Override
	/*this method is required by the UserDetailsService interface,
	 *it loads the user by their email address
	*/
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
    	User user = userDAO.findByEmail(email).orElseThrow(() ->
            new UsernameNotFoundException("User was not found"));
    	
    	System.out.println("User roles: " + user.getRoles());
    	
    	if (user.getRoles() == null || user.getRoles().isEmpty()) {
            throw new IllegalStateException("User does not have any roles assigned.");
        }

        //map all roles to GrantedAuthority objects
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new org.springframework.security.core.authority.SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        
    	return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(authorities)
                .build();
    }
	
	// Generate the password reset link and send the email
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

    // Generate reset token and create a reset link
    private String generateResetToken(User user) {
        UUID uuid = UUID.randomUUID();
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime expiryDateTime = currentDateTime.plusMinutes(15);
        
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setUser(user);
        resetToken.setToken(uuid.toString());
        resetToken.setExpiryDateTime(expiryDateTime);

        // Save the token in the database
        tokenDAO.save(resetToken);

        // Generate the reset link
        String endpointUrl = "http://localhost:8080/resetPassword/";
        return endpointUrl + resetToken.getToken();
    }
	
 // Check if a token has expired
    public boolean hasExpired(LocalDateTime expiryDateTime) {
        return expiryDateTime.isBefore(LocalDateTime.now());
    }


    // Find a reset token by its value
    public PasswordResetToken findResetTokenByToken(String token) {
        return tokenDAO.findByToken(token); // Lookup token in the database
    }
	
 // Run this method every 1 second
    @Scheduled(fixedRate = 1000)  // Every second
    public void cleanExpiredTokens() {
        LocalDateTime now = LocalDateTime.now();
        
        // Fetch tokens that have expired
        List<PasswordResetToken> expiredTokens = tokenDAO.findByExpiryDateTimeBefore(now);
        
        // If there are expired tokens, delete them immediately
        if (!expiredTokens.isEmpty()) {
            tokenDAO.deleteAll(expiredTokens);
            System.out.println("Expired tokens cleaned up: " + expiredTokens.size());
        }
    }
    
	//method to check if an email already exists in the db
    public boolean isEmailAlreadyInUse(User user) {
    	String id = user.getEmail(); 
        List<User> usersList = userDAO.findAll();  

        //loop through all users to check if any user's email matches the input email
        for (User existingUser : usersList) {
            if (existingUser.getEmail().toLowerCase().equals(id.toLowerCase())) {
            	
            	//email already in use
            	return true;  
            }
        }
        return false;
	}
    
    //method to get user by email
    public User getUserByEmail(Object principal) {
    	UserDetails userDetails = (UserDetails) principal;
        return userDAO.findByEmail(userDetails.getUsername())
                      .orElseThrow(() -> new RuntimeException("User not found"));
    }
    
    //method to get user by id
    public User getUserById(String userId) {
        return userDAO.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }

    
    ///method to delete a user
    public void deleteUserById(String userId) {
        userDAO.deleteById(userId); 
    }
    
    //retrieve roles associated with a user
    public List<Role> getUserRoles(String idUser) {
    	
        Optional<User> user = userDAO.findById(idUser);
        if (user.isPresent()) {
        	
        	//return the roles associated with the user
            return user.get().getRoles(); 
        }
        throw new EntityNotFoundException("User not found with ID: " + idUser);
    }
    
    //method to change a user password 
    public void changePassword(String userId, String oldPassword, String newPassword) throws Exception {
        
        User user = userDAO.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found."));

        //verify old password is correct
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new Exception("The old password is wrong.");
        }

        //encode new password and update user
        String encodedNewPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedNewPassword);

        //save user with new password
        userDAO.save(user);
    }
    
    //retrieve all available user images
    public List<UserImage> getAllUserImg() {
        return userImageDAO.findAll();
    }
    
    //method to assign or change the profile image of a user
    public void assignOrUpdateProfileImage(String userId, Long userImageId) {
        //get the user by ID
        User user = userDAO.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        //get the image by ID
        UserImage userImage = userImageDAO.findById(userImageId).orElseThrow(() -> new RuntimeException("Image not found"));

        user.setUserImage(userImage);
        
        //save the user with the new image
        userDAO.save(user);
    }
}
