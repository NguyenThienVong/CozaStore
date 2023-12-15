package com.poly.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.dto.RevenueOrderDTO;
import com.poly.dto.TopCategoryDTO;
import com.poly.dto.TopCustomerDTO;
import com.poly.dto.TopProductDTO;
import com.poly.entity.Orders;

public interface OrdersDAO extends JpaRepository<Orders, Long> {

	@Query("SELECT o FROM Orders o WHERE o.accounts.username=?1 ORDER BY id DESC")
	List<Orders> findByUsername(String username);

	@Query(value = "SELECT * from Orders ORDER BY Id DESC;", nativeQuery = true)
	List<Orders> findAllDESC();

	@Query(value = "SELECT * FROM Orders WHERE Orders.status_id = 'Pending' ORDER BY create_date DESC", nativeQuery = true)
	List<Orders> findAllOrderPending();

	@Query(value = "SELECT * FROM Orders WHERE Orders.status_id = 'Confirm' ORDER BY create_date DESC", nativeQuery = true)
	List<Orders> findAllOrderConfirm();

	@Query(value = "SELECT * FROM Orders WHERE Orders.status_id = 'Delivery' ORDER BY create_date DESC", nativeQuery = true)
	List<Orders> findAllOrderDelivery();

	@Query(value = "SELECT * FROM Orders WHERE Orders.status_id = 'Complete' ORDER BY create_date DESC", nativeQuery = true)
	List<Orders> findAllOrderComplete();

	@Query(value = "SELECT * FROM Orders WHERE Orders.status_id = 'Cancel' ORDER BY create_date DESC", nativeQuery = true)
	List<Orders> findAllOrderCancel();

	@Query("SELECT new com.poly.dto.TopCustomerDTO(o.accounts, COUNT(o.accounts.username) as count, SUM(o.total) as sum) "
			+ "FROM Orders o GROUP BY o.accounts.username ORDER BY COUNT(o.accounts.username) DESC")
	List<TopCustomerDTO> getTopCustomers();

	@Query("Select new com.poly.dto.TopCategoryDTO(o.products.categories, sum(o.price*o.quantity), sum(o.quantity))"
			+ " from Order_detail o Group By o.products.categories")
	List<TopCategoryDTO> getRevenueCategory();

	@Query("SELECT new com.poly.dto.TopProductDTO(o.products, sum(o.quantity) as sum, sum(o.quantity * o.price) as sold)"
			+ " FROM Order_detail o GROUP BY o.products ORDER BY sum(o.quantity*o.price) DESC")
	List<TopProductDTO> getTopProducts();
	
	@Query("SELECT new com.poly.dto.RevenueOrderDTO(o.order_status, SUM(o.total) as sum, COUNT(o.order_status.id) as count) "
	        + "FROM Orders o "
	        + "GROUP BY o.order_status ORDER BY SUM(o.total) DESC")
	List<RevenueOrderDTO> getRevenueOrder();


	@Query("SELECT new com.poly.dto.RevenueOrderDTO(o.order_status, SUM(o.total) as sum, COUNT(o.order_status.id) as count) "
	        + "FROM Orders o WHERE o.create_date BETWEEN ?1 AND ?2 "
	        + "GROUP BY o.order_status ORDER BY SUM(o.total) DESC")
	List<RevenueOrderDTO> pushRevenueOrder(Date startDate, Date endDate);
	
	@Query(value = "SELECT * from Orders WHERE Orders.username like ?1 or id like ?1 ORDER BY Id DESC;", nativeQuery = true)
	List<Orders> findOrderByKw(String kw);
	
}