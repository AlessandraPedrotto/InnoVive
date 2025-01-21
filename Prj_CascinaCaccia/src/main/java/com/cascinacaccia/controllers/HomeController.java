package com.cascinacaccia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cascinacaccia.repos.CategoryDAO;

@Controller
public class HomeController {
	
	@Autowired
	private CategoryDAO categoryDAO;
	
	//handles requests to the root URL ("/")
    @GetMapping("/")
    public String home(Model model) {
    	String selectedLanguage = "it"; // You can set this based on the user's stored preference
        model.addAttribute("selectedLanguage", selectedLanguage);
    	model.addAttribute("categories", categoryDAO.findAll());
        model.addAttribute("categoryId", "");
    	//return the Home page
        return "Home"; 
    }
    
    @GetMapping("/chatbot")
    public String chatbot(Model model) {
    	model.addAttribute("categories", categoryDAO.findAll());
        model.addAttribute("categoryId", "");
    	
        //return the Chatbot page
        return "Chatbot"; 
    }
    
    @GetMapping("/prenota")
    public String prenota(Model model) {
    	String selectedLanguage = "it"; // You can set this based on the user's stored preference
        model.addAttribute("selectedLanguage", selectedLanguage);
    	model.addAttribute("categories", categoryDAO.findAll());
        model.addAttribute("categoryId", "");
    	
        //return the Prenota page
        return "Prenota"; 
    }
    
    @GetMapping("/attivita")
    public String attivita(Model model) {
    	model.addAttribute("categories", categoryDAO.findAll());
        model.addAttribute("categoryId", "");
    	
        //return the Attivita page
        return "Attivita"; 
    }
    
    @GetMapping("/struttura")
    public String struttura(Model model) {
    	model.addAttribute("categories", categoryDAO.findAll());
        model.addAttribute("categoryId", "");
        
    	//return the Struttura page
        return "Struttura"; 
    }
}
