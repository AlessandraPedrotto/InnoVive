package com.cascinacaccia.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cascinacaccia.entities.Role;
import com.cascinacaccia.entities.User;
import com.cascinacaccia.repos.UserDAO;

@Service
/*the UserService class implements UserDetailsService, 
 *which is used for loading user-specific data during authentication in Spring Security
*/
public class UserService implements UserDetailsService{
	
	@Autowired
	UserDAO userDAO;
	@Autowired
	PasswordEncoder passwordEncoder;
	
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
        
        //create a list of roles for the user
        List<Role> roles = new ArrayList<>();
        //roles.add(new Role("2", "ROLE_ADMIN")); //uncomment this line to add an admin role and comment the other one
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
            new UsernameNotFoundException("Utente non trovato"));
    	
    	System.out.println("User roles: " + user.getRoles());
    	
    	//if the user does not have any roles assigned, throw an IllegalStateException
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            throw new IllegalStateException("User does not have any roles assigned.");
        }
        
    	return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRoles().get(0).getName().substring(5))
                .build();
    }
}
