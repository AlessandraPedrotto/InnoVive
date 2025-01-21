package com.cascinacaccia.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cascinacaccia.entities.Category;
import com.cascinacaccia.entities.Generalform;
import com.cascinacaccia.entities.Informationform;
import com.cascinacaccia.repos.CategoryDAO;
import com.cascinacaccia.repos.GeneralformDAO;
import com.cascinacaccia.repos.InformationformDAO;
import com.cascinacaccia.services.InformationFormService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class InformationFormController {

    @Autowired
    private CategoryDAO categoryDAO;
    @Autowired
    private GeneralformDAO generalFormDAO;
    @Autowired
    private InformationformDAO informationFormDAO;
    @Autowired
    private InformationFormService informationFormService;
    
    //navigation to the page information form
    @GetMapping("/informationForm")
    public String showForm(Model model) {
        model.addAttribute("categories", categoryDAO.findAll());
        model.addAttribute("categoryId", "");
        return "InformationForm";
    }
    
    //submission of the information form
    @PostMapping("/submit-form")
    public String submitForm(
        @RequestParam String name, 
        @RequestParam String surname, 
        @RequestParam String email, 
        @RequestParam String categoryId,
        @RequestParam String content, 
        RedirectAttributes redirectAttributes, 
        HttpServletRequest request) {

        try {
        	
            //fetch Category
            Category category = categoryDAO.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId));
            
            //get the category name
            String categoryName = category.getName();
            
            Generalform generalform;

            //create a new Generalform if no match is found
            generalform = new Generalform(UUID.randomUUID().toString(), name, surname, email, category);
            generalFormDAO.save(generalform);
            
            //create InformationForm
            Informationform informationForm = new Informationform(UUID.randomUUID().toString(), generalform, content);
            informationForm.setStatus("TO DO");
            informationFormDAO.save(informationForm);

            //send Emails via service
            informationFormService.sendEmailToAdmin("innovive2024@gmail.com", generalform.getName(), generalform.getSurname(), generalform.getEmail(), categoryName, informationForm.getContent());
            informationFormService.sendConfirmationEmail(email, name, surname, email, categoryName, content);

            redirectAttributes.addFlashAttribute("success", "Modulo inviato con successo!");
            return "redirect:"+ request.getHeader("Referer");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Errore durante l'invio del modulo, prova ad inviarlo di nuovo.");
            return "redirect:"+ request.getHeader("Referer");
        }
    }
    
    //assign a user to a task
    @PostMapping("/assignUser")
    public String assignUserToInformation(@RequestParam String informationFormId, @RequestParam String userId, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        try {
            informationFormService.assignUser(userId, informationFormId);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Errore durante l'assegnazione del task, riprova.");
            return "redirect:"+ request.getHeader("Referer");
        }
        redirectAttributes.addFlashAttribute("success", "Task assegnato con successo!");
        return "redirect:"+ request.getHeader("Referer");
    }
    
    //remove a user from a task
    @PostMapping("/removeUserFromInformationForm")
    public String removeUserFromInformation(@RequestParam String informationFormId, @RequestParam String userId, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        try {
            informationFormService.removeUser(userId, informationFormId);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Errore durante la rimozione dell'utente dal task, riprova.");
            return "redirect:"+ request.getHeader("Referer");
        }
        redirectAttributes.addFlashAttribute("success", "L'utente è stato rimosso dal task con successo!");
        return "redirect:"+ request.getHeader("Referer");
    }
    
    //assign a status to a task
    @PostMapping("/assignStatus")
    public String assignStatusToInformation(@RequestParam String informationFormStatus, @RequestParam String informationFormId, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        try {
            informationFormService.assignStatus(informationFormStatus, informationFormId);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Errore durante la modifica dello stato del task, riprova.");
            return "redirect:"+ request.getHeader("Referer");
        }
        redirectAttributes.addFlashAttribute("success", "Stato del task modificato con successo!");
        return "redirect:"+ request.getHeader("Referer");
    }
}