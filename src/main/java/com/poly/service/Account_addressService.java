package com.poly.service;

import java.util.List;

import com.poly.entity.Account_address;

public interface Account_addressService {
	
	public List<Account_address> findAll();
	
	public Account_address findById(Long id);

	public Account_address create(Account_address userAddress);

	public Account_address update(Account_address userAddress);

	public void delete(Long id);

}
