package com.example.security.web.controller;
 
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


import lombok.AllArgsConstructor;
 
@Controller
@AllArgsConstructor
public class HomeController {
 
	@GetMapping({ "/", "/home" })
	public String home() {
		return "home";
	}
 
	@GetMapping("/hello")
	public String hello() {
		return "hello";
	}
	
}