package com.cascinacaccia.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cascinacaccia.entities.BookingForm;
import com.cascinacaccia.entities.Generalform;
import com.cascinacaccia.entities.Role;
import com.cascinacaccia.entities.User;
import com.cascinacaccia.entities.UserImage;
import com.cascinacaccia.repos.UserDAO;
import com.cascinacaccia.repos.UserImageDAO;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

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
	private BookingFormService bookingFormService;
	@Lazy
	@Autowired
	private InformationFormService informationFormService;
	
	/*
	 * Updates the user's state and last access time in the database.
	 * 
	 * @param email The email address of the user whose state and last access time need to be updated.
	 * @param state The new state to assign to the user (e.g., "ONLINE" or "OFFLINE").
	 */
	@Transactional
	public void updateUserStateAndLastAccess(String email, String state) {

	    User user = userDAO.findByEmail(email)
	            .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

	    user.setState(state);
	    user.setLastAccess(LocalDateTime.now());

	    userDAO.save(user);
	}
	
	@Scheduled(fixedRate = 100000) // Every 5 minutes
	public void markInactiveUsersOffline() {
	    LocalDateTime cutoffTime = LocalDateTime.now().minusMinutes(5);
	    List<User> inactiveUsers = userDAO.findUsersByStateAndLastAccessBefore("ONLINE", cutoffTime);
	    for (User user : inactiveUsers) {
	        user.setState("OFFLINE");
	        userDAO.save(user);
	    }
	}
	
	 /*
     * Registers a new user by setting their ID, name, email, password, default profile image, 
     * and role, then saves the user to the database.
     * 
     * @param user The user object containing registration details.
     */
	public void register(User user) { 
		
        String id = UUID.randomUUID().toString();
        String name = user.getName();
        String email = user.getEmail();
        String password = passwordEncoder.encode(user.getPassword());
        user.setId(id);
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setState("OFFLINE");
        
        //fetch the default profile image from the database
        UserImage defaultProfileImage = userImageDAO.findById(12L)
                .orElseThrow(() -> new RuntimeException("Default profile image not found"));
        
        //set the default profile image for the new user
        user.setUserImage(defaultProfileImage);
        
        //assign a role for the user
        List<Role> roles = new ArrayList<>();
        //roles.add(new Role("2", "ROLE_ADMIN")); //uncomment this line to add an admin 
        roles.add(new Role("1", "ROLE_EMPLOYEE"));
        
        //save the user and the user roles in the database
        user.setRoles(roles);
        userDAO.save(user);
    }
	
	/*
     * Assigns roles to an existing user.
     * 
     * @param user The user to assign roles to.
     * @param roles A list of roles to assign to the user.
     * @throws IllegalArgumentException if the number of roles is less than 1 or more than 2.
     */
	public void assignRoles(User user, List<Role> roles) {
        
		//check if the number of roles assigned is between 1 and 2
        if (roles.size() < 1 || roles.size() > 2) {
            throw new IllegalArgumentException("User must have at least 1 role and a maximum of 2 roles.");
        }
        
        user.setRoles(roles);
        userDAO.save(user);
    }
	
	/*
     * Loads a user by their email address. This method is required by the UserDetailsService interface.
     * It maps the user roles to GrantedAuthority objects for Spring Security.
     * 
     * @param email The email of the user to load.
     * @return UserDetails object representing the authenticated user.
     * @throws UsernameNotFoundException if the user is not found.
     */
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
		//find the user by email, throw exception if not found
    	User user = userDAO.findByEmail(email).orElseThrow(() ->
            new UsernameNotFoundException("User was not found"));
    	
    	System.out.println("User roles: " + user.getRoles());
    	
    	//ensure the user has at least one role assigned
    	if (user.getRoles() == null || user.getRoles().isEmpty()) {
            throw new IllegalStateException("User does not have any roles assigned.");
        }
    	
    	user.setState("ONLINE");
        user.setLastAccess(LocalDateTime.now());

        // Save the updated user state to the database
        userDAO.save(user);
        System.out.println("User state updated to ONLINE for email: " + email);

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
    
	/*
     * Checks if the given email is already in use by an existing user in the database.
     * 
     * @param user The user whose email needs to be checked.
     * @return true if the email is already in use, false otherwise.
     */
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
    
    /*
     * Retrieves a user by their email address from the currently authenticated principal.
     * 
     * @param principal The currently authenticated user.
     * @return The User object corresponding to the authenticated principal.
     */
    public User getUserByEmail(Object principal) {
    	UserDetails userDetails = (UserDetails) principal;
        return userDAO.findByEmail(userDetails.getUsername())
                      .orElseThrow(() -> new RuntimeException("User not found"));
    }
    
    /*
     * Retrieves a user by their ID.
     * 
     * @param userId The ID of the user.
     * @return The User object corresponding to the user ID.
     * @throws RuntimeException if the user is not found.
     */
    public User getUserById(String userId) {
        return userDAO.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }

    /*
     * Deletes a user by their ID. This method also removes associations with information forms 
     * and clears roles before deleting the user from the database.
     * 
     * @param userId The ID of the user to delete.
     * @throws IllegalArgumentException if the user is not found.
     */
    public void deleteUserById(String userId) {
    	
    	//fetch the user
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
        
        // Step 2: Remove associations with BookingForms
        List<Generalform> generalFormss = bookingFormService.getAssignedFormsByUserBooking(userId); 
        for (Generalform generalForm : generalFormss) {
        	generalForm.getBookingForms().forEach(bookingForm -> {
	        	if (bookingForm.getAssignedUser() != null && 
		                bookingForm.getAssignedUser().getId().equals(userId)) {
		                
		                // Nullify the user reference in the booking form
		                bookingForm.setAssignedUser(null);
		                bookingFormService.saveBookingForm(bookingForm); // Save the update
		            }
        		});
        }
        
        // Step 3: Remove roles and other associations
        user.getRoles().clear();
        userDAO.save(user);

        // Step 4: Delete the user
        userDAO.deleteById(userId); 
    }
    
    /*
     * Retrieves the roles associated with a user.
     * 
     * @param idUser The ID of the user whose roles need to be retrieved.
     * @return A list of roles assigned to the user.
     * @throws EntityNotFoundException if the user is not found.
     */
    public List<Role> getUserRoles(String idUser) {
    	
        Optional<User> user = userDAO.findById(idUser);
        if (user.isPresent()) {
        	
        	//return the roles associated with the user
            return user.get().getRoles(); 
        }
        throw new EntityNotFoundException("User not found with ID: " + idUser);
    }
    
    /*
     * Changes the password for a user after verifying that the old password is correct.
     * 
     * @param userId The ID of the user whose password needs to be changed.
     * @param oldPassword The current password of the user.
     * @param newPassword The new password to set.
     * @throws Exception if the old password is incorrect.
     */
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
    
    /*
     * Retrieves all available user images from the database.
     * 
     * @return A list of user images.
     */
    public List<UserImage> getAllUserImg() {
        return userImageDAO.findAll();
    }
    
    /*
     * Assigns or updates the profile image for a user.
     * 
     * @param userId The ID of the user to update the profile image for.
     * @param userImageId The ID of the new user image to assign.
     */
    public void assignOrUpdateProfileImage(String userId, Long userImageId) {
        
    	//get the user by ID
        User user = userDAO.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        //get the image by ID
        UserImage userImage = userImageDAO.findById(userImageId).orElseThrow(() -> new RuntimeException("Image not found"));

        user.setUserImage(userImage);
        
        //save the user with the new image
        userDAO.save(user);
    }
    
    /*
     * Retrieves all users from the database.
     * 
     * @return A list of all users.
     */
    public List<User> getAllUsers() {
        return userDAO.findAll(); 
    }
    
    /*
     * Updates the name and surname of a user.
     * 
     * @param userId   The ID of the user whose name and surname need to be updated.
     * @param newName  The new name to be set for the user.
     * @param newSurname The new surname to be set for the user.
     * 
     * @throws RuntimeException if the user with the specified ID is not found.
     */
    public void updateNameAndSurname(String userId, String newName, String newSurname) {
        User user = userDAO.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(newName);
        user.setSurname(newSurname);
        
        //save updated user details in the database
        userDAO.save(user);
    }
}
