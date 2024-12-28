package com.cascinacaccia.controllers;

import java.util.List;
import java.util.Optional;
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
import com.cascinacaccia.services.InformationService;

@Controller
public class FormController {

    @Autowired
    CategoryDAO categoryDAO;
    @Autowired
    GeneralformDAO generalFormDAO;
    @Autowired
    InformationformDAO informationFormDAO;
    @Autowired
    InformationService informationService;
    
    //navigation to the page information form
    @GetMapping("/informationForm")
    public String showForm(Model model) {
        model.addAttribute("categories", categoryDAO.findAll());
        model.addAttribute("categoryId", "");
        return "InformationForm";
    }

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
            
            //check for existing Generalform entries
            List<Generalform> existingForms = generalFormDAO.findByEmailAndCategoryAndNameAndSurname(email, category, name, surname);

            Generalform generalform;
            if (!existingForms.isEmpty()) {
                // Reuse the existing Generalform if found
                generalform = existingForms.get(0); // Get the first match
            } else {
                // Create a new Generalform if no match is found
                generalform = new Generalform(UUID.randomUUID().toString(), name, surname, email, category);
                generalFormDAO.save(generalform);
            }

            //create InformationForm
            Informationform informationForm = new Informationform(UUID.randomUUID().toString(), generalform, content);
            informationFormDAO.save(informationForm);

            //send Emails via service
            informationService.sendEmailToAdmin("innovive2024@gmail.com", generalform.getName(), generalform.getSurname(), generalform.getEmail(), generalform.getCategory().getName(), informationForm.getContent());
            informationService.sendConfirmationEmail(email);

            redirectAttributes.addFlashAttribute("message", "Form submitted successfully!");
            return "redirect:/informationForm";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error processing form: " + e.getMessage());
            return "redirect:/informationForm";
        }
    }
}