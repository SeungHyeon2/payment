package com.example.security.web.controller;
 
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.security.persistence.model.Product;
import com.example.security.service.ProductService;
 
@Controller
public class SystemController {
 
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	ProductService productService;
	
	
	@GetMapping("/system")
	public String system(HttpServletRequest request) {
		return "system";
	}
 
	@GetMapping("/system/create")
	public String create(HttpServletRequest request) {
		return "systemCreate";
	}
	
	@GetMapping("/system/delete")
	public String delete(HttpServletRequest request, Model model) {
		List<Product> list = productService.getAllUsers();
		
		model.addAttribute("list", list);
		return "systemDelete";
	}
	
	@GetMapping("/accessDenied")
	public String accessDenied() {
		return "accessDenied";
	}
	
}
