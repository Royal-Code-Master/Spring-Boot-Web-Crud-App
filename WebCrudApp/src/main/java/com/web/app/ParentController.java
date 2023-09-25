package com.web.app;

import java.util.Optional;

import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.web.app.entity.User;
import com.web.app.repository.UserRepo;

import jakarta.servlet.http.HttpSession;



@Controller
public class ParentController {
    
    @Autowired
    private UserRepo userrepo;
    
    
    
    // User Register Form Details.
    
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "index"; 
    }

    @PostMapping("/register")
    public String processRegistrationForm(@ModelAttribute("user") @Validated User user, BindingResult result) {
        if (result.hasErrors()) {
            return "/register";
        }

        userrepo.save(user);
        return "redirect:/signin";
    }

    
    
 // User login Form Details.
    
    @GetMapping("/signin")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "login"; 
    }
    
    
    @PostMapping("/signin")
    public String processLoginForm(@ModelAttribute("user") User user, BindingResult result, HttpSession session) {
        if (result.hasErrors()) {
            return "login"; 
        }

        User existingUser = userrepo.findByEmail(user.getEmail());
        if (existingUser != null && existingUser.getPwd().equals(user.getPwd())) {
            session.setAttribute("loggedInUser", existingUser);
            return "redirect:/all"; 
        } else {
            // Login failed, show an error message
            result.rejectValue("email", "error.user", "Invalid email or password");
            return "login";
        }
    }
    
       
 // User Details.
    
    @GetMapping("/all")

    public String userList(Model model) {
        java.util.List<User> users = userrepo.findAll();
        
        model.addAttribute("users", users);
  
        return "allusers"; 
    }

    
    
    // user logout
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/signin";
    }
    
    
    
    // delete user
    
    @GetMapping("/deleteuser")
    public String deleteUser(@RequestParam(name = "userId") Long userId, RedirectAttributes redirectAttributes) {
        try {
            userrepo.deleteById(userId);
            redirectAttributes.addFlashAttribute("successMessage", "User deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete user");
        }
        return "redirect:/all"; // Redirect to a users list page
    }
    
    @GetMapping("/updateuser")
    public String updatePage(Model model) {
    	model.addAttribute("user", new User());
        return "update"; 
    }
    
    @PostMapping("/updateuser")
    public String updateUser(@RequestParam(name = "userId") Long userId, 
            @RequestParam(name = "fname") String fname, 
            @RequestParam(name = "lname") String lname,
            @RequestParam(name = "email") String email,
            @RequestParam(name = "pwd") String pwd,
            
            RedirectAttributes redirectAttributes) {

        Optional<User> optionalUser = userrepo.findById(userId);

        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();

            existingUser.setFname(fname);
            existingUser.setLname(lname);
            existingUser.setEmail(email);
            existingUser.setPwd(pwd); 
            
            userrepo.save(existingUser); 
            
            redirectAttributes.addFlashAttribute("message", "User updated successfully");
        } else {
            redirectAttributes.addFlashAttribute("error", "User not found");
        }

        return "redirect:/all";
    }

}
