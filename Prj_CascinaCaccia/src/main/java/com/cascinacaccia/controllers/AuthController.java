package com.cascinacaccia.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
	
	//when the user navigates to "/login", this method is called
	@GetMapping("/login")
    public String loginPage() {
        return "Login";
    }
}