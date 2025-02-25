package com.cascinacaccia.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import com.cascinacaccia.repos.UserDAO;
import com.cascinacaccia.services.FilterService;
import com.cascinacaccia.services.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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
	private UserDAO userDAO;
	@Autowired
	private BookingFormDAO bookingFormDAO;
	
	//mapping to display all form submissions
    @GetMapping("/request")
    public String getAllFormRequests(
    		@RequestParam(name = "query", required = false, defaultValue = "") String query,
    		@RequestParam(name = "sort", required = false) Boolean sortAscending,
            @RequestParam(name = "sortBy", required = false, defaultValue = "newest") String sortBy,
			@RequestParam(name = "page", required = false, defaultValue = "1") int page,
	        @RequestParam(name = "size", required = false, defaultValue = "12") int size,
	        @RequestParam(name = "categories", required = false) List<String> categoryIds,
	        @RequestParam(name = "statuses", required = false) List<String> statuses,
	        @RequestParam(name = "formType", defaultValue = "all") String formType,
	        @RequestParam(name = "assignation", required = false) String assignation,
	        @RequestParam(name = "startDate", required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate,
	        @RequestParam(name = "endDate", required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endDate,
	        HttpSession session,
	        HttpServletRequest request,
	        HttpServletResponse response,
	        @AuthenticationPrincipal Object principal,
	        Model model) {
    	
    	User user = userService.getUserByEmail(principal);
    	
    	//ensure statuses is never null
        if (statuses == null) {
            statuses = new ArrayList<>();
        }
        
        session.setAttribute("newFormCount", 0);
        
        //update the 'lastSeen' timestamp for the user
        LocalDateTime now = LocalDateTime.now();
        user.setLastSeen(now);
        userDAO.save(user);
        
        //reset the "lastSeen" cookie to update the timestamp of when the user last viewed the requests
        Cookie lastSeenCookie = new Cookie("lastSeen", LocalDateTime.now().toString());
        lastSeenCookie.setMaxAge(60 * 60 * 24 * 365); // 1 year
        lastSeenCookie.setPath("/");
        response.addCookie(lastSeenCookie); 
        
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
        
        //apply filter by date range if provided
        if (startDate != null && endDate != null) {
            generalForms = FilterService.filterByDateRange(generalForms, startDate, endDate);
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
            model.addAttribute("message", "Nessun risultato trovato per questa ricerca.");
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
        
        int blockSize = 5;
        int currentPage = page;
        
	    //calculate the start and end pages for the current block
        int startPage = ((currentPage - 1) / blockSize) * blockSize + 1;
        int endPage = Math.min(startPage + blockSize - 1, totalPages);

        //paginate the list of users
        List<Generalform> paginatedForms = FilterService.getPaginatedList(generalForms, page, size);
        List<User> users = userService.getAllUsers();
        List<Category> categories = categoryDAO.findAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        
        AtomicReference<LocalDateTime> lastSeenRef = new AtomicReference<>(LocalDateTime.now().minusDays(30));
        
        //read cookies and get the "lastSeen" timestamp
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("lastSeen".equals(cookie.getName())) {
                    try {
                        String lastSeenStr = cookie.getValue();
                        DateTimeFormatter timeFormatter = DateTimeFormatter.ISO_DATE_TIME;
                        lastSeenRef.set(LocalDateTime.parse(lastSeenStr, formatter));
                    } catch (Exception e) {
                        
                    	//error parsing cookie
                        System.err.println("Error parsing lastSeen cookie: " + e.getMessage());
                    }
                }
            }
        }
        
        LocalDateTime lastSeen = user.getLastSeen() != null ? user.getLastSeen() : LocalDateTime.now().minusDays(30);
        
        //calculate the new form count based on lastSeen for the specific user
        long newFormCount = generalForms.stream()
            .filter(generalForm -> generalForm.getSubmissionDate() != null &&
                generalForm.getSubmissionDate().isAfter(lastSeen))
            .count();
            
        //set session attribute to reset new form count
        session.setAttribute("newFormCount", newFormCount);
	
	    model.addAttribute("startPage", startPage);
	    model.addAttribute("endPage", endPage);
	    model.addAttribute("blockSize", blockSize);
	    model.addAttribute("hasPreviousBlock", startPage > 1);
	    model.addAttribute("hasNextBlock", endPage < totalPages);
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
        model.addAttribute("newFormCount", newFormCount);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        return "Request";
    }
}