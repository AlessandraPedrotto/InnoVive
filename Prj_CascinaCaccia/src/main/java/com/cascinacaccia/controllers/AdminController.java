package com.cascinacaccia.controllers;

import java.util.List;

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

    //list of all the existing accounts
    @GetMapping("/listUsers")
    public String listUsers(@RequestParam(name = "query", required = false, defaultValue = "") String query,
            				@RequestParam(name = "sort", required = false) Boolean sortAscending,
            				@RequestParam(name = "page", required = false, defaultValue = "1") int page,
            		        @RequestParam(name = "size", required = false, defaultValue = "1") int size,Model model) {
        List<User> users;

        //if there's a query, search the users; otherwise, get all users
        if (query == null || query.isEmpty()) {
        	users = filterService.getAllUsers();
        } else {
            users = filterService.searchUsers(query);
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

        // Paginate the list of users
        List<User> paginatedUsers = filterService.getPaginatedUsers(users, page, size);

        // Add pagination details to the model
        model.addAttribute("users", paginatedUsers);
        model.addAttribute("query", query);
        model.addAttribute("totalPages", totalPages); 
        model.addAttribute("currentPage", page); 
        model.addAttribute("totalUsers", totalUsers); 
        model.addAttribute("sort", sortAscending); 
        return "ListUsers";
    }

    //delete User
    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam String userId) {
        userService.deleteUserById(userId);
     
        //redirect back to the dashboard
        return "redirect:/admin/listUsers"; 
    }
    
  //this is a GET request to "/register" where we load the registration form
    @GetMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "UserRegister"; 
    }

    // Handle User Registration (POST request)
    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public String registerUser(@ModelAttribute User user, Model model) {
        // Check if the email is already registered
        boolean userExists = userService.isEmailAlreadyInUse(user);
        if (userExists) {
            model.addAttribute("exist", "Error, the email is already registered.");
            return "UserRegister";  // Stay on the register page
        }

        try {
            // Register the new user
            userService.register(user);
            model.addAttribute("regiSuccess", "Registered successfully");
            return "redirect:/profile";  // Redirect to profile page after success
        } catch (Exception e) {
            model.addAttribute("error", "Registration failed: " + e.getMessage());
            return "UserRegister";  // Stay on the register page if there is an error
        }
    }
}
