package com.poly.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.entity.Order_status;

public interface OrderStatusDAO extends JpaRepository<Order_status, String>{

}
