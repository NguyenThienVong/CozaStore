package com.poly.service;

import java.util.List;

import com.poly.entity.Vouchers;

public interface VoucherService {

	List<Vouchers> findAll();

	Vouchers findById(String id);

	Vouchers create(Vouchers vouchers);

	Vouchers update(Vouchers vouchers);

	void delete(String id);

}
