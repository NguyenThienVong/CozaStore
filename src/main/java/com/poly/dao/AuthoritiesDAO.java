package com.poly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.entity.Accounts;
import com.poly.entity.Authorities;

public interface AuthoritiesDAO extends JpaRepository<Authorities, Long> {
	
	@Query("select DISTINCT a FROM Authorities a WHERE a.accounts IN ?1")
	List<Authorities> authoritiesOf(List<Accounts> accounts);

}
