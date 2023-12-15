package com.poly.service.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.dao.OrdersDetailDAO;
import com.poly.entity.Order_detail;
import com.poly.service.OrderDetailService;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
	
	@Autowired
	OrdersDetailDAO ordersDetailDAO;

	 @Override
	    public List<Order_detail> findByOrderId(Long id) {
	        // Triển khai logic để lấy danh sách Order_detail dựa trên order_id
	        return ordersDetailDAO.findByOrders_Id(id);
	    }

	@Override
	public Order_detail findById(Long id) {
		// TODO Auto-generated method stub
		return ordersDetailDAO.findById(id).get();
	}


	
}