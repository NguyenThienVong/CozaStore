package com.poly.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.dao.VouchersDAO;
import com.poly.entity.Vouchers;
import com.poly.service.VoucherService;

@Service
public class VoucherServiceImpl implements VoucherService {
	
	@Autowired
	VouchersDAO vouchersDAO;

	@Override
	public List<Vouchers> findAll() {
		// TODO Auto-generated method stub
		return vouchersDAO.findAll();
	}

	@Override
	public Vouchers findById(String id) {
		// TODO Auto-generated method stub
		return vouchersDAO.findById(id).get();
	}

	@Override
	public Vouchers create(Vouchers vouchers) {
		// TODO Auto-generated method stub
		return vouchersDAO.save(vouchers);
	}

	@Override
	public Vouchers update(Vouchers vouchers) {
		// TODO Auto-generated method stub
		return vouchersDAO.save(vouchers);
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		vouchersDAO.deleteById(id);
	}

}
