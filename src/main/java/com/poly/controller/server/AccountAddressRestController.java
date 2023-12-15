package com.poly.controller.server;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.dao.Account_addressDAO;
import com.poly.entity.Account_address;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/account-address")
public class AccountAddressRestController {
	
	@Autowired
	Account_addressDAO account_addressDAO;
	
	@GetMapping()
	public List<Account_address> getAll() {
		return account_addressDAO.findAll();
	}

	@GetMapping("{username}")
	public List<Account_address> getByUsername(@PathVariable("username") String username) {
		return account_addressDAO.findByUsername(username);
	}
	

}
