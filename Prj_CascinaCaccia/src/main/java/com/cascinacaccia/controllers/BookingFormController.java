package com.cascinacaccia.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cascinacaccia.entities.BookingForm;
import com.cascinacaccia.entities.Category;
import com.cascinacaccia.entities.Generalform;
import com.cascinacaccia.entities.Informationform;
import com.cascinacaccia.repos.BookingFormDAO;
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
    private BookingFormDAO bookingFormDAO;
    
    @Autowired
    private BookingFormService bookingFormService;
    
    //navigation to the page booking form
    @GetMapping("/bookingForm")
    public String showForm(Model model) {
    	model.addAttribute("categories", categoryDAO.findAll());
        model.addAttribute("categoryId", "");
        return "BookingForm";
    }
    
    //submission of the information form
    @PostMapping("/submit-booking")
    public String submitForm(
        @RequestParam String name, 
        @RequestParam String surname, 
        @RequestParam String email, 
        @RequestParam String categoryId,
        @RequestParam String checkIn, 
        @RequestParam String checkOut,
        @RequestParam String content, 
        RedirectAttributes redirectAttributes) {

        try {
        	
        	// Parse the input dates 
        	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedCheckIn = dateFormat.parse(checkIn);
            Date parsedCheckOut = dateFormat.parse(checkOut);
            
            //fetch Category
            Category category = categoryDAO.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId));
            
            //get the category name
            String categoryName = category.getName();
            
            //check for existing Generalform entries
            List<Generalform> existingForms = generalFormDAO.findByEmailAndCategoryAndNameAndSurname(email, category, name, surname);

            Generalform generalform;
            if (!existingForms.isEmpty()) {
            	
                //reuse the existing Generalform if found
                generalform = existingForms.get(0); 
            } else {
            	
                //create a new Generalform if no match is found
                generalform = new Generalform(UUID.randomUUID().toString(), name, surname, email, category);
                generalFormDAO.save(generalform);
            }

            //create BookingForm
            BookingForm bookingForm = new BookingForm(UUID.randomUUID().toString(), generalform, content);
            bookingForm.setStatus("TO DO");
            bookingForm.setCheckIn(parsedCheckIn);  
            bookingForm.setCheckOut(parsedCheckOut);   
            bookingFormDAO.save(bookingForm);

            //send Emails via service
            bookingFormService.sendEmailToAdmin("innovive2024@gmail.com", generalform.getName(), generalform.getSurname(), generalform.getEmail(), categoryName, parsedCheckIn, parsedCheckOut, bookingForm.getContent());
            bookingFormService.sendConfirmationEmail(email, name, surname, email, categoryName, parsedCheckIn, parsedCheckOut, content);

            redirectAttributes.addFlashAttribute("message", "Form submitted successfully!");
            return "redirect:/bookingForm";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error processing form: " + e.getMessage());
            return "redirect:/bookingForm";
        }
    }
    
    //assign a user to a task
    @PostMapping("/assignUserBooking")
    public String assignUserToBooking(@RequestParam String bookingFormId, @RequestParam String userId, RedirectAttributes redirectAttributes) {
        try {
            bookingFormService.assignUser(userId, bookingFormId);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to assign user: " + e.getMessage());
            return "redirect:/request";
        }
        return "redirect:/request";
    }
    
    //remove a user from a task
    @PostMapping("/removeUserFromBookingForm")
    public String removeUserFromBooking(@RequestParam String bookingFormId, @RequestParam String userId, RedirectAttributes redirectAttributes) {
        try {
        	bookingFormService.removeUser(userId, bookingFormId);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to remove user: " + e.getMessage());
            return "redirect:/request";
        }
        redirectAttributes.addFlashAttribute("success", "User removed successfully.");
        return "redirect:/request";
    }
    
    //assign a status to a task
    @PostMapping("/assignStatusBooking")
    public String assignStatusToBooking(@RequestParam String bookingFormStatus, @RequestParam String bookingFormId, RedirectAttributes redirectAttributes) {
        try {
        	bookingFormService.assignStatus(bookingFormStatus, bookingFormId);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to assign status: " + e.getMessage());
            return "redirect:/request";
        }
        return "redirect:/request";
    }
}
