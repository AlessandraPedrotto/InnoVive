package com.cascinacaccia.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cascinacaccia.entities.User;
import com.cascinacaccia.entities.UserImage;
import com.cascinacaccia.repos.UserImageDAO;
import com.cascinacaccia.services.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

	@Autowired
	UserService userService;
	@Autowired
	UserImageDAO userImageDAO;
	
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
}
