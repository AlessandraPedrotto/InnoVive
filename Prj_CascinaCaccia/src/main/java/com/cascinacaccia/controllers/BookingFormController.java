package com.cascinacaccia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cascinacaccia.entities.Bookingform;
import com.cascinacaccia.repos.BookingformDAO;
import com.cascinacaccia.repos.CategoryDAO;
import com.cascinacaccia.repos.GeneralformDAO;
import com.cascinacaccia.services.BookingFormService;

@Controller
public class BookingFormController {

	@Autowired
    private CategoryDAO categoryDAO;
	
    @Autowired
    private GeneralformDAO generalFormDAO;
    
    @Autowired
    private BookingformDAO bookingdao;
    
    @Autowired
    private BookingFormService bookingservice;
    
    
    @GetMapping("/bookingform")
    public String showForm(Model model) {
    	model.addAttribute("categories", categoryDAO.findAll());
        model.addAttribute("categoryId", "");
        return "BookingForm";
    }
    
   

    
}
