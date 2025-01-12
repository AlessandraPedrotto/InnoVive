package com.cascinacaccia.controllers;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cascinacaccia.entities.BookingForm;
import com.cascinacaccia.entities.Category;
import com.cascinacaccia.entities.Generalform;
import com.cascinacaccia.entities.Informationform;
import com.cascinacaccia.entities.User;
import com.cascinacaccia.repos.BookingFormDAO;
import com.cascinacaccia.repos.CategoryDAO;
import com.cascinacaccia.repos.GeneralformDAO;
import com.cascinacaccia.repos.InformationformDAO;
import com.cascinacaccia.services.FilterService;
import com.cascinacaccia.services.UserService;

@Controller
public class RequestController {
	
	@Autowired
	private GeneralformDAO generalFormDAO;
	@Autowired
	private InformationformDAO informationFormDAO;
	@Autowired
	private UserService userService;
	@Autowired
	private CategoryDAO categoryDAO;
	@Autowired
	private BookingFormDAO bookingFormDAO;
	
	//mapping to display all form submissions
    @GetMapping("/request")
    public String getAllFormRequests(
    		@RequestParam(name = "query", required = false, defaultValue = "") String query,
    		@RequestParam(name = "sort", required = false) Boolean sortAscending,
            @RequestParam(name = "sortBy", required = false, defaultValue = "newest") String sortBy,
			@RequestParam(name = "page", required = false, defaultValue = "1") int page,
	        @RequestParam(name = "size", required = false, defaultValue = "5") int size,
	        @RequestParam(name = "categories", required = false) List<String> categoryIds,
	        @RequestParam(name = "statuses", required = false) List<String> statuses,
	        @RequestParam(name = "formType", defaultValue = "all") String formType,
	        @RequestParam(name = "assignation", required = false) String assignation,
	        Model model) {
    	
    	//ensure statuses is never null
        if (statuses == null) {
            statuses = new ArrayList<>();
        }
        
    	List<Generalform> generalForms = generalFormDAO.findAll();
        List<Informationform> informationForms = informationFormDAO.findAll();
        List<BookingForm> bookingForms = bookingFormDAO.findAll();
        
    	//filter by selected categories if available
        generalForms = FilterService.filterFormsByCategories(generalForms, categoryIds);
        
        generalForms = FilterService.filterFormsByStatuses(generalForms, statuses);
    	
        //apply form type filter (either Information Form or Booking Form)
        if ("informationForm".equals(formType)) {
        	generalForms = FilterService.filterByInformationForm(generalForms);
        } else if ("bookingForm".equals(formType)) {
        	generalForms = FilterService.filterByBookingForm(generalForms);
        }
        
        //apply filter by assignation status if provided
        if (assignation != null && !assignation.isEmpty()) {
            boolean isAssigned = "assigned".equals(assignation);
            generalForms = FilterService.filterByAssignment(generalForms, isAssigned);
        }
        
        //sort forms based on the selected option
        switch (sortBy) {
            case "surnameAsc":
                generalForms = FilterService.sortBySurname(generalForms, true);
                break;
            case "surnameDesc":
                generalForms = FilterService.sortBySurname(generalForms, false);
                break;
            case "newest":
                generalForms = FilterService.sortBySubmissionDate(generalForms, false);
                break;
            case "oldest":
                generalForms = FilterService.sortBySubmissionDate(generalForms, true);
                break;
            default:
                //default to newest
                generalForms = FilterService.sortBySubmissionDate(generalForms, false);
        }
        
    	//check if no forms are found
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
        List<User> users = userService.getAllUsers();
        List<Category> categories = categoryDAO.findAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        
        model.addAttribute("query", query);
        model.addAttribute("totalPages", totalPages); 
        model.addAttribute("currentPage", page); 
        model.addAttribute("totalForms", totalForms); 
        model.addAttribute("sort", sortAscending);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("generalForms", paginatedForms);
        model.addAttribute("users", users);
        model.addAttribute("categories", categories);
        model.addAttribute("categoriesSelected", categoryIds != null ? categoryIds : List.of());
        model.addAttribute("informationForms", informationForms);
        model.addAttribute("bookingForms", bookingForms);
        model.addAttribute("statusesSelected", statuses);
        model.addAttribute("formatter", formatter);
        model.addAttribute("formType", formType);
        model.addAttribute("assignation", assignation);
        return "Request";
    }
}