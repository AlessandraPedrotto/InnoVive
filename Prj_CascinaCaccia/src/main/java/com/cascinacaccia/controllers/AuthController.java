package com.cascinacaccia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cascinacaccia.repos.CategoryDAO;

@Controller
public class AuthController {
	
	@Autowired
	private CategoryDAO categoryDAO;
	
	//when the user navigates to "/login", this method is called
	@GetMapping("/login")
    public String loginPage(Model model, @RequestParam(value = "error", required = false) String error, @RequestParam(value = "errorSession", required = false) String errorSession) {
		
		//add error and logout messages if available
	    if (error != null) {
	        model.addAttribute("error", "Accesso negato, la password e/o email potrebbero essere errate.");
	    }
	    if (errorSession != null) {
	        model.addAttribute("error", "Sessione scaduta, effettua nuovamente il log in.");
	    }
	    
		model.addAttribute("categories", categoryDAO.findAll());
        model.addAttribute("categoryId", "");
        
        //return the Login page
        return "Login";
    }
}