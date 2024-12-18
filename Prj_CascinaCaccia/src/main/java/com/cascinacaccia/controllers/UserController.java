package com.cascinacaccia.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

	//when the user navigates to "/home", this method is called
	@GetMapping("/profile")
    public String homePage() {
        return "Profile";
    }
}
