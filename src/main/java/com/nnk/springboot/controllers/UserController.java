package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.MyPasswordValidator;
import com.nnk.springboot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/user/list")
    public String home(Authentication auth, Model model)
    {
        model.addAttribute("users", userService.findAll());
        return "user/list";
    }

    @GetMapping("/user/add")
    public String addUser(User user) {
        return "user/add";
    }

    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
        String duplicateError = null;
        String patternError = null;
        // Validate username
        User existsUser = userService.findByUsername(user.getUsername());
        if (existsUser != null) {
            duplicateError = "The username already exists";
            model.addAttribute("duplicateError", true);
        }
        // Validate password
        String userPassword = user.getPassword();
        boolean valid = MyPasswordValidator.isValid(userPassword);
        if (!valid) {
            patternError = "Password doesn't match the pattern";
            model.addAttribute("patternError", true);
        }
        if (duplicateError == null && patternError == null && (!result.hasErrors())) {
            userService.save(user);
            model.addAttribute("users", userService.findAll());
            return "redirect:/user/list";
        }
        return "user/add";
    }

    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        User user = userService.findById(id);
        user.setPassword("");
        model.addAttribute("user", user);
        return "user/update";
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) {
        String duplicateError = null;
        String patternError = null;
        // Username CAN be the same when update
        User userToUpdate = userService.findById(id);
        User existsUser = userService.findByUsername(user.getUsername());
        if (!userToUpdate.getUsername().equals(user.getUsername())) {
            if (existsUser != null) {
                duplicateError = "The username already exists";
                model.addAttribute("duplicateError", true);
            }
        }
        // Validate password
        String userPassword = user.getPassword();
        boolean valid = MyPasswordValidator.isValid(userPassword);
        if (!valid) {
            patternError = "Password doesn't match the pattern";
            model.addAttribute("patternError", true);
        }
        if (duplicateError == null && patternError == null && (!result.hasErrors())) {
            Boolean updated = userService.updateUser(id, user);
            if (updated) {
                model.addAttribute("users", userService.findAll());
                return "redirect:/user/list";
            }
        }
        return "user/update";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        User user = userService.findById(id);
        userService.delete(user);
        model.addAttribute("users", userService.findAll());
        return "redirect:/user/list";
    }
}
