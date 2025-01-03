package com.cascinacaccia.controllers;

import java.util.List;
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
        RedirectAttributes redirectAttributes) {

        try {
        	
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

            //create InformationForm
            Informationform informationForm = new Informationform(UUID.randomUUID().toString(), generalform, content);
            informationForm.setStatus("TO DO");
            informationFormDAO.save(informationForm);

            //send Emails via service
            informationFormService.sendEmailToAdmin("innovive2024@gmail.com", generalform.getName(), generalform.getSurname(), generalform.getEmail(), categoryName, informationForm.getContent());
            informationFormService.sendConfirmationEmail(email, name, surname, email, categoryName, content);

            redirectAttributes.addFlashAttribute("message", "Form submitted successfully!");
            return "redirect:/informationForm";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error processing form: " + e.getMessage());
            return "redirect:/informationForm";
        }
    }
    
    //assign a user to a task
    @PostMapping("/assignUser")
    public String assignUserToInformation(@RequestParam String informationFormId, @RequestParam String userId, RedirectAttributes redirectAttributes) {
        try {
            informationFormService.assignUser(userId, informationFormId);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to assign user: " + e.getMessage());
            return "redirect:/request";
        }
        return "redirect:/request";
    }
    
    //remove a user from a task
    @PostMapping("/removeUserFromInformationForm")
    public String removeUserFromInformation(@RequestParam String informationFormId, @RequestParam String userId, RedirectAttributes redirectAttributes) {
        try {
            informationFormService.removeUser(userId, informationFormId);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to remove user: " + e.getMessage());
            return "redirect:/request";
        }
        redirectAttributes.addFlashAttribute("success", "User removed successfully.");
        return "redirect:/request";
    }
    
    //assign a status to a task
    @PostMapping("/assignStatus")
    public String assignStatusToInformation(@RequestParam String informationFormStatus, @RequestParam String informationFormId, RedirectAttributes redirectAttributes) {
        try {
            informationFormService.assignStatus(informationFormStatus, informationFormId);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to assign status: " + e.getMessage());
            return "redirect:/request";
        }
        return "redirect:/request";
    }
}