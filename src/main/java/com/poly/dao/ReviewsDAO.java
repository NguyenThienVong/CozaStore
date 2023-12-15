package com.poly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.entity.Reviews;

public interface ReviewsDAO extends JpaRepository<Reviews, Long>{
	
	@Query(value = "Select * FROM Reviews where product_id = ?1", nativeQuery = true)
	List<Reviews> findByProductId(Long id);

}
