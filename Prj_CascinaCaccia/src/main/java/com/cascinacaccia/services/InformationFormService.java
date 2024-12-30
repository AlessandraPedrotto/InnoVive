package com.cascinacaccia.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.cascinacaccia.entities.Informationform;
import com.cascinacaccia.entities.User;
import com.cascinacaccia.repos.InformationformDAO;

import jakarta.mail.MessagingException;

@Service
/*
 * InformationFormService handles the business logic related to information forms.
 * This includes:
 * - Sending emails to the admin and user when a new form is submitted.
 * - Assigning and removing users to/from information forms.
 * - Updating the status of information forms.
 */
public class InformationFormService {

	@Autowired
    private JavaMailSender mailSender;
	@Autowired
	private InformationformDAO informationFormDAO;
	@Autowired
	private UserService userService;
	
	//method that sends an email to the company when a new form is submitted
    public void sendEmailToAdmin(String adminEmail, String name, String surname, String email, String categoryName, String content) throws MessagingException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(adminEmail);
        message.setSubject("New Form Submission");

        String emailContent = "General Form Details:\n" +
                "Name: " + name + "\n" +
                "Surname: " + surname + "\n" +
                "Email: " + email + "\n" +
                "Category: " + categoryName + "\n\n" +
                "Message: " + content;

        message.setText(emailContent);
        mailSender.send(message);
    }
    
    //method that sends a confirmation email to the user after submitting the form
    public void sendConfirmationEmail(String userEmail) throws MessagingException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userEmail);
        message.setSubject("Form Submitted Successfully");
        message.setText("Thank you for your submission! We have received your form.");
        mailSender.send(message);
    }
    
    //method to assign a user to a task (form)
    public void assignUser(String userId, String informationFormId) {
    	
    	Optional<Informationform> informationForm = informationFormDAO.findById(informationFormId);
    	if(informationForm.isPresent()) {
    		Informationform informationFormFound = informationForm.get();
    		User user = userService.getUserById(userId);
    		informationFormFound.setAssignedUser(user);
    		informationFormDAO.save(informationFormFound);
        } else {
            throw new RuntimeException("Information Form not found with ID: " + informationFormId);
        }
    }
    
    //method to remove a user from a specific information form
    public void removeUser(String userId, String informationFormId) {
        Optional<Informationform> informationForm = informationFormDAO.findById(informationFormId);
        
        if (informationForm.isPresent()) {
            Informationform informationFormFound = informationForm.get();
            User user = userService.getUserById(userId);
            
            // Check if the user is already assigned to this form
            if (informationFormFound.getAssignedUser() != null && informationFormFound.getAssignedUser().equals(user)) {
                
            	// Remove the user from the assignedUser field
                informationFormFound.setAssignedUser(null); 
                informationFormDAO.save(informationFormFound); 
            } else {
                throw new RuntimeException("User is not assigned to this Information Form.");
            }
        } else {
            throw new RuntimeException("Information Form not found with ID: " + informationFormId);
        }
    }
    
    //method to assign a status to a task (form)
    public void assignStatus(String status, String informationFormId) {
    	Optional<Informationform> informationForm = informationFormDAO.findById(informationFormId);
    	if(informationForm.isPresent()) {
    		Informationform informationFormFound = informationForm.get();
    		informationFormFound.setStatus(status);
    		informationFormDAO.save(informationFormFound);
        } else {
            throw new RuntimeException("Information Form not found with ID: " + informationFormId);
        }
    }
}
