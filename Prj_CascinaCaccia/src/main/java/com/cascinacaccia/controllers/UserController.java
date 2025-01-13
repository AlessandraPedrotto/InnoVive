package com.cascinacaccia.controllers;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cascinacaccia.entities.BookingForm;
import com.cascinacaccia.entities.Category;
import com.cascinacaccia.entities.Generalform;
import com.cascinacaccia.entities.Informationform;
import com.cascinacaccia.entities.User;
import com.cascinacaccia.entities.UserImage;
import com.cascinacaccia.repos.BookingFormDAO;
import com.cascinacaccia.repos.CategoryDAO;
import com.cascinacaccia.repos.InformationformDAO;
import com.cascinacaccia.repos.UserImageDAO;
import com.cascinacaccia.services.BookingFormService;
import com.cascinacaccia.services.FilterService;
import com.cascinacaccia.services.InformationFormService;
import com.cascinacaccia.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private UserImageDAO userImageDAO;
	@Autowired
	private BookingFormDAO bookingFormDAO;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private InformationformDAO informationFormDAO;
	@Autowired
	private InformationFormService informationFormService;
	@Autowired
	private CategoryDAO categoryDAO;
	@Autowired
	private BookingFormService bookingFormService;
	
	private static final String REGEX_PASSWORD = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[\\p{Punct}])(?=\\S+$).{8,}$";
	
	//navigation to profile page
	@GetMapping("/profile")
	public String profilePage(@AuthenticationPrincipal Object principal, Model model) throws Exception {
	    
	    User user = userService.getUserByEmail(principal);

	    String profileImageUrl = user.getUserImage() != null ? user.getUserImage().getImgPath() : "/default-image.png";
	    
	    //fetch the assigned requests for the logged-in user
	    List<Generalform> assignedForms = informationFormService.getAssignedFormsByUser(user.getId());
	    
	    //filter the InformationForms to only include those assigned to the user
	    assignedForms.forEach(generalForm -> {
	        List<Informationform> assignedInformationForms = generalForm.getInformationForms().stream()
	            .filter(informationForm -> informationForm.getAssignedUser() != null && 
	                informationForm.getAssignedUser().getId().equals(user.getId()))
	            .collect(Collectors.toList());
	        generalForm.setInformationForms(assignedInformationForms); 
	    });
	    
	    //pass the user's name, surname and email to the model
	    model.addAttribute("fullName", user.getName()); //add name
	    model.addAttribute("email", user.getEmail());  //add email
	    model.addAttribute("profileImageUrl", profileImageUrl); //add image
	    model.addAttribute("surname", user.getSurname()); //add surname
	    model.addAttribute("assignedForms", assignedForms);
	    return "profile";
	}
	
	@PostMapping("/set-user-online")
	public ResponseEntity<Void> setUserOnline(HttpSession session) {
	    String email = (String) session.getAttribute("email");
	    if (email != null) {
	        userService.updateUserStateAndLastAccess(email, "ONLINE");
	    }
	    return ResponseEntity.ok().build();
	}

	@PostMapping("/set-user-offline")
	public ResponseEntity<Void> setUserOffline(HttpSession session) {
	    String email = (String) session.getAttribute("email");
	    if (email != null) {
	        System.out.println("Received offline request for email: " + email); // Debug log
	        userService.updateUserStateAndLastAccess(email, "OFFLINE");
	    } else {
	        System.out.println("No email found in session. Cannot set user offline."); // Debug log
	    }
	    return ResponseEntity.ok().build();
	}
	
	@PostMapping("/profile/update-name")
    public String updateNameAndSurname(@AuthenticationPrincipal Object principal, 
                                       @RequestParam("name") String name, 
                                       @RequestParam("surname") String surname) {

        // Get the logged-in user's email or ID
        User user = userService.getUserByEmail(principal);

        // Update the user's name and surname
        userService.updateNameAndSurname(user.getId(), name, surname);

        return "redirect:/profile"; // Redirect back to the profile page
    }
	
	//navigation to yourTasks page
	@GetMapping("/yourTasks")
	public String userTasks(@AuthenticationPrincipal Object principal,
						@RequestParam(name = "sort", required = false) Boolean sortAscending,
			            @RequestParam(name = "sortBy", required = false, defaultValue = "newest") String sortBy,
						@RequestParam(name = "page", required = false, defaultValue = "1") int page,
			            @RequestParam(name = "size", required = false, defaultValue = "5") int size,
			            @RequestParam(name = "categories", required = false) List<String> categoryIds,
                        @RequestParam(name = "statuses", required = false) List<String> statuses,
                        @RequestParam(name = "formType", defaultValue = "all") String formType, 
                        Model model) throws Exception{
		
		User user = userService.getUserByEmail(principal);
		if (statuses == null) {
            statuses = new ArrayList<>();
        }
		//fetch the assigned requests for the logged-in user
	    List<Generalform> assignedForms = informationFormService.getAssignedFormsByUser(user.getId())
	    		.stream()
	    	    .sorted(Comparator.comparing(
	    	        Generalform::getSubmissionDate,
	    	        Comparator.nullsLast(Comparator.naturalOrder())
	    	    ))
	    	    .collect(Collectors.toList());
		    
	    //filter the InformationForms to only include those assigned to the user
	    assignedForms.forEach(generalForm -> {
	        List<Informationform> assignedInformationForms = generalForm.getInformationForms().stream()
	            .filter(informationForm -> informationForm.getAssignedUser() != null && 
	                informationForm.getAssignedUser().getId().equals(user.getId()))
	            .collect(Collectors.toList());
	        generalForm.setInformationForms(assignedInformationForms); 
	    });
	    
	    List<Generalform> assignedBookingForms = bookingFormService.getAssignedFormsByUserBooking(user.getId());
	    
	    List<Generalform> allAssignedForms = new ArrayList<>();
	    allAssignedForms.addAll(assignedForms);
	    allAssignedForms.addAll(assignedBookingForms);
	    
	    //filter by selected categories if available
	    allAssignedForms = FilterService.filterFormsByCategories(allAssignedForms, categoryIds);
        
	    allAssignedForms = FilterService.filterFormsByStatuses(allAssignedForms, statuses);
        
	    //apply form type filter (either Information Form or Booking Form)
        if ("informationForm".equals(formType)) {
        	allAssignedForms = FilterService.filterByInformationForm(allAssignedForms);
        } else if ("bookingForm".equals(formType)) {
        	allAssignedForms = FilterService.filterByBookingForm(allAssignedForms);
        }
        
	    //sort forms based on the selected option
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
	    
	    //if no forms are assigned, add a "noResults" message to the model
	    if (allAssignedForms.isEmpty()) {
	        model.addAttribute("noResults", "No tasks assigned to you.");
	    }
	    
	    //pagination logic: Get the total number of forms
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
	    List<Generalform> paginatedForms = FilterService.getPaginatedList(allAssignedForms, page, size);
	    List<Category> categories = categoryDAO.findAll();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	    
	    model.addAttribute("totalPages", totalPages); 
	    model.addAttribute("currentPage", page);
	    model.addAttribute("totalForms", totalForms);
	    model.addAttribute("sort", sortAscending);
        model.addAttribute("sortBy", sortBy);
	    model.addAttribute("assignedForms", paginatedForms);
	    model.addAttribute("categoriesSelected", categoryIds != null ? categoryIds : List.of());
	    model.addAttribute("statusesSelected", statuses);
	    model.addAttribute("categories", categories);
	    model.addAttribute("assignedBookingForms", assignedBookingForms); 
	    model.addAttribute("formatter", formatter);
	    model.addAttribute("formType", formType);
	    return "YourTasks";
	}
	
	//process to change the status of a Informationform
	@PostMapping("/assignStatusProfile")
	public String assignStatus(@RequestParam("informationFormId") String informationFormId,
	                            @RequestParam("informationFormStatus") String status) {
	    
		//fetch the Informationform using the ID
	    Informationform informationForm = informationFormDAO.findById(informationFormId)
	        .orElseThrow(() -> new RuntimeException("InformationForm not found"));

	    //update the status of the Informationform
	    informationForm.setStatus(status);
	    
	    //save the updated Informationform
	    informationFormDAO.save(informationForm);

	    //redirect back to the profile page
	    return "redirect:/yourTasks";
	}
	
	//process to change the status of a BookingForm
	@PostMapping("/assignStatusBookingProfile")
	public String assignStatusBooking(@RequestParam("bookingFormId") String bookingFormId,
	                            @RequestParam("bookingFormStatus") String status) {
	    
		//fetch the BookingForm using the ID
	    BookingForm bookingForm = bookingFormDAO.findById(bookingFormId)
	        .orElseThrow(() -> new RuntimeException("BookingForm not found"));

	    //update the status of the BookingForm
	    bookingForm.setStatus(status);
	    
	    //save the updated BookingForm
	    bookingFormDAO.save(bookingForm);

	    //redirect back to the profile page
	    return "redirect:/yourTasks";
	}
	
	//navigation to change password page
	@GetMapping("/changePassword")
    public String changePasswordView(@AuthenticationPrincipal Object principal, Model model) {
    	User user = userService.getUserByEmail(principal);
        if(user.getPassword()!=null) {
        	model.addAttribute("passNULL",false);
        }
        else model.addAttribute("passNULL",true);
        
        return "ChangePassword"; 
    }
    
	//process to change the password
    @PostMapping("/changePassword")
    public String changePassword(@AuthenticationPrincipal Object principal,
                                 @RequestParam Map<String, String> formData, 
                                 RedirectAttributes redirectAttributes,
                                 HttpSession session) {  

        User user = userService.getUserByEmail(principal);

        try {
            String oldPassword = formData.get("oldPassword");
            String newPassword = formData.get("newPassword");
            String confirmPassword = formData.get("confirmPassword");
            
            //check if the new password matches the old password
            if (passwordEncoder.matches(newPassword, user.getPassword())) {
                redirectAttributes.addFlashAttribute("error", "New password cannot be the same as the old password.");
                return "redirect:/changePassword";
            }
            
            //check if the new password matches the old password
            if (passwordEncoder.matches(newPassword, user.getPassword())) {
                redirectAttributes.addFlashAttribute("error", "New password cannot be the same as the old password.");
                return "redirect:/changePassword";
            }
            
            //check if the new password entered is correct
            if (!newPassword.equals(confirmPassword)) {
                redirectAttributes.addFlashAttribute("error", "New passwords do not match.");
                return "redirect:/changePassword";
            }
            
            if (!isValidPassword(newPassword)) {
                redirectAttributes.addFlashAttribute("error", "Password must be at least 8 characters long, contain an uppercase letter, a number, and a special character.");
                return "redirect:/changePassword";
            }
            
            userService.changePassword(user.getId(), oldPassword, newPassword);

            session.invalidate(); 
            
            redirectAttributes.addFlashAttribute("success", "Password change successful.");
            
            //redirect to the login page to request re-authentication
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/changePassword";
        }
    }
    
    //method to validate password using regex 
    private boolean isValidPassword(String password) {
        Pattern pattern = Pattern.compile(REGEX_PASSWORD);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches(); 
    }
    
    //navigation to profile image page
    @GetMapping("/profileImage")
    public String assignProfileImagePage(Model model, @AuthenticationPrincipal Object principal) {
        User user = userService.getUserByEmail(principal);

        //ensure user is added to the model
        model.addAttribute("user", user);

        //fetch all images from the database
        List<UserImage> allUserImages = userImageDAO.findAll();
        model.addAttribute("allUserImages", allUserImages);

        return "ProfileImage";
    }
    
    //assign or update profile image
    @PostMapping("/profileImage")
    public String assignProfileImage(@RequestParam String userId, @RequestParam Long userImageId, Model model) {
        try {
            userService.assignOrUpdateProfileImage(userId, userImageId);
            model.addAttribute("message", "Profile image updated successfully!");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/profile";
    }
}
