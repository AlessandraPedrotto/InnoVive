package com.cascinacaccia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
}