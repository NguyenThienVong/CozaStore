package com.poly.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.poly.service.BlogService;

@Controller
public class BlogController {
	
	@Autowired
	BlogService blogService;
	
	@GetMapping("/home/blog")
	public String blog(Model model) {
		model.addAttribute("blogs", blogService.findAll());
		return "user/blog/blog";
	}
	
	@RequestMapping("/home/blog-detail/{id}")
	public String blog_detail(Model model, @PathVariable("id") Long id) {
		model.addAttribute("dt", blogService.findById(id));
		return "user/blog/blog-detail";
	}

}
