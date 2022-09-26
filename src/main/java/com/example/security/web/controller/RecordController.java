package com.example.security.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.security.persistence.model.PayRecord;
import com.example.security.service.RecordService;

@Controller
public class RecordController {

	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	RecordService recordService;
	
	@RequestMapping("/record")
	public String record(HttpServletRequest request, Model model) {
		
		
		return "/record/record";
	}
	
	@RequestMapping("/record/payRecord")
	public String payRecord(HttpServletRequest request, Model model) {

		// SecurityContextHolder 빈을 통해 SpringSecurity 로그인 객체를 불러옴
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = (UserDetails)principal;
		String id = userDetails.getUsername();
		
		model.addAttribute("uid", id);
		
		List<PayRecord> list = recordService.findRecord(id);
		
		model.addAttribute("list", list);
		
		int count = recordService.count(id);
		model.addAttribute("count", count);
		
		return "record/payRecord";
	}
	
	@RequestMapping("/record/chargeRecord")
	public String chargeRecord(HttpServletRequest request, Model model) {
		
		
		return "record/chargeRecord";
	}
}
