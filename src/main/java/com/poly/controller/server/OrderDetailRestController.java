package com.poly.controller.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.entity.Order_detail;
import com.poly.service.OrderDetailService;



@CrossOrigin("*")
@RestController
@RequestMapping("/rest/orderdetais")
public class OrderDetailRestController {
	
	@Autowired
	OrderDetailService orderDetailService;
	
	@GetMapping("{id}")
	public Order_detail getOne(@PathVariable("id") Long id) {
		return (Order_detail) orderDetailService.findByOrderId(id);
	}

}
