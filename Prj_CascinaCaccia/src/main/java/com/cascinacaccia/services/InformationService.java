package com.cascinacaccia.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;

@Service
public class InformationService {

	@Autowired
    private JavaMailSender mailSender;
	
	//this method sends an email to the company when a new form is submitted
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
    
    //this method sends a confirmation email to the user after submitting the form
    public void sendConfirmationEmail(String userEmail) throws MessagingException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userEmail);
        message.setSubject("Form Submitted Successfully");
        message.setText("Thank you for your submission! We have received your form.");
        mailSender.send(message);
    }
}
