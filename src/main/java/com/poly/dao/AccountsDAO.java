package com.poly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.entity.Accounts;

public interface AccountsDAO extends JpaRepository<Accounts, String> {
	
	@Query("select distinct acc.accounts from Authorities acc where acc.roles.id IN ('USER', 'STAFF')")
	List<Accounts> getAdmin();

}
