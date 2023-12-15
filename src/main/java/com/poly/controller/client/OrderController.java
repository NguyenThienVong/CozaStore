package com.poly.controller.client;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.dao.OrderStatusDAO;
import com.poly.dao.OrdersDAO;
import com.poly.entity.Order_detail;
import com.poly.entity.Orders;
import com.poly.service.OrderDetailService;
import com.poly.service.OrderService;
import com.poly.utils.ParamService;

@Controller
public class OrderController {

	@Autowired
	OrderService orderService;
	@Autowired
	OrderDetailService orderDetailService;
	@Autowired
	OrdersDAO orderDAO;
	@Autowired
	OrderStatusDAO statusDAO;

	@Autowired
	ParamService paramService;

	@RequestMapping("/order/checkout")
	public String checkout() {
		return "user/cart/checkout";
	}

	@RequestMapping("/order/myorder")
	public String list(Model model, HttpServletRequest request) {
		String username = request.getRemoteUser();
		model.addAttribute("orders", orderService.findByUsername(username));
		return "user/order/myorder";
	}

	@RequestMapping("/order/detail/{id}")
	public String detail(@PathVariable("id") Long id, Model model) {
		List<Order_detail> orderDetails = orderDetailService.findByOrderId(id);
		model.addAttribute("order", orderService.findById(id));
		model.addAttribute("orderDetails", orderDetails);
		return "user/order/OrderDetail";
	}

	@RequestMapping("/order/cancel/{id}")
	public String cancel(@PathVariable("id") Long id, Model model) {
		Orders orders = orderService.findById(id);
		orders.setOrder_status(statusDAO.getById("Cancel"));
		orderDAO.save(orders);
		return "redirect:/order/detail/" + id;
	}
	
	@RequestMapping("/order/complete/{id}")
	public String complete(@PathVariable("id") Long id, Model model) {
		Orders orders = orderService.findById(id);
		orders.setOrder_status(statusDAO.getById("Complete"));
		orderDAO.save(orders);
		return "redirect:/order/detail/" + id;
	}

	@RequestMapping("/order/list")
	public String list(Model model) {
		model.addAttribute("orders", orderService.findAllDESC());
		return "user/order/ADorder";
	}

}