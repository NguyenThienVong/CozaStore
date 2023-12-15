package com.poly.service;

import java.util.List;

import com.poly.entity.Order_detail;

public interface OrderDetailService {
	
	 List<Order_detail> findByOrderId(Long id);
	 
	 Order_detail findById(Long id);
	 
}