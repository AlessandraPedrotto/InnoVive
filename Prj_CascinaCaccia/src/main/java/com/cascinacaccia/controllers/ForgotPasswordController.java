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

import com.cascinacaccia.entities.PasswordResetToken;
import com.cascinacaccia.entities.User;
import com.cascinacaccia.repos.CategoryDAO;
import com.cascinacaccia.repos.UserDAO;
import com.cascinacaccia.services.ForgotPasswordService;

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
    public String showForgotPasswordPage(Model model) {
    	model.addAttribute("categories", categoryDAO.findAll());
        model.addAttribute("categoryId", "");
    	
        return "ForgotPassword"; 
    }
    
    //process to change the password forgotten
    @PostMapping("/forgotPassword")
    public String processForgotPassword(@RequestParam("email") String email, Model model) {
        Optional<User> optionalUser = userDAO.findByEmail(email);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String userName = user.getName();
            String userSurname = user.getSurname(); 
            String result = forgotPasswordService.sendResetEmail(user, userName, userSurname); 
            if ("success".equals(result)) {
                model.addAttribute("success", "Email inviata con successo all'indirizzo " + email);
            } else {
                model.addAttribute("error", "Errore durante l'invio della mail, riprova.");
            }
        } else {
            model.addAttribute("error", "Non esiste nessun utente con questa mail.");
        }

        return "ForgotPassword"; 
    }
    
    //show the password reset form when the user clicks on the link
    @GetMapping("/resetPassword/{token}")
    public String showResetPasswordForm(@PathVariable String token, Model model) {
    	
    	//check if the token is correctly passed
        System.out.println("Received token: " + token);  
        PasswordResetToken resetToken = forgotPasswordService.findResetTokenByToken(token);

        if (resetToken != null && !forgotPasswordService.hasExpired(resetToken.getExpiryDateTime())) {
            model.addAttribute("token", token);
            return "ResetPassword";
        }

        return "redirect:/?error=expired";
    }

    //process the password reset
    @PostMapping("/resetPassword")
    public String processResetPassword(@RequestParam("token") String token,
                                       @RequestParam("password") String password, 
                                       @RequestParam("confirmPassword") String confirmPassword,
                                       Model model) {
        //log the received token
        System.out.println("Received token in POST: " + token);
        
        //fetch the reset token from the database
        PasswordResetToken resetToken = forgotPasswordService.findResetTokenByToken(token);
        if (resetToken == null) {
            System.out.println("Token not found in POST request!");
            model.addAttribute("error", "Il token per il reset della password non è stato trovato, chiedine uno.");
            return "redirect:/?error=invalidToken";
        }

        //log the expiry date of the token
        System.out.println("Token expiry date: " + resetToken.getExpiryDateTime());
        
        //check if the token has expired
        if (forgotPasswordService.hasExpired(resetToken.getExpiryDateTime())) {
            System.out.println("Token has expired.");
            model.addAttribute("error", "Il token per il reset della password è scaduto, chiedine un altro.");
            return "redirect:/?error=expired";
        }

        //continue with the password reset process if the token is valid
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Le due password non coincidono.");
            model.addAttribute("token", token);
            return "ResetPassword";
        }
        
        User user = resetToken.getUser();

        // Check if the new password is the same as the current one
        if (passwordEncoder.matches(password, user.getPassword())) {
            model.addAttribute("error", "La nuova password non può essere uguale a quella vecchia.");
            model.addAttribute("token", token);
            return "ResetPassword";
        }
        
        if (!isValidPassword(confirmPassword)) {
        	model.addAttribute("error", "La password deve essere di almeno 8 caratteri, una lettera maiuscola, un numero e un carattere speciale.");
            model.addAttribute("token", token);
        	return "ResetPassword";
        }

        user.setPassword(passwordEncoder.encode(password));
        userDAO.save(user);

        model.addAttribute("success", "Password cambiata con successo!");
        return "redirect:/login?success=passwordReset";
    }
    
    //method to validate password using regex 
    private boolean isValidPassword(String password) {
        Pattern pattern = Pattern.compile(REGEX_PASSWORD);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches(); 
    }
}