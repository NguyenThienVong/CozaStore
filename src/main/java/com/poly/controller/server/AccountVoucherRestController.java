package com.poly.controller.server;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.dao.Account_voucherDAO;
import com.poly.entity.Account_voucher;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/account-voucher")
public class AccountVoucherRestController {

	@Autowired
	Account_voucherDAO account_voucherDAO;

	@GetMapping()
	public List<Account_voucher> getAll() {
		return account_voucherDAO.findAll();
	}

	@GetMapping("{username}")
	public List<Account_voucher> getByUsername(@PathVariable("username") String username) {
		return account_voucherDAO.findByUsername(username);
	}

	@PutMapping("{username}")
	public Account_voucher update(@PathVariable("username") String username,
			@RequestBody Account_voucher account_voucher) {
		return account_voucherDAO.save(account_voucher);
	}
}
