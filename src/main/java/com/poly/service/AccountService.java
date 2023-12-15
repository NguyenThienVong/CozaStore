package com.poly.service;

import java.util.List;

import com.poly.entity.Accounts;

public interface AccountService {
	
	public List<Accounts> findAll();

	public Accounts findById(String username);

	public List<Accounts> getAdministrators();

	public Accounts create(Accounts accounts);
	
	public Accounts update(Accounts accounts);

	public void delete(String username);

}
