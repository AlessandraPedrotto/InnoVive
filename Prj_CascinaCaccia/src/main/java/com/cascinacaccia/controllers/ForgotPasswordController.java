package com.cascinacaccia.controllers;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cascinacaccia.entities.PasswordResetToken;
import com.cascinacaccia.entities.User;
import com.cascinacaccia.repos.CategoryDAO;
import com.cascinacaccia.repos.UserDAO;
import com.cascinacaccia.services.ForgotPasswordService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ForgotPasswordController {
	
	@Autowired
	private ForgotPasswordService forgotPasswordService;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private CategoryDAO categoryDAO;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private static final String REGEX_PASSWORD = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[\\p{Punct}])(?=\\S+$).{8,}$";
	
	//navigation to the page forgot password
    @GetMapping("/forgotPassword")
    public String showForgotPasswordPage(Model model,
    		@RequestParam(value = "lang", required = false) String lang) {
    	model.addAttribute("selectedLanguage", lang);
    	model.addAttribute("categories", categoryDAO.findAll());
        model.addAttribute("categoryId", "");
    	
        return "ForgotPassword"; 
    }
    
    //process to change the password forgotten
    @PostMapping("/forgotPassword")
    public String processForgotPassword(@RequestParam("email") String email,
    		@RequestParam(value = "lang", required = false) String lang,
    		Model model) {
    	
    	if (lang == null || lang.isEmpty()) {
            lang = "it"; // Default to Italian
        }
    	
        Optional<User> optionalUser = userDAO.findByEmail(email);
        model.addAttribute("selectedLanguage", lang);
        
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String userName = user.getName();
            String userSurname = user.getSurname(); 
            String result = forgotPasswordService.sendResetEmail(user, userName, userSurname); 
            
            if ("success".equals(result)) {
            	if (lang.equals("it")) {
                    model.addAttribute("success", "Email inviata con successo all'indirizzo " + email);
                } else {
                    model.addAttribute("success", "An email has been successfully sent to " + email);
                }
            } else {
            	if (lang.equals("it")) {
                    model.addAttribute("error", "Errore durante l'invio della mail, riprova.");
                } else {
                    model.addAttribute("error", "Error while sending the email, please try again.");
                }
            }
        } else {
        	if(lang.equals("it")) {
        		 model.addAttribute("error", "Non esiste nessun utente con questa mail.");
        	}else {
        		 model.addAttribute("error", "There is no user with this email.");
        	}
           
        }

        return "ForgotPassword"; 
    }
    
    //show the password reset form when the user clicks on the link
    @GetMapping("/resetPassword/{token}")
    public String showResetPasswordForm(@PathVariable String token, Model model, RedirectAttributes redirectAttributes,@RequestParam(value = "lang", required = false) String lang) {
    	
    	if (lang == null || lang.isEmpty()) {
	        lang = "it"; // Fallback to Italian
	    }
    	
    	model.addAttribute("selectedLanguage", lang);
    	
    	//check if the token is correctly passed
        System.out.println("Received token: " + token);  
        PasswordResetToken resetToken = forgotPasswordService.findResetTokenByToken(token);

        if (resetToken != null && !forgotPasswordService.hasExpired(resetToken.getExpiryDateTime())) {
            model.addAttribute("token", token);
            return "ResetPassword";
        }
        
        String errorMessage = (lang.equals("en")) ? "The password reset token was not found or has expired, please ask for one." : "Il token per il reset della password non è stato trovato o è scaduto, chiedine uno.";
        redirectAttributes.addFlashAttribute("error", errorMessage);
        return "redirect:/forgotPassword?error=true";
    }

    //process the password reset
    @PostMapping("/resetPassword")
    public String processResetPassword(@RequestParam("token") String token,
                                       @RequestParam("password") String password, 
                                       @RequestParam("confirmPassword") String confirmPassword,
                                       @RequestParam(value = "lang", required = false) String lang,
                                       HttpServletRequest request,
                                       RedirectAttributes redirectAttributes,
                                       Model model) {
    	
    	if (lang == null || lang.isEmpty()) {
            lang = "it";
        }
    	
    	model.addAttribute("selectedLanguage", lang);
    	
        //log the received token
        System.out.println("Received token in POST: " + token);
        
        //fetch the reset token from the database
        PasswordResetToken resetToken = forgotPasswordService.findResetTokenByToken(token);
        if (resetToken == null) {
            System.out.println("Token not found in POST request!");
            
            String errorMessage = (lang.equals("it")) ? "Il token per il reset della password non è stato trovato, chiedine uno." : "The reset password token was not found, request a new one.";
            model.addAttribute("error", errorMessage);
            
            return "redirect:/forgotPassword?error=true";
        }

        //log the expiry date of the token
        System.out.println("Token expiry date: " + resetToken.getExpiryDateTime());
        
        //check if the token has expired
        if (forgotPasswordService.hasExpired(resetToken.getExpiryDateTime())) {
            System.out.println("Token has expired.");
            
            String errorMessage = (lang.equals("it")) ? "Il token per il reset della password è scaduto, chiedine un altro." : "The reset password token has expired, request another one.";
            model.addAttribute("error", errorMessage);
            
            return "redirect:/forgotPassword?error=true";
        }

        //continue with the password reset process if the token is valid
        if (!password.equals(confirmPassword)) {
        	
        	String errorMessage = (lang.equals("it")) ? "Le due password non coincidono." : "The passwords do not match.";
            model.addAttribute("error", errorMessage);
            model.addAttribute("token", token);
            return "ResetPassword";
        }
        
        User user = resetToken.getUser();

        // Check if the new password is the same as the current one
        if (passwordEncoder.matches(password, user.getPassword())) {
        	
        	String errorMessage = (lang.equals("it")) ? "La nuova password non può essere uguale a quella vecchia." : "The new password cannot be the same as the old one.";
            model.addAttribute("error", errorMessage);
        	
        	model.addAttribute("token", token);
            return "ResetPassword";
        }
        
        if (!isValidPassword(confirmPassword)) {
        	
        	String errorMessage = (lang.equals("it")) ? "La password deve essere di almeno 8 caratteri, una lettera maiuscola, un numero e un carattere speciale." : "The password must be at least 8 characters, contain an uppercase letter, a number, and a special character.";
            model.addAttribute("error", errorMessage);
        	
        	model.addAttribute("token", token);
        	return "ResetPassword";
        }

        user.setPassword(passwordEncoder.encode(password));
        userDAO.save(user);

        String successMessage = (lang.equals("it")) ? "Password cambiata con successo!" : "Password changed successfully!";
        model.addAttribute("success", successMessage);
        return "redirect:/login?success=true";
    }
    
    //method to validate password using regex 
    private boolean isValidPassword(String password) {
        Pattern pattern = Pattern.compile(REGEX_PASSWORD);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches(); 
    }
}