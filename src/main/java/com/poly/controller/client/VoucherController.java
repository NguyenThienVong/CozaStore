package com.poly.controller.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.dao.Account_voucherDAO;
import com.poly.dao.AccountsDAO;
import com.poly.dao.VouchersDAO;
import com.poly.entity.Account_voucher;
import com.poly.entity.Vouchers;

@Controller
@RequestMapping("home")
public class VoucherController {

	@Autowired
	VouchersDAO vouchersDAO;

	@Autowired
	Account_voucherDAO account_voucherDAO;

	@Autowired
	AccountsDAO accountsDAO;

	@GetMapping("vouchers/list")
	public String vouchersALL(Model model) {
		List<Vouchers> vouchers = vouchersDAO.findAll();
		model.addAttribute("vouchers", vouchers);
		return "user/voucher/listVoucher";

	}

	@GetMapping("vouchers/{username}")
	public String vouchers(@PathVariable("username") String username, Model model) {
		model.addAttribute("voucher", account_voucherDAO.findByUsername(username));
		return "user/voucher/myVoucher";
	}

	@RequestMapping("vouchers/add/{id}")
	public String vouchersCreate(Model model, @PathVariable("id") String id) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Account_voucher acc = new Account_voucher();
		acc.setAccounts(accountsDAO.getById(username));
		acc.setVouchers(vouchersDAO.getById(id));
		account_voucherDAO.save(acc);
		return "redirect:/home/vouchers/"+ username;
	}

}
