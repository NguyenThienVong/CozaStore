package com.poly.controller.server;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.entity.Vouchers;
import com.poly.service.VoucherService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/vouchers")
public class VoucherRestController {
	
	@Autowired
	VoucherService voucherService;

	@GetMapping()
	public List<Vouchers> getAll() {
		return voucherService.findAll();
	}
	
	@GetMapping("{username}")
	public Vouchers getOne(@PathVariable("username") String id) {
		return voucherService.findById(id);
	}
	
	@PostMapping()
	public Vouchers create(@RequestBody Vouchers vouchers) {
		return voucherService.create(vouchers);
	}

	@PutMapping("{id}")
	public Vouchers update(@PathVariable("id") String id, @RequestBody Vouchers vouchers) {
		return voucherService.update(vouchers);
	}

	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") String id) {
		voucherService.delete(id);
	}

}
