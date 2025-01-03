package com.cascinacaccia.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cascinacaccia.entities.Generalform;
import com.cascinacaccia.entities.Role;
import com.cascinacaccia.entities.User;
import com.cascinacaccia.entities.UserImage;
import com.cascinacaccia.repos.UserDAO;
import com.cascinacaccia.repos.UserImageDAO;

import jakarta.persistence.EntityNotFoundException;

@Service
/*
* UserService is a service class responsible for managing user-related operations in the system.
* It implements the UserDetailsService interface for user authentication with Spring Security.
* The primary functionalities provided by this service include:
* - Registering a new user and assigning default roles and profile images.
* - Assigning or updating roles for a user.
* - Loading user details by email during authentication (required by Spring Security).
* - Checking if a user's email already exists in the database.
* - Retrieving user details by email or ID.
* - Deleting a user by ID.
* - Retrieving the roles associated with a user.
* - Changing a user's password.
* - Assigning or updating a user's profile image.
* - Retrieving a list of all available user images.
* - Fetching all users in the system.
*/
public class UserService implements UserDetailsService{
	
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private UserImageDAO userImageDAO;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Lazy
	@Autowired
	private InformationFormService informationFormService;
	
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
        
        //fetch the default profile image from the database
        UserImage defaultProfileImage = userImageDAO.findById(12L)
                .orElseThrow(() -> new RuntimeException("Default profile image not found"));
        
        //set the default profile image for the new user
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

    //method to delete a user
    public void deleteUserById(String userId) {
    	
    	// Fetch the user
    	User user = userDAO.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        // Step 1: Remove associations with Informationforms
        List<Generalform> generalForms = informationFormService.getAssignedFormsByUser(userId);
        for (Generalform generalForm : generalForms) {
            generalForm.getInformationForms().forEach(informationForm -> {
                if (informationForm.getAssignedUser() != null && 
                    informationForm.getAssignedUser().getId().equals(userId)) {
                    // Nullify the user reference in the information form
                    informationForm.setAssignedUser(null);
                    informationFormService.saveInformationForm(informationForm); // Save the update
                }
            });
        }

        // Step 2: Remove roles and other associations
        user.getRoles().clear(); // Clear roles
        userDAO.save(user); // Save the updated user state to ensure no dangling references

        // Step 3: Delete the user
        userDAO.deleteById(userId); 
    }
    
    //method to retrieve roles associated with a user
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
    
    //method to retrieve all available user images
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
    
    //method to get all the users
    public List<User> getAllUsers() {
        return userDAO.findAll(); 
    }
}
