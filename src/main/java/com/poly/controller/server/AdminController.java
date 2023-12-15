package com.poly.controller.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.service.AccountService;

@Controller
public class AdminController {
	
	@Autowired
	AccountService accountService;
	
	@RequestMapping({ "/admin" , "/admin/index" })
	public String index(Model model) {
		 String username = SecurityContextHolder.getContext().getAuthentication().getName();
		 model.addAttribute("admin", accountService.findById(username));	
		return "admin/index";
	}

}
