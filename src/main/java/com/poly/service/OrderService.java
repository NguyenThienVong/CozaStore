package com.poly.service;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.poly.entity.Orders;

public interface OrderService {

	public Orders create(JsonNode orderData);

	public Orders findById(Long id);

	public List<Orders> findByUsername(String username);
	
	public List<Orders> findOrderByKw(String kw);

	List<Orders> findAll();

	List<Orders> findAllDESC();

	public void delete(Long id);

	public Orders update(Orders orders);

	List<Orders> findAllOrderPending();

	List<Orders> findAllOrderConfirm();

	List<Orders> findAllOrderDelivery();

	List<Orders> findAllOrderComplete();

	List<Orders> findAllOrderCancel();


}