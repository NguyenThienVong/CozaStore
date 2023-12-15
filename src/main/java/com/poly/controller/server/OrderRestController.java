package com.poly.controller.server;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.poly.entity.Order_detail;
import com.poly.entity.Orders;
import com.poly.service.OrderDetailService;
import com.poly.service.OrderService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/orders")
public class OrderRestController {

	@Autowired
	OrderService orderService;

	@Autowired
	OrderDetailService orderDetailService;

	@PostMapping()
	public Orders create(@RequestBody JsonNode orderData) {
		return orderService.create(orderData);
	}

	@GetMapping()
	public List<Orders> getAll() {
		return orderService.findAllDESC();
	}

	@GetMapping("load-orders-detail/{id}")
	public List<Order_detail> getAllDetail(@PathVariable("id") Long id) {
		return orderDetailService.findByOrderId(id);
	}
	
	@GetMapping("search/{kw}")
	public List<Orders> findAllOrderBySearch(@PathVariable("kw") String kw){
		return orderService.findOrderByKw(kw);
	}

	@GetMapping("{id}")
	public Orders getOne(@PathVariable("id") Long id) {
		return orderService.findById(id);
	}

	@PutMapping("{id}")
	public Orders update(@PathVariable("id") Long id, @RequestBody Orders order) {
		return orderService.update(order);
	}

	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") Long id) {
		orderService.delete(id);
	}

	@GetMapping("all-orders")
	public List<Orders> allOrder() {
		return orderService.findAllDESC();
	}

	@GetMapping("order-pending")
	public List<Orders> orderPending() {
		return orderService.findAllOrderPending();
	}

	@GetMapping("order-confirm")
	public List<Orders> orderConfirm() {
		return orderService.findAllOrderConfirm();
	}

	@GetMapping("order-delivery")
	public List<Orders> orderDelivery() {
		return orderService.findAllOrderDelivery();
	}

	@GetMapping("order-complete")
	public List<Orders> orderComplete() {
		return orderService.findAllOrderComplete();
	}

	@GetMapping("order-cancel")
	public List<Orders> orderCancel() {
		return orderService.findAllOrderCancel();
	}

}