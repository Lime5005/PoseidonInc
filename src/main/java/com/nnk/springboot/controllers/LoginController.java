package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.BidListService;
import com.nnk.springboot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Objects;

@Controller
@RequestMapping
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private BidListService bidListService;

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @PostMapping("/login")
    public String getHome(Principal principal, Model model) {
        Authentication authentication = (Authentication) principal;
        Object princpl = authentication.getPrincipal();
        String name = princpl.getClass().getName();
        System.out.println(name + " === name");
        User user = userService.findByUsername(name);
        if (Objects.equals(user.getRole(), "ADMIN")) {
            model.addAttribute("users", userService.findAll());
            return "redirect: user/list";
        }
        if (user.getRole().equals("USER")) {
            model.addAttribute("bidLists", bidListService.findAll());
        }
        return "redirect: bidList/list";

    }

    @GetMapping("/secure/article-details")
    public ModelAndView getAllUserArticles() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("users", userService.findAll());
        mav.setViewName("user/list");
        return mav;
    }

    @GetMapping("/error")
    public ModelAndView error() {
        ModelAndView mav = new ModelAndView();
        String errorMessage= "You are not authorized for the requested data.";
        mav.addObject("errorMsg", errorMessage);
        mav.setViewName("403");
        return mav;
    }
}
