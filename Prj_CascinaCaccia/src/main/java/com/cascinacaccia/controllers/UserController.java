package com.cascinacaccia.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import com.cascinacaccia.entities.UserImage;
import com.cascinacaccia.repos.UserDAO;
import com.cascinacaccia.repos.UserImageDAO;
import com.cascinacaccia.services.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

	@Autowired
	UserService userService;
	@Autowired
	UserImageDAO userImageDAO;
	@Autowired
	UserDAO userDAO;
	@Autowired
	PasswordEncoder passwordEncoder;
	
	private static final String REGEX_PASSWORD = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[\\p{Punct}])(?=\\S+$).{8,}$";
	
	//navigation to profile page
	@GetMapping("/profile")
	public String profilePage(@AuthenticationPrincipal Object principal, Model model) throws Exception {
	    
	    User user = userService.getUserByEmail(principal);

	    String profileImageUrl = user.getUserImage() != null ? user.getUserImage().getImgPath() : "/default-image.png";
	    
	    //pass the user's name, surname and email to the model
	    model.addAttribute("fullName", user.getName()); //add name
	    model.addAttribute("email", user.getEmail());  //add email
	    model.addAttribute("profileImageUrl", profileImageUrl); //add image
	    model.addAttribute("surname", user.getSurname()); //add surname
	    return "profile";
	}
	
	//navigation to change password page
	@GetMapping("/changePassword")
    public String changePasswordView(@AuthenticationPrincipal Object principal, Model model) {
    	User user = userService.getUserByEmail(principal);
        if(user.getPassword()!=null) {
        	model.addAttribute("passNULL",false);
        }
        else model.addAttribute("passNULL",true);
        
        return "ChangePassword"; 
    }
    
    @PostMapping("/changePassword")
    public String changePassword(@AuthenticationPrincipal Object principal,
                                 @RequestParam Map<String, String> formData, 
                                 RedirectAttributes redirectAttributes,
                                 HttpSession session) {  

        User user = userService.getUserByEmail(principal);

        try {
            String oldPassword = formData.get("oldPassword");
            String newPassword = formData.get("newPassword");
            String confirmPassword = formData.get("confirmPassword");
            
            //check if the new password you entered is correct
            if (!newPassword.equals(confirmPassword)) {
                redirectAttributes.addFlashAttribute("error", "New passwords do not match.");
                return "redirect:/changePassword";
            }
            
            if (!isValidPassword(newPassword)) {
                redirectAttributes.addFlashAttribute("error", "Password must be at least 8 characters long, contain an uppercase letter, a number, and a special character.");
                return "redirect:/changePassword";
            }
            
            userService.changePassword(user.getId(), oldPassword, newPassword);

            session.invalidate(); 
            
            redirectAttributes.addFlashAttribute("success", "Password change successful.");
            //redirect to the login page to request re-authentication
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/changePassword";
        }
    }
    
    //method to validate password using regex (same logic as in AdminController)
    private boolean isValidPassword(String password) {
        Pattern pattern = Pattern.compile(REGEX_PASSWORD);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches(); 
    }
    
    //navigation to profile image page
    @GetMapping("/profileImage")
    public String assignProfileImagePage(Model model, @AuthenticationPrincipal Object principal) {
        User user = userService.getUserByEmail(principal);

        //ensure user is added to the model
        model.addAttribute("user", user);

        //fetch all images from the database
        List<UserImage> allUserImages = userImageDAO.findAll();
        model.addAttribute("allUserImages", allUserImages);

        return "ProfileImage";
    }
    
    //assign or update profile image
    @PostMapping("/profileImage")
    public String assignProfileImage(@RequestParam String userId, @RequestParam Long userImageId, Model model) {
        try {
            userService.assignOrUpdateProfileImage(userId, userImageId);
            model.addAttribute("message", "Profile image updated successfully!");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/profile";
    }
    
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
            String result = userService.sendResetEmail(user); 
            if ("success".equals(result)) {
                model.addAttribute("success", "Email di reset inviata correttamente a " + email);
            } else {
                model.addAttribute("error", "Errore durante l'invio dell'email.");
            }
        } else {
            model.addAttribute("error", "Utente con questa email non trovato.");
        }

        return "ForgotPassword"; 
    }
    
    // Mostra il form di reset della password quando l'utente clicca sul link
    @GetMapping("/resetPassword/{token}")
    public String showResetPasswordForm(@PathVariable String token, Model model) {
        System.out.println("Received token: " + token);  // Check if the token is correctly passed
        PasswordResetToken resetToken = userService.findResetTokenByToken(token);

        if (resetToken != null && !userService.hasExpired(resetToken.getExpiryDateTime())) {
            model.addAttribute("token", token); // Add the token to the model
            return "ResetPassword"; // Display the form
        }

        return "redirect:/?error=expired"; // Redirect if token is expired or invalid
    }



    // Processa il reset della password
    @PostMapping("/resetPassword")
    public String processResetPassword(@RequestParam("token") String token,
                                       @RequestParam("password") String password, 
                                       @RequestParam("confirmPassword") String confirmPassword,
                                       Model model) {
        // Log the received token
        System.out.println("Received token in POST: " + token);
        
        // Fetch the reset token from the database
        PasswordResetToken resetToken = userService.findResetTokenByToken(token);
        if (resetToken == null) {
            System.out.println("Token not found in POST request!");
            return "redirect:/?error=invalidToken";
        }

        // Log the expiry date of the token
        System.out.println("Token expiry date: " + resetToken.getExpiryDateTime());
        
        // Check if the token has expired
        if (userService.hasExpired(resetToken.getExpiryDateTime())) {
            System.out.println("Token has expired.");
            return "redirect:/?error=expired";
        }

        // Continue with the password reset process if the token is valid
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Le password non coincidono.");
            model.addAttribute("token", token);
            return "ResetPassword";
        }
        
        if (!isValidPassword(confirmPassword)) {
        	model.addAttribute("error", "Password must be at least 8 characters long, contain an uppercase letter, a number, and a special character.");
            model.addAttribute("token", token);
        	return "ResetPassword";
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(password)); // Hash the password before saving
        userDAO.save(user);

        return "redirect:/login?success=passwordReset";
    }

}
