package com.poly.dto;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.dao.OrdersDAO;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/revenueOrder")
public class RevenueOrderRestController {
	
	@Autowired
	OrdersDAO ordersDAO;
	
	@GetMapping("all")
	public List<RevenueOrderDTO> getAll() {
		return ordersDAO.getRevenueOrder();
	}
	
	@GetMapping("{startDate}/{endDate}")
	public List<RevenueOrderDTO> getAllByTime(@PathVariable("startDate") Date startDate, @PathVariable("endDate") Date endDate) {
		return ordersDAO.pushRevenueOrder(startDate, endDate);
	}
	

}
