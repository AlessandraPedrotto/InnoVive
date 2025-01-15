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

import jakarta.servlet.http.HttpServletRequest;

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
        	
        	//parse the input dates 
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

            redirectAttributes.addFlashAttribute("success", "Prenotazione inviata con successo!");
            return "redirect:/prenota";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Errore durante l'invio della prenotazione, prova ad inviarla di nuovo.");
            return "redirect:/prenota";
        }
    }
    
    //assign a user to a task
    @PostMapping("/assignUserBooking")
    public String assignUserToBooking(@RequestParam String bookingFormId, @RequestParam String userId, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        try {
            bookingFormService.assignUser(userId, bookingFormId);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Errore durante l'assegnazione del task, riprova.");
            return "redirect:"+ request.getHeader("Referer");
        }
        redirectAttributes.addFlashAttribute("success", "Task assegnato con successo!");
        return "redirect:"+ request.getHeader("Referer");
    }
    
    //remove a user from a task
    @PostMapping("/removeUserFromBookingForm")
    public String removeUserFromBooking(@RequestParam String bookingFormId, @RequestParam String userId, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        try {
        	bookingFormService.removeUser(userId, bookingFormId);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Errore durante la rimozione dell'utente dal task, riprova.");
            return "redirect:"+ request.getHeader("Referer");
        }
        redirectAttributes.addFlashAttribute("success", "L'utente è stato rimosso dal task con successo!");
        return "redirect:"+ request.getHeader("Referer");
    }
    
    //assign a status to a task
    @PostMapping("/assignStatusBooking")
    public String assignStatusToBooking(@RequestParam String bookingFormStatus, @RequestParam String bookingFormId, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        try {
        	bookingFormService.assignStatus(bookingFormStatus, bookingFormId);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Errore durante la modifica dello stato del task, riprova.");
            return "redirect:"+ request.getHeader("Referer");
        }
        redirectAttributes.addFlashAttribute("success", "Stato del task modificato con successo!");
        return "redirect:"+ request.getHeader("Referer");
    }
}
