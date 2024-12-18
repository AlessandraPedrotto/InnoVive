package com.cascinacaccia.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cascinacaccia.entities.User;
import com.cascinacaccia.services.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
    private UserService userService;

    //admin Dashboard
    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "Dashboard";
    }

    //delete User
    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam String userId) {
        userService.deleteUserById(userId);
     
        //redirect back to the dashboard
        return "redirect:/admin/dashboard"; 
    }
}
