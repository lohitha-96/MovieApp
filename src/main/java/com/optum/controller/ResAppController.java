package com.optum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.optum.model.User;

@Controller
@RequestMapping("/movieapp")
public class ResAppController { // returning resources(jsp pages)

	// http://localhost:9091/movieapp/login
	@GetMapping("/login")
	public String register(Model model) {
		model.addAttribute("user", new User());
		return "login";
	}

	// http://localhost:9091/movieapp/signup
	@GetMapping("/signup")
	public String login(Model model) {
		model.addAttribute("user", new User());
		return "signup";
	}

}
