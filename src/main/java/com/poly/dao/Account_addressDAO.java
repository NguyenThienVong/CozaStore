package com.poly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.poly.entity.Account_address;

public interface Account_addressDAO extends JpaRepository<Account_address, Long> {
		
	@Query(value = "SELECT * from Account_address where Account_address.username = ?1", nativeQuery = true)
	List<Account_address> findByUsername(String username);

}
