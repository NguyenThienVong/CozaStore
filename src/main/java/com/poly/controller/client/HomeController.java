package com.poly.controller.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.dao.BlogsDAO;
import com.poly.entity.Products;
import com.poly.service.ProductsService;

@Controller
public class HomeController {
	
	@Autowired
	ProductsService productService;
	
	@Autowired
	BlogsDAO blogsDAO;
	
	@RequestMapping({ "/" , "/home/index" })
	public String index(Model model) {
			
		  List<Products> onSale = productService.loadAllOnSale();
		  model.addAttribute("onSale", onSale);
		
		 /* iPhone */
		  List<Products> items1a = productService.findProductNew("1");
		  model.addAttribute("items1a", items1a);
		  List<Products> items1b = productService.findProductDESC("1");
		  model.addAttribute("items1b", items1b);	  
		  List<Products> items1c = productService.findProductASC("1");
		  model.addAttribute("items1c", items1c);
		  
		  /* iPad */
		  List<Products> items2a = productService.findProductNew("2");
		  model.addAttribute("items2a", items2a);
		  List<Products> items2b = productService.findProductDESC("2");
		  model.addAttribute("items2b", items2b);	  
		  List<Products> items2c = productService.findProductASC("2");
		  model.addAttribute("items2c", items2c);
		  
		  /* MacBook */
		  List<Products> items3a = productService.findProductNew("3");
		  model.addAttribute("items3a", items3a);
		  List<Products> items3b = productService.findProductDESC("3");
		  model.addAttribute("items3b", items3b);	  
		  List<Products> items3c = productService.findProductASC("3");
		  model.addAttribute("items3c", items3c);
		  
		  /* Apple Watch */
		  List<Products> items4a = productService.findProductNew("4");
		  model.addAttribute("items4a", items4a);
		  List<Products> items4b = productService.findProductDESC("4");
		  model.addAttribute("items4b", items4b);	  
		  List<Products> items4c = productService.findProductASC("4");
		  model.addAttribute("items4c", items4c);
		  
		  /* Âm Thanh */
		  List<Products> items5a = productService.findProductNew("5");
		  model.addAttribute("items5a", items5a);
		  List<Products> items5b = productService.findProductDESC("5");
		  model.addAttribute("items5b", items5b);	  
		  List<Products> items5c = productService.findProductASC("5");
		  model.addAttribute("items5c", items5c);
		  
		  /* Phụ Kiện */
		  List<Products> items6a = productService.findProductNew("6");
		  model.addAttribute("items6a", items6a);
		  List<Products> items6b = productService.findProductDESC("6");
		  model.addAttribute("items6b", items6b);	  
		  List<Products> items6c = productService.findProductASC("6");
		  model.addAttribute("items6c", items6c);
		 
		model.addAttribute("blogs", blogsDAO.load3Blogs());
		return "user/index";
	}
	
	
	@RequestMapping("/home/about")
	public String about() {
		return "user/blog/about";
	}
	
	
	@RequestMapping("/home/cart")
	public String cart() {
		return "user/cart/cart";
	}
	
	
	@RequestMapping("/home/login")
	public String login() {
		return "user/account/login";
	}

}
