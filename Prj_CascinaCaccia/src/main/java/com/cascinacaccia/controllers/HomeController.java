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
}
