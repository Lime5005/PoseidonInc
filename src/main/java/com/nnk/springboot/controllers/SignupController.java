package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.MyPasswordValidator;
import com.nnk.springboot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
        String patternError = null;
        // Validate username
        User existsUser = userService.findByUsername(user.getUsername());
        if (existsUser != null) {
            signupError = "The username already exists";
            model.addAttribute("signupError", true);
        }
        // Validate password
        String userPassword = user.getPassword();
        boolean valid = MyPasswordValidator.isValid(userPassword);
        if (!valid) {
            patternError = "Password doesn't match the pattern";
            model.addAttribute("patternError", true);
        }
        if (signupError == null && patternError == null && (!result.hasErrors())) {
            userService.save(user);
            redirAttrs.addFlashAttribute("message", "You've successfully signed up, please login.");
            return "redirect:/login";
        }

        return "signup";

    }

}
