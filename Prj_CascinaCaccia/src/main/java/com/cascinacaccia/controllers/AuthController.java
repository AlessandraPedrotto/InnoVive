package com.cascinacaccia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.cascinacaccia.entities.User;
import com.cascinacaccia.services.UserService;

@Controller
public class AuthController {

	@Autowired
	UserService userService;
	
	//when the user navigates to "/login", this method is called
	@GetMapping("/login")
    public String loginPage() {
        return "Login";
    }
	
	//this is a GET request to "/register" where we load the registration form
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "UserRegister"; 
    }
    
    //this POST method handles the form submission after the user fills out the registration form
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
    	
    	// Check if the user (email) already exists
        boolean userExists = userService.isEmailAlreadyInUse(user);
        if (userExists) {
            model.addAttribute("exist", "Error, the email is alredy registered.");
            return "UserRegister";
        }
    	try {
            userService.register(user);
            model.addAttribute("regiSuccess", "Registered successfully");
            //after successful registration, redirect the user to the login page
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", "Registration failed: " + e.getMessage());
            //if an error occurs during registration return to the registration page with the error message
            return "UserRegister";
        }
    }
}