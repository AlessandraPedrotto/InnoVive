package com.cascinacaccia.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cascinacaccia.entities.Generalform;
import com.cascinacaccia.entities.User;
import com.cascinacaccia.repos.GeneralformDAO;
import com.cascinacaccia.services.FilterService;
import com.cascinacaccia.services.UserService;

@Controller
public class RequestController {
	
	@Autowired
	private GeneralformDAO generalFormDAO;
	@Autowired
	private UserService userService;
	
	//mapping to display all form submissions
    @GetMapping("/request")
    public String getAllFormRequests(
    		@RequestParam(name = "query", required = false, defaultValue = "") String query,
			@RequestParam(name = "sort", required = false) Boolean sortAscending,
			@RequestParam(name = "page", required = false, defaultValue = "1") int page,
	        @RequestParam(name = "size", required = false, defaultValue = "5") int size,
	        Model model) {
    	
    	List<Generalform> generalForms = generalFormDAO.findAll(Sort.by(Sort.Order.desc("submissionDate")));
    	
    	// Check if no forms are found
        if (generalForms.isEmpty()) {
            model.addAttribute("message", "No results found");
        }
        
    	//pagination logic: Get the total number of users
        int totalForms = generalForms.size();
        
        //calculate total pages
        int totalPages = FilterService.getTotalPages(generalForms, size);
        
        //ensure the current page is within the valid range
        if (page < 1) {
            page = 1; 
        } else if (page > totalPages) {
            page = totalPages; 
        }

        //paginate the list of users
        List<Generalform> paginatedForms = FilterService.getPaginatedList(generalForms, page, size);
        
        //add the form lists to the model
        model.addAttribute("query", query);
        model.addAttribute("totalPages", totalPages); 
        model.addAttribute("currentPage", page); 
        model.addAttribute("totalForms", totalForms); 
        model.addAttribute("sort", sortAscending); 
        model.addAttribute("generalForms", paginatedForms);
        
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        
        return "Request";
    }
}
