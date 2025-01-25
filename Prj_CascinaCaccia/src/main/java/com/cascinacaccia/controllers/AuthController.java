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
	
	/*
     * Handles requests to the "/login" URL.
     * This method is called when a user navigates to the login page.
     * It checks for error or session expiration parameters and adds the corresponding messages to the model.
     * 
     * @param model The model object used to pass attributes to the view.
     * @param error An optional parameter indicating a login error.
     * @param errorSession An optional parameter indicating a session expiration.
     * 
     * @return The name of the view (Login page) to render.
     */
	@GetMapping("/login")
    public String loginPage(Model model, 
    						@RequestParam(value = "error", required = false) String error, 
    						@RequestParam(value = "errorSession", required = false) String errorSession,
    						@RequestParam(value = "lang", required = false) String lang) {
		
		if (lang == null || lang.isEmpty()) {
	        lang = "it"; // Fallback to Italian
	    }
		
		// Handle error and session expired messages
		if (error != null) {
		    // Check if the language is English or Italian and set the error message accordingly
		    String errorMessage = (lang.equals("en")) ? "Access denied, password and/or email may be incorrect."
		                                              : "Accesso negato, la password e/o l'email potrebbero essere errati.";
		    model.addAttribute("error", errorMessage);
		}

		// Handle session expired error
		if (errorSession != null) {
		    // Check if the language is English or Italian and set the session expired message accordingly
		    String sessionExpiredMessage = (lang.equals("en")) ? "Session expired, please log in again."
		                                                       : "Sessione scaduta, per favore accedi di nuovo.";
		    model.addAttribute("errorSession", sessionExpiredMessage);
		}
	    
		model.addAttribute("categories", categoryDAO.findAll());
        model.addAttribute("categoryId", "");
        model.addAttribute("selectedLanguage", lang);
        //return the Login page
        return "Login";
    }
}