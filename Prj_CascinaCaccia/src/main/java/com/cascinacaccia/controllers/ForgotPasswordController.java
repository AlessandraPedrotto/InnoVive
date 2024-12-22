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
import com.cascinacaccia.repos.UserDAO;
import com.cascinacaccia.services.ForgotPasswordService;
import com.cascinacaccia.services.UserService;

@Controller
public class ForgotPasswordController {
	
	@Autowired
	ForgotPasswordService forgotPasswordService;
	@Autowired
	UserService userService;
	@Autowired
	UserDAO userDAO;
	@Autowired
	PasswordEncoder passwordEncoder;
	
	private static final String REGEX_PASSWORD = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[\\p{Punct}])(?=\\S+$).{8,}$";
	
	//navigation to the page forgot password
    @GetMapping("/forgotPassword")
    public String showForgotPasswordPage() {
        return "ForgotPassword"; 
    }
    
    @PostMapping("/forgotPassword")
    public String processForgotPassword(@RequestParam("email") String email, Model model) {
        Optional<User> optionalUser = userDAO.findByEmail(email);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String result = forgotPasswordService.sendResetEmail(user); 
            if ("success".equals(result)) {
                model.addAttribute("success", "Reset email successfully sent to " + email);
            } else {
                model.addAttribute("error", "An error occurred while sending the email.");
            }
        } else {
            model.addAttribute("error", "No user with this mail.");
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
            return "redirect:/?error=invalidToken";
        }

        //log the expiry date of the token
        System.out.println("Token expiry date: " + resetToken.getExpiryDateTime());
        
        //check if the token has expired
        if (forgotPasswordService.hasExpired(resetToken.getExpiryDateTime())) {
            System.out.println("Token has expired.");
            return "redirect:/?error=expired";
        }

        //continue with the password reset process if the token is valid
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "The passwords do not match.");
            model.addAttribute("token", token);
            return "ResetPassword";
        }
        
        User user = resetToken.getUser();

        // Check if the new password is the same as the current one
        if (passwordEncoder.matches(password, user.getPassword())) {
            model.addAttribute("error", "New password cannot be the same as the old password.");
            model.addAttribute("token", token);
            return "ResetPassword";
        }
        
        if (!isValidPassword(confirmPassword)) {
        	model.addAttribute("error", "Password must be at least 8 characters long, contain an uppercase letter, a number, and a special character.");
            model.addAttribute("token", token);
        	return "ResetPassword";
        }

        user.setPassword(passwordEncoder.encode(password));
        userDAO.save(user);

        return "redirect:/login?success=passwordReset";
    }
    
    //method to validate password using regex 
    private boolean isValidPassword(String password) {
        Pattern pattern = Pattern.compile(REGEX_PASSWORD);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches(); 
    }
}
