package com.poly.dto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.dao.OrdersDAO;


@CrossOrigin("*")
@RestController
@RequestMapping("/rest/topProduct")
public class TopProductRestController {
	
	@Autowired
	OrdersDAO ordersDAO;

	@GetMapping()
	public List<TopProductDTO> getAll() {
		return ordersDAO.getTopProducts();
	}
	
}
