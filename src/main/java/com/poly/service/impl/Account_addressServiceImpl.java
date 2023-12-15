package com.poly.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.dao.Account_addressDAO;
import com.poly.entity.Account_address;
import com.poly.service.Account_addressService;

@Service
public class Account_addressServiceImpl implements Account_addressService {
	
	
	@Autowired
	Account_addressDAO addressDAO;

	@Override
	public List<Account_address> findAll() {
		// TODO Auto-generated method stub
		return addressDAO.findAll();
	}

	@Override
	public Account_address create(Account_address userAddress) {
		// TODO Auto-generated method stub
		return addressDAO.save(userAddress);
	}

	@Override
	public Account_address update(Account_address userAddress) {
		// TODO Auto-generated method stub
		return addressDAO.save(userAddress);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		addressDAO.deleteById(id);
	}

	@Override
	public Account_address findById(Long id) {
		// TODO Auto-generated method stub
		return addressDAO.findById(id).get();
	}

}
