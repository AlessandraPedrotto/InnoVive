package com.cascinacaccia.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	//handles requests to the root URL ("/")
    @GetMapping("/")
    public String home() {
    	
    	//return the Home page
        return "Home"; 
    }
    @GetMapping("/chatbot")
    public String chatbot() {
    	
    	//return the Home page
        return "Chatbot"; 
    }
    @GetMapping("/prenota")
    public String prenota() {
    	
    	//return the Home page
        return "Prenota"; 
    }
    @GetMapping("/attivita")
    public String attivita() {
    	
    	//return the Home page
        return "Attivita"; 
    }
    @GetMapping("/struttura")
    public String struttura() {
    	
    	//return the Home page
        return "Struttura"; 
    }
}
