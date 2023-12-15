package com.poly.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.dao.AccountsDAO;
import com.poly.entity.Accounts;
import com.poly.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	AccountsDAO accountsDAO;

	@Override
	public List<Accounts> findAll() {
		// TODO Auto-generated method stub
		return accountsDAO.findAll();
	}

	@Override
	public Accounts findById(String username) {
		// TODO Auto-generated method stub
		return accountsDAO.findById(username).get();
	}

	@Override
	public List<Accounts> getAdministrators() {
		// TODO Auto-generated method stub
		return accountsDAO.getAdmin();
	}

	@Override
	public Accounts create(Accounts accounts) {
		// TODO Auto-generated method stub
		return accountsDAO.save(accounts);
	}

	@Override
	public Accounts update(Accounts accounts) {
		// TODO Auto-generated method stub
		return accountsDAO.save(accounts);
	}

	@Override
	public void delete(String username) {
		// TODO Auto-generated method stub
		accountsDAO.deleteById(username);
	}


}
