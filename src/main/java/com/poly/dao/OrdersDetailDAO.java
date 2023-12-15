package com.poly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poly.entity.Order_detail;

@Repository
public interface OrdersDetailDAO extends JpaRepository<Order_detail, Long> {
	List<Order_detail> findByOrders_Id(Long id);
}