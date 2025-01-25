package com.cascinacaccia.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class CustomErrorController {
	
	/*
     * Handles requests to the "/error" URL.
     * This method is called whenever an error occurs in the application, such as a 404 or 500 error.
     * It returns the view name "Error" to render the error page.
     * 
     * @return The name of the error page view to be rendered.
     */
	@GetMapping("/error")
	public String handleError(Model model,@RequestParam(value = "lang", required = false) String lang) {
		model.addAttribute("selectedLanguage", lang);
		
		//return the Error page
		return "Error";
	}
}
