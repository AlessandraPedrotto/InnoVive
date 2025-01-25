package com.cascinacaccia.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cascinacaccia.entities.BookingForm;
import com.cascinacaccia.entities.Category;
import com.cascinacaccia.entities.Generalform;
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
    @Autowired
    private LocaleResolver localeResolver;
    @Autowired
    private MessageSource messageSource;
    
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
        RedirectAttributes redirectAttributes,
        HttpServletRequest request,
        @RequestParam(value = "lang", required = false) String lang,
    	Model model){

    	if (lang == null || lang.isEmpty()) {
            lang = "it"; // Default to Italian
        }
    	
    	model.addAttribute("selectedLanguage", lang);

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
          
            Generalform generalform;	
            
            //create a new Generalform if no match is found
            generalform = new Generalform(UUID.randomUUID().toString(), name, surname, email, category);
            generalFormDAO.save(generalform);

            //create BookingForm
            BookingForm bookingForm = new BookingForm(UUID.randomUUID().toString(), generalform, content);
            bookingForm.setStatus("TO DO");
            bookingForm.setCheckIn(parsedCheckIn);  
            bookingForm.setCheckOut(parsedCheckOut);   
            bookingFormDAO.save(bookingForm);

            //send Emails via service
            bookingFormService.sendEmailToAdmin("innovive2024@gmail.com", generalform.getName(), generalform.getSurname(), generalform.getEmail(), categoryName, parsedCheckIn, parsedCheckOut, bookingForm.getContent());
            bookingFormService.sendConfirmationEmail(email, name, surname, email, categoryName, parsedCheckIn, parsedCheckOut, content);

            String successMessage = (lang.equals("en")) ? "Form submitted successfully!" : "Modulo inviato con successo!";
            redirectAttributes.addFlashAttribute("success", successMessage);
            String referer = request.getHeader("Referer");
            return "redirect:" + (referer != null ? referer : "/") + "?lang=" + lang;

        } catch (Exception e) {
        	String errorMessage = (lang.equals("en")) ? "Error during form submission, please try again." : "Errore durante l'invio del modulo, prova ad inviarlo di nuovo.";
            redirectAttributes.addFlashAttribute("error", errorMessage);
            String referer = request.getHeader("Referer");
            return "redirect:" + (referer != null ? referer : "/") + "?lang=" + lang;
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
