package com.cascinacaccia.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cascinacaccia.entities.BookingForm;
import com.cascinacaccia.entities.Category;
import com.cascinacaccia.entities.Generalform;
import com.cascinacaccia.entities.Informationform;
import com.cascinacaccia.entities.User;
import com.cascinacaccia.repos.CategoryDAO;
import com.cascinacaccia.repos.GeneralformDAO;
import com.cascinacaccia.repos.InformationformDAO;
import com.cascinacaccia.repos.UserDAO;
import com.cascinacaccia.services.BookingFormService;
import com.cascinacaccia.services.FilterService;
import com.cascinacaccia.services.InformationFormService;
import com.cascinacaccia.services.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private GeneralformDAO generalFormDAO;
	@Autowired
	private UserService userService;
	@Autowired
	private FilterService filterService;
	@Autowired
	private InformationFormService informationFormService;
	@Autowired
	private InformationformDAO informationFormDAO;
	@Autowired
	private CategoryDAO categoryDAO;
	@Autowired
	private BookingFormService bookingFormService;
	
	
	//regular expressions for validating email and password
    private static final String REGEX_PASSWORD = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[\\p{Punct}])(?=\\S+$).{8,}$";
    private static final String REGEX_EMAIL = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

	
    //list of all the existing accounts
    @GetMapping("/listUsers")
    public String listUsers(@RequestParam(name = "query", required = false, defaultValue = "") String query,
				    		@RequestParam(name = "sort", required = false) Boolean sortAscending,
				            @RequestParam(name = "sortBy", required = false, defaultValue = "surnameAsc") String sortBy,
            				@RequestParam(name = "page", required = false, defaultValue = "1") int page,
            		        @RequestParam(name = "size", required = false, defaultValue = "3") int size,
            		        @AuthenticationPrincipal org.springframework.security.core.userdetails.User loggedInUser, 
            		        @AuthenticationPrincipal Object principal,
            		        HttpSession session,
            	            HttpServletRequest request,
            		        Model model) {
    	
    	User user = userService.getUserByEmail(principal);
    	
    	if (loggedInUser == null) {
            return "redirect:/login"; 
        }

        //you need to fetch the full custom user object based on email
        User userFromDb = userDAO.findByEmail(loggedInUser.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Logged-in user not found"));

    	
        List<User> users;
        
        //if there's a query, search the users; otherwise, get all users
        if (query == null || query.isEmpty()) {
        	users = filterService.getAllUsers();
        } else {
            users = filterService.searchUsers(query);
            
            //if no results found
            if (users.isEmpty()) {
                model.addAttribute("noResults", "Nessun utente trovato con questo tipo di ricerca: " + query);
            }
        }
        
        //handle null sortAscending
        if (sortAscending == null) {
            sortAscending = true;
        }
        
        //apply sorting logic after filtering
        if (users != null && !users.isEmpty()) {
            switch (sortBy) {
                case "surnameAsc":
                    users = FilterService.sortUsersBySurname(users, true);
                    break;
                case "surnameDesc":
                    users = FilterService.sortUsersBySurname(users, false);
                    break;
                default:
                    //default sort logic
                    users = FilterService.sortUsersBySurname(users, sortAscending);
                    break;
            }
            
            //no result found
            if (users.isEmpty()) {
                model.addAttribute("noResults", "Nessun utente trovato con questo tipo di ricerca: " + query);
            }
        }

        //pagination logic: Get the total number of users
        int totalUsers = users.size();
        
        //calculate total pages
        int totalPages = FilterService.getTotalPages(users, size);
        
        //ensure the current page is within the valid range
        if (page < 1) {
            page = 1; 
        } else if (page > totalPages) {
            page = totalPages; 
        }

        //paginate the list of users
        List<User> paginatedUsers = FilterService.getPaginatedList(users, page, size);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        
        List<Generalform> generalForms = generalFormDAO.findAll();
    	
    	AtomicReference<LocalDateTime> lastSeenRef = new AtomicReference<>(LocalDateTime.now().minusDays(30));
        
        // Read cookies and get the "lastSeen" timestamp
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("lastSeen".equals(cookie.getName())) {
                    try {
                        String lastSeenStr = cookie.getValue();
                        DateTimeFormatter timeFormatter = DateTimeFormatter.ISO_DATE_TIME;
                        lastSeenRef.set(LocalDateTime.parse(lastSeenStr, formatter));
                    } catch (Exception e) {
                        // Error parsing cookie
                        System.err.println("Error parsing lastSeen cookie: " + e.getMessage());
                    }
                }
            }
        }
        
        LocalDateTime lastSeen = user.getLastSeen() != null ? user.getLastSeen() : LocalDateTime.now().minusDays(30);  // Use the user's own lastSeen value
        
        // Calculate the new form count based on lastSeen for the specific user
        long newFormCount = generalForms.stream()
            .filter(generalForm -> generalForm.getSubmissionDate() != null &&
                generalForm.getSubmissionDate().isAfter(lastSeen))
            .count();
            
        // Set session attribute to reset new form count
        session.setAttribute("newFormCount", newFormCount);
        
        model.addAttribute("users", paginatedUsers);
        model.addAttribute("query", query);
        model.addAttribute("totalPages", totalPages); 
        model.addAttribute("currentPage", page); 
        model.addAttribute("totalUsers", totalUsers); 
        model.addAttribute("sort", sortAscending); 
        model.addAttribute("hasUsers", !users.isEmpty());
        model.addAttribute("sort", sortAscending);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("hasUsers", !users.isEmpty()); 
        model.addAttribute("loggedInUserId", userFromDb.getId());
        model.addAttribute("formatter", formatter);
        model.addAttribute("newFormCount", newFormCount);
        return "ListUsers";
    }
    
    //view user details (Public Profile)
    @GetMapping("/publicProfile/{userId}")
    public String publicProfile(@PathVariable("userId") String userId, @AuthenticationPrincipal Object principal, Model model, HttpSession session,
            HttpServletRequest request) {
    	User users = userService.getUserByEmail(principal);
    	User user = userService.getUserById(userId);

    	if (user != null) {
            model.addAttribute("user", user);  // Make sure user is added to the model
        } else {
            // Handle the case where user is not found
            model.addAttribute("error", "Utente non trovato.");
        }
    	
        //add user details to the model (name, surname, email, and profile image)
        String profileImageUrl = user.getUserImage() != null ? user.getUserImage().getImgPath() : "/default-image.png";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        
        List<Generalform> generalForms = generalFormDAO.findAll();
    	
    	AtomicReference<LocalDateTime> lastSeenRef = new AtomicReference<>(LocalDateTime.now().minusDays(30));
        
        // Read cookies and get the "lastSeen" timestamp
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("lastSeen".equals(cookie.getName())) {
                    try {
                        String lastSeenStr = cookie.getValue();
                        DateTimeFormatter timeFormatter = DateTimeFormatter.ISO_DATE_TIME;
                        lastSeenRef.set(LocalDateTime.parse(lastSeenStr, formatter));
                    } catch (Exception e) {
                        // Error parsing cookie
                        System.err.println("Error parsing lastSeen cookie: " + e.getMessage());
                    }
                }
            }
        }
        
        LocalDateTime lastSeen = users.getLastSeen() != null ? users.getLastSeen() : LocalDateTime.now().minusDays(30);  // Use the user's own lastSeen value
        
        // Calculate the new form count based on lastSeen for the specific user
        long newFormCount = generalForms.stream()
            .filter(generalForm -> generalForm.getSubmissionDate() != null &&
                generalForm.getSubmissionDate().isAfter(lastSeen))
            .count();
            
        // Set session attribute to reset new form count
        session.setAttribute("newFormCount", newFormCount);
        
        model.addAttribute("fullName", user.getName());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("profileImageUrl", profileImageUrl);
        model.addAttribute("surname", user.getSurname());
        model.addAttribute("state", user.getState());
        model.addAttribute("lastAccess", user.getLastAccess());
        model.addAttribute("formatter", formatter);
        model.addAttribute("newFormCount", newFormCount);
        return "PublicProfile";
    }
    
    //navigation to yourTasksPublic page
    @GetMapping("/yourTasksPublic/{userId}")
    public String yourTasksPublic(@PathVariable("userId") String userId, 
					    		@RequestParam(name = "sort", required = false) Boolean sortAscending,
					            @RequestParam(name = "sortBy", required = false, defaultValue = "newest") String sortBy,
					    		@RequestParam(name = "page", required = false, defaultValue = "1") int page,
					    	    @RequestParam(name = "size", required = false, defaultValue = "5") int size,
					    	    @RequestParam(name = "categories", required = false) List<String> categoryIds,
		                        @RequestParam(name = "statuses", required = false) List<String> statuses,
		                        @RequestParam(name = "formType", defaultValue = "all") String formType, 
		                        @AuthenticationPrincipal Object principal, 
		                        HttpSession session,
		                        HttpServletRequest request,
		                        Model model) {
        
    	User userss = userService.getUserByEmail(principal);
    	
    	//fetch user by userId
        User user = userService.getUserById(userId);
        if (statuses == null) {
            statuses = new ArrayList<>();
        }
        //fetch tasks assigned to this user and sort by submissionDate
        List<Generalform> assignedForms = informationFormService.getAssignedFormsByUser(user.getId())
        		.stream()
        	    .sorted(Comparator.comparing(
        	        Generalform::getSubmissionDate,
        	        Comparator.nullsLast(Comparator.naturalOrder())
        	    ))
        	    .collect(Collectors.toList());
        
        //filter tasks based on user assignment
        assignedForms.forEach(generalForm -> {
            List<Informationform> assignedInformationForms = generalForm.getInformationForms().stream()
                .filter(informationForm -> informationForm.getAssignedUser() != null && 
                    informationForm.getAssignedUser().getId().equals(user.getId()))
                .collect(Collectors.toList());
            generalForm.setInformationForms(assignedInformationForms); 
        });
        
        List<User> users = userService.getAllUsers();
        
        //fetch assigned booking forms
        List<Generalform> assignedBookingForms = bookingFormService.getAssignedFormsByUserBooking(user.getId());

        //combine information forms and booking forms
        List<Generalform> allAssignedForms = new ArrayList<>();
        allAssignedForms.addAll(assignedForms);
        allAssignedForms.addAll(assignedBookingForms);
        
        allAssignedForms = FilterService.filterFormsByCategories(allAssignedForms, categoryIds);
        
        allAssignedForms = FilterService.filterFormsByStatuses(allAssignedForms, statuses);
        
        //apply form type filter (either Information Form or Booking Form)
        if ("informationForm".equals(formType)) {
        	allAssignedForms = FilterService.filterByInformationForm(allAssignedForms);
        } else if ("bookingForm".equals(formType)) {
        	allAssignedForms = FilterService.filterByBookingForm(allAssignedForms);
        }
        
        switch (sortBy) {
		    case "surnameAsc":
		    	allAssignedForms = FilterService.sortBySurname(allAssignedForms, true);
		        break;
		    case "surnameDesc":
		    	allAssignedForms = FilterService.sortBySurname(allAssignedForms, false);
		        break;
		    case "newest":
		    	allAssignedForms = FilterService.sortBySubmissionDate(allAssignedForms, false);
		        break;
		    case "oldest":
		    	allAssignedForms = FilterService.sortBySubmissionDate(allAssignedForms, true);
		        break;
		    default:
		    	allAssignedForms = FilterService.sortBySubmissionDate(allAssignedForms, false);
		}

        //if no tasks are assigned, add a "noResults" message to the model
        if (allAssignedForms.isEmpty()) {
            model.addAttribute("noResults", "Non ci sono task assegnati a questo utente.");
        }
        
        //pagination logic: Get the total number of tasks
        int totalForms = allAssignedForms.size();
        
        //calculate total pages
        int totalPages = FilterService.getTotalPages(allAssignedForms, size);
        
        //ensure the current page is within the valid range
        if (page < 1) {
            page = 1;
        } else if (page > totalPages) {
            page = totalPages;
        }

        //paginate the list of assigned forms
        List<Generalform> paginatedAssignedForms = FilterService.getPaginatedList(allAssignedForms, page, size);
        List<Category> categories = categoryDAO.findAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        
        List<Generalform> generalForms = generalFormDAO.findAll();
    	
    	AtomicReference<LocalDateTime> lastSeenRef = new AtomicReference<>(LocalDateTime.now().minusDays(30));
        
        // Read cookies and get the "lastSeen" timestamp
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("lastSeen".equals(cookie.getName())) {
                    try {
                        String lastSeenStr = cookie.getValue();
                        DateTimeFormatter timeFormatter = DateTimeFormatter.ISO_DATE_TIME;
                        lastSeenRef.set(LocalDateTime.parse(lastSeenStr, formatter));
                    } catch (Exception e) {
                        // Error parsing cookie
                        System.err.println("Error parsing lastSeen cookie: " + e.getMessage());
                    }
                }
            }
        }
        
        LocalDateTime lastSeen = userss.getLastSeen() != null ? userss.getLastSeen() : LocalDateTime.now().minusDays(30);  // Use the user's own lastSeen value
        
        // Calculate the new form count based on lastSeen for the specific user
        long newFormCount = generalForms.stream()
            .filter(generalForm -> generalForm.getSubmissionDate() != null &&
                generalForm.getSubmissionDate().isAfter(lastSeen))
            .count();
            
        // Set session attribute to reset new form count
        session.setAttribute("newFormCount", newFormCount);
        
        //add data to the model
        model.addAttribute("assignedForms", paginatedAssignedForms);
        model.addAttribute("user", user); 
        model.addAttribute("users", users);
        model.addAttribute("userName", user.getName());
        model.addAttribute("userSurname", user.getSurname());
        model.addAttribute("sort", sortAscending);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalForms", totalForms);
        model.addAttribute("categoriesSelected", categoryIds != null ? categoryIds : List.of());
	    model.addAttribute("statusesSelected", statuses);
	    model.addAttribute("categories", categories);
	    model.addAttribute("formatter", formatter);
	    model.addAttribute("formType", formType);
	    model.addAttribute("newFormCount", newFormCount);
        return "YourTasksPublic"; 
    }
    
    //process to change the status of a form
    @PostMapping("/assignStatusPublicProfile")
	public String assignStatusPublicProfile(
			@RequestParam("userId") String userId,
			@RequestParam("informationFormId") String informationFormId,
			@RequestParam("informationFormStatus") String status,
			RedirectAttributes redirectAttributes) {
    	
    	try {
    		
		    //fetch the Informationform using the ID
		    Informationform informationForm = informationFormDAO.findById(informationFormId)
		        .orElseThrow(() -> new RuntimeException("InformationForm not found"));
	
		    //update the status of the Informationform
		    informationForm.setStatus(status);
		    
		    //save the updated Informationform
		    informationFormDAO.save(informationForm);
	
		 // Add success message to redirect attributes
	        redirectAttributes.addFlashAttribute("success", "Stato del task modificato con successo!");
	
	    } catch (Exception e) {
	        
	    	// Add error message to redirect attributes
	        redirectAttributes.addFlashAttribute("error", "Errore durante la modifica dello stato del task, riprova.");
	    }
	    //redirect back to the profile page
	    return "redirect:/admin/yourTasksPublic/" + userId;
	}
    
    //delete User
    @GetMapping("/deleteUser")
    public String confirmDeleteUser(@RequestParam String userId, Model model, @AuthenticationPrincipal Object principal, HttpSession session,
            HttpServletRequest request) {
        
    	User users = userService.getUserByEmail(principal);
    	User user = userService.getUserById(userId);

        //fetch tasks assigned to this user and sort by submissionDate
    	List<Generalform> assignedFormsFromInformation = informationFormService.getAssignedFormsByUser(user.getId())
    	        .stream()
    	        .sorted(Comparator.comparing(Generalform::getSubmissionDate, Comparator.nullsLast(Comparator.naturalOrder())))
    	        .collect(Collectors.toList());
    	
    	List<Generalform> assignedFormsFromBooking = bookingFormService.getAssignedFormsByUserBooking(user.getId())
    	        .stream()
    	        .sorted(Comparator.comparing(Generalform::getSubmissionDate, Comparator.nullsLast(Comparator.naturalOrder())))
    	        .collect(Collectors.toList());
    	
    	// Combine both lists to form a complete list of assigned Generalforms
    	List<Generalform> assignedForms = new ArrayList<>();
        assignedForms.addAll(assignedFormsFromInformation);
        assignedForms.addAll(assignedFormsFromBooking);
        
    	    //filter tasks with "TO DO" or "IN PROGRESS" statuses
    	    List<Generalform> filteredForms = assignedForms.stream()
    	        .filter(generalForm -> {
    	            
    	        	//filter the associated information forms with relevant statuses
    	            List<Informationform> filteredInformationForms = generalForm.getInformationForms().stream()
    	                .filter(informationForm -> 
    	                    informationForm.getAssignedUser() != null &&
    	                    informationForm.getAssignedUser().getId().equals(user.getId()) &&
    	                    (informationForm.getStatus().equalsIgnoreCase("TO DO") || 
    	                     informationForm.getStatus().equalsIgnoreCase("TO_DO") || 		
    	                     informationForm.getStatus().equalsIgnoreCase("IN PROGRESS"))
    	                )
    	                .collect(Collectors.toList());

    	            //filter the associated booking forms with relevant statuses
    	            List<BookingForm> filteredBookingForms = generalForm.getBookingForms().stream()
    	                .filter(bookingForm -> 
    	                    bookingForm.getAssignedUser() != null &&
    	                    bookingForm.getAssignedUser().getId().equals(user.getId()) &&
    	                    (bookingForm.getStatus().equalsIgnoreCase("TO DO") || 
    	                     bookingForm.getStatus().equalsIgnoreCase("IN PROGRESS"))
    	                )
    	                .collect(Collectors.toList());
    	            
    	            generalForm.setInformationForms(filteredInformationForms);
    	            generalForm.setBookingForms(filteredBookingForms);

    	            //only keep general forms that have at least one relevant information form or booking form
    	            return !filteredInformationForms.isEmpty() || !filteredBookingForms.isEmpty();
    	        })
    	        .collect(Collectors.toList());
    	    
    	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    	    
    	    List<Generalform> generalForms = generalFormDAO.findAll();
        	
        	AtomicReference<LocalDateTime> lastSeenRef = new AtomicReference<>(LocalDateTime.now().minusDays(30));
            
            // Read cookies and get the "lastSeen" timestamp
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("lastSeen".equals(cookie.getName())) {
                        try {
                            String lastSeenStr = cookie.getValue();
                            DateTimeFormatter timeFormatter = DateTimeFormatter.ISO_DATE_TIME;
                            lastSeenRef.set(LocalDateTime.parse(lastSeenStr, formatter));
                        } catch (Exception e) {
                            // Error parsing cookie
                            System.err.println("Error parsing lastSeen cookie: " + e.getMessage());
                        }
                    }
                }
            }
            
            LocalDateTime lastSeen = users.getLastSeen() != null ? users.getLastSeen() : LocalDateTime.now().minusDays(30);  // Use the user's own lastSeen value
            
            // Calculate the new form count based on lastSeen for the specific user
            long newFormCount = generalForms.stream()
                .filter(generalForm -> generalForm.getSubmissionDate() != null &&
                    generalForm.getSubmissionDate().isAfter(lastSeen))
                .count();
                
            // Set session attribute to reset new form count
            session.setAttribute("newFormCount", newFormCount);
    	    
    	    model.addAttribute("assignedForms", filteredForms);
    	    model.addAttribute("user", user);
    	    model.addAttribute("formatter", formatter);
    	    model.addAttribute("newFormCount", newFormCount);

        //return the confirmation page
        return "DeleteUser"; 
    }
    
    //process to delete a user
    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam String userId,  RedirectAttributes redirectAttributes) {
        	
    	try {
    		userService.deleteUserById(userId);
    		
    		// Add success message to redirect attributes
            redirectAttributes.addFlashAttribute("success", "Utente eliminatocon successo!");
        } catch (Exception e) {
            // Add error message to redirect attributes if an exception occurs
            redirectAttributes.addFlashAttribute("error", "Errore durante la registrazione, riprova.");
        }
    	
        //redirect back to the list of users after deletion
        return "redirect:/admin/listUsers"; 
    }
    
    //this is a GET request to "/register" where we load the registration form
    @GetMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public String registerPage(@AuthenticationPrincipal Object principal, Model model, HttpSession session, HttpServletRequest request) {
    	
    	User user = userService.getUserByEmail(principal);
    	
    	List<Generalform> generalForms = generalFormDAO.findAll();
        
        AtomicReference<LocalDateTime> lastSeenRef = new AtomicReference<>(LocalDateTime.now().minusDays(30));
        
        // Read cookies and get the "lastSeen" timestamp
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("lastSeen".equals(cookie.getName())) {
                    try {
                        String lastSeenStr = cookie.getValue();
                        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
                        lastSeenRef.set(LocalDateTime.parse(lastSeenStr, formatter));
                    } catch (Exception e) {
                        // Error parsing cookie
                        System.err.println("Error parsing lastSeen cookie: " + e.getMessage());
                    }
                }
            }
        }
        
        LocalDateTime lastSeen = user.getLastSeen() != null ? user.getLastSeen() : LocalDateTime.now().minusDays(30);  // Use the user's own lastSeen value
        
        // Calculate the new form count based on lastSeen for the specific user
        long newFormCount = generalForms.stream()
            .filter(generalForm -> generalForm.getSubmissionDate() != null &&
                generalForm.getSubmissionDate().isAfter(lastSeen))
            .count();
            
        // Set session attribute to reset new form count
        session.setAttribute("newFormCount", newFormCount);
        
        model.addAttribute("newFormCount", newFormCount);
        model.addAttribute("user", new User());
        return "UserRegister"; 
    }

    //handle User Registration (POST request)
    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public String registerUser(@ModelAttribute User user, Model model, RedirectAttributes redirectAttributes) {
    	
        //check if the email is already registered
        boolean userExists = userService.isEmailAlreadyInUse(user);
        if (userExists) {
        	redirectAttributes.addFlashAttribute("error", "Questa mail è già stata usate per un account.");
            return "UserRegister";  
        }

        //validate email format
        if (!isValidEmail(user.getEmail())) {
        	redirectAttributes.addFlashAttribute("error", "Formato della mail invalido.");
            return "UserRegister";  
        }

        //validate password format
        if (!isValidPassword(user.getPassword())) {
        	redirectAttributes.addFlashAttribute("error", "Password must be at least 8 characters long, contain an uppercase letter, a number, and a special character.");
            return "UserRegister";  
        }
        
        try {
        	
            //register the new user
            userService.register(user);
            redirectAttributes.addFlashAttribute("success", "Registrazione effettuata con successo!");
            
            //redirect to profile page after success
            return "redirect:/profile";  
        } catch (Exception e) {
        	redirectAttributes.addFlashAttribute("error", "Errore durante la registrazione, riprova.");
            
            //stay on the register page if there is an error
            return "UserRegister";  
        }
    }
    
    //method to validate password using regex
    private boolean isValidPassword(String password) {
        Pattern pattern = Pattern.compile(REGEX_PASSWORD);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches(); 
    }

    //method to validate email using regex
    private boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(REGEX_EMAIL);  
        Matcher matcher = pattern.matcher(email); 
        return matcher.matches(); 
    }
}
