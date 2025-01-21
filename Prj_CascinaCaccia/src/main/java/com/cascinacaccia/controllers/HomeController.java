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
	
	/*
     * Handles requests to the root URL ("/").
     * This method loads the home page and populates the model with categories.
     * 
     * @param model The Spring Model object to hold attributes that will be used in the view.
     * @return The name of the view to render (Home page).
     */
    @GetMapping("/")
    public String home(Model model) {
    	model.addAttribute("categories", categoryDAO.findAll());
        model.addAttribute("categoryId", "");
    	
        //return the Home page
        return "Home"; 
    }
    
    /*
     * Handles requests to "/chatbot".
     * This method loads the chatbot page and populates the model with categories.
     * 
     * @param model The Spring Model object to hold attributes that will be used in the view.
     * @return The name of the view to render (Chatbot page).
     */
    @GetMapping("/chatbot")
    public String chatbot(Model model) {
    	model.addAttribute("categories", categoryDAO.findAll());
        model.addAttribute("categoryId", "");
    	
        //return the Chatbot page
        return "Chatbot"; 
    }
    
    /*
     * Handles requests to "/prenota".
     * This method loads the Prenota (Booking) page and populates the model with categories.
     * 
     * @param model The Spring Model object to hold attributes that will be used in the view.
     * @return The name of the view to render (Prenota page).
     */
    @GetMapping("/prenota")
    public String prenota(Model model) {
    	model.addAttribute("categories", categoryDAO.findAll());
        model.addAttribute("categoryId", "");
    	
        //return the Prenota page
        return "Prenota"; 
    }
    
    /*
     * Handles requests to "/attivita".
     * This method loads the Attivita (Activities) page and populates the model with categories.
     * 
     * @param model The Spring Model object to hold attributes that will be used in the view.
     * @return The name of the view to render (Attivita page).
     */
    @GetMapping("/attivita")
    public String attivita(Model model) {
    	model.addAttribute("categories", categoryDAO.findAll());
        model.addAttribute("categoryId", "");
    	
        //return the Attivita page
        return "Attivita"; 
    }
    
    /*
     * Handles requests to "/struttura".
     * This method loads the Struttura (Structure) page and populates the model with categories.
     * 
     * @param model The Spring Model object to hold attributes that will be used in the view.
     * @return The name of the view to render (Struttura page).
     */
    @GetMapping("/struttura")
    public String struttura(Model model) {
    	model.addAttribute("categories", categoryDAO.findAll());
        model.addAttribute("categoryId", "");
        
    	//return the Struttura page
        return "Struttura"; 
    }
}
