package com.cascinacaccia.controllers;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cascinacaccia.entities.User;
import com.cascinacaccia.services.FilterService;
import com.cascinacaccia.services.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
    UserService userService;
	@Autowired
	FilterService filterService;
	
	//regular expressions for validating email and password
    private static final String REGEX_PASSWORD = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[\\p{Punct}])(?=\\S+$).{8,}$";
    private static final String REGEX_EMAIL = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

	
    //list of all the existing accounts
    @GetMapping("/listUsers")
    public String listUsers(@RequestParam(name = "query", required = false, defaultValue = "") String query,
            				@RequestParam(name = "sort", required = false) Boolean sortAscending,
            				@RequestParam(name = "page", required = false, defaultValue = "1") int page,
            		        @RequestParam(name = "size", required = false, defaultValue = "3") int size,Model model) {
        List<User> users;

        //if there's a query, search the users; otherwise, get all users
        if (query == null || query.isEmpty()) {
        	users = filterService.getAllUsers();
        } else {
            users = filterService.searchUsers(query);
         // If no results found
            if (users.isEmpty()) {
                model.addAttribute("noResults", "No users found for the search query: " + query);
            }
        }
        
        //if sorting is required, call the sorting method
        if (sortAscending != null) {
            users = filterService.sortUsersBySurname(users, sortAscending);
        }

        //pagination logic: Get the total number of users
        int totalUsers = users.size();
        
        //calculate total pages
        int totalPages = (int) Math.ceil((double) totalUsers / size);
        
        //ensure the current page is within the valid range
        if (page < 1) {
            page = 1; 
        } else if (page > totalPages) {
            page = totalPages; 
        }

        //paginate the list of users
        List<User> paginatedUsers = filterService.getPaginatedUsers(users, page, size);

        //add pagination details to the model
        model.addAttribute("users", paginatedUsers);
        model.addAttribute("query", query);
        model.addAttribute("totalPages", totalPages); 
        model.addAttribute("currentPage", page); 
        model.addAttribute("totalUsers", totalUsers); 
        model.addAttribute("sort", sortAscending);
        model.addAttribute("hasUsers", !users.isEmpty()); 
        return "ListUsers";
    }

    //delete User
    @GetMapping("/deleteUser")
    public String confirmDeleteUser(@RequestParam String userId, Model model) {
        //get the user details to display on the confirmation page
        User user = userService.getUserById(userId);

        //add user data to the model so it can be displayed on the page
        model.addAttribute("user", user);

        //return the confirmation page
        return "DeleteUser"; 
    }
    
    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam String userId) {
        userService.deleteUserById(userId);
        return "redirect:/admin/listUsers"; 
    }
    
    //this is a GET request to "/register" where we load the registration form
    @GetMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "UserRegister"; 
    }

    //handle User Registration (POST request)
    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public String registerUser(@ModelAttribute User user, Model model) {
    	
        //check if the email is already registered
        boolean userExists = userService.isEmailAlreadyInUse(user);
        if (userExists) {
            model.addAttribute("exist", "Error, the email is already registered.");
            return "UserRegister";  
        }

        //validate email format
        if (!isValidEmail(user.getEmail())) {
            model.addAttribute("emailError", "Invalid email format.");
            return "UserRegister";  
        }

        //validate password format
        if (!isValidPassword(user.getPassword())) {
            model.addAttribute("passwordError", "Password must be at least 8 characters long, contain an uppercase letter, a number, and a special character.");
            return "UserRegister";  
        }
        
        try {
            //register the new user
            userService.register(user);
            model.addAttribute("regiSuccess", "Registered successfully");
            return "redirect:/profile"; 
        } catch (Exception e) {
            model.addAttribute("error", "Registration failed: " + e.getMessage());
            return "UserRegister";  
        }
    }
    
    //method to validate password using regex
    private boolean isValidPassword(String password) {
        Pattern pattern = Pattern.compile(REGEX_PASSWORD);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches(); 
    }

    //method to validate email using regex
    private boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(REGEX_EMAIL);  
        Matcher matcher = pattern.matcher(email); 
        return matcher.matches(); 
    }
}
