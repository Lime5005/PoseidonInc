package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/signup")
public class SignupController {
    private UserService userService;

    @Autowired
    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String signupView(Model model) {
        model.addAttribute(new User());
        return "signup";
    }

    @PostMapping
    private String signupUser(@Valid @ModelAttribute("user") User user,
                              BindingResult result, Model model, RedirectAttributes redirAttrs) {
        String signupError = null;
        User existsUser = userService.findByUsername(user.getUsername());
        if (existsUser != null) {
            signupError = "The username already exists";
        }
        if (signupError == null) {
//            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//            user.setPassword(encoder.encode(user.getPassword()));
            userService.save(user);
        }

        if (signupError == null && !result.hasErrors()) {
            redirAttrs.addFlashAttribute("message", "You've successfully signed up, please login.");
            return "redirect:/login";
        } else {
            model.addAttribute("signupError", true);
        }

        return "signup";

    }


}
