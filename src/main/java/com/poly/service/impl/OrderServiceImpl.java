package com.poly.service.impl;

import java.util.List;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poly.dao.OrderStatusDAO;
import com.poly.dao.OrdersDAO;
import com.poly.dao.OrdersDetailDAO;
import com.poly.entity.Order_detail;
import com.poly.entity.Orders;
import com.poly.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{
	@Autowired
	OrdersDAO dao;
	
	@Autowired
	OrdersDetailDAO ddao;
	
	@Autowired
	OrderStatusDAO statusDAO;

	@Override
	public Orders create(JsonNode orderData) {
		ObjectMapper mapper = new ObjectMapper();

		Orders order = mapper.convertValue(orderData, Orders.class);
		dao.save(order);

		TypeReference<List<Order_detail>> type = new TypeReference<List<Order_detail>>() {
		};
		List<Order_detail> details = mapper.convertValue(orderData.get("orderDetails"), type).stream()
				.peek(d -> d.setOrders(order)).collect(Collectors.toList());
		ddao.saveAll(details);

		return order;

	}

	@Override
	public Orders findById(Long id) {
		return dao.findById(id).get();
	}

	@Override
	public List<Orders> findByUsername(String username) {
		return dao.findByUsername(username);
	}

	@Override
	public List<Orders> findAll() {
		return dao.findAll();
	}

	@Override
	public List<Orders> findAllDESC() {
		return dao.findAllDESC();
	}

	@Override
	public void delete(Long id) {
		dao.deleteById(id);
	}

	@Override
	public Orders update(Orders orders) {
		return dao.save(orders);
	}


	@Override
	public List<Orders> findAllOrderPending() {
		// TODO Auto-generated method stub
		return dao.findAllOrderPending();
	}

	@Override
	public List<Orders> findAllOrderConfirm() {
		// TODO Auto-generated method stub
		return dao.findAllOrderConfirm();
	}

	@Override
	public List<Orders> findAllOrderDelivery() {
		// TODO Auto-generated method stub
		return dao.findAllOrderDelivery();
	}

	@Override
	public List<Orders> findAllOrderComplete() {
		// TODO Auto-generated method stub
		return dao.findAllOrderComplete();
	}

	@Override
	public List<Orders> findAllOrderCancel() {
		// TODO Auto-generated method stub
		return dao.findAllOrderCancel();
	}

	@Override
	public List<Orders> findOrderByKw(String kw) {
		// TODO Auto-generated method stub
		return dao.findOrderByKw("%"+kw+"%");
	}


}