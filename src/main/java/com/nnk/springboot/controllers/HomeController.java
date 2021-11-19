package com.nnk.springboot.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController
{
	@RequestMapping("/")
	public String home(Authentication authentication, Model model)
	{
		String name = authentication.getName();
		model.addAttribute("name", "Login as: " + name);
		return "home";
	}

	@RequestMapping("/admin/home")
	public String adminHome(Model model)
	{
		return "redirect:/bidList/list";
	}


}
