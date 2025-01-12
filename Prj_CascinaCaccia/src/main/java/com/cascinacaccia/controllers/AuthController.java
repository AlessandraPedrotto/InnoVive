package com.cascinacaccia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cascinacaccia.repos.CategoryDAO;

@Controller
public class AuthController {
	
	@Autowired
	private CategoryDAO categoryDAO;
	
	//when the user navigates to "/login", this method is called
	@GetMapping("/login")
    public String loginPage(Model model) {
		model.addAttribute("categories", categoryDAO.findAll());
        model.addAttribute("categoryId", "");
        
        //return the Login page
        return "Login";
    }
}