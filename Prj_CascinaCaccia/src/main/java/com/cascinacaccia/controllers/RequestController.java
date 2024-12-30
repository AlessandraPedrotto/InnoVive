package com.cascinacaccia.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cascinacaccia.entities.Generalform;
import com.cascinacaccia.entities.User;
import com.cascinacaccia.repos.GeneralformDAO;
import com.cascinacaccia.repos.InformationformDAO;
import com.cascinacaccia.services.UserService;

@Controller
public class RequestController {
	
	@Autowired
	GeneralformDAO generalFormDAO;
	@Autowired
	InformationformDAO informationFormDAO;
	@Autowired
	UserService userService;
	
	//mapping to display all form submissions
    @GetMapping("/request")
    public String getAllFormRequests(Model model) {
        
    	//fetch all the general forms and associated information forms
        List<Generalform> generalForms = generalFormDAO.findAll();
        List<User> users = userService.getAllUsers(); 
        
        //add the form lists to the model
        model.addAttribute("generalForms", generalForms);
        model.addAttribute("users", users);
        return "Request";
    }
}
