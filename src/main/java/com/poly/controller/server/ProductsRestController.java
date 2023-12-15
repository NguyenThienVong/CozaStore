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

import com.poly.entity.Products;
import com.poly.service.ProductsService;


@CrossOrigin("*")
@RestController
@RequestMapping("/rest/products")
public class ProductsRestController {

	@Autowired
	ProductsService productsService;

	@GetMapping("{id}")
	public Products getOne(@PathVariable("id") Long id) {
		return productsService.findById(id);
	}

	@GetMapping()
	public List<Products> getAll() {
		return productsService.findAll();
	}
	
	@GetMapping("noSell")
	public List<Products> getAllNoSell() {
		return productsService.loadAllNoSell();
	}
	
	@GetMapping("onSell")
	public List<Products> getAllOnSell() {
		return productsService.loadAllOnSell();
	}
	
	@GetMapping("noSale")
	public List<Products> getAllNoSale() {
		return productsService.loadAllNoSale();
	}
	
	@GetMapping("onSale")
	public List<Products> getAllOnSale() {
		return productsService.loadAllOnSale();
	}

	@PostMapping()
	public Products create(@RequestBody Products products) {
		return productsService.create(products);
	}

	@PutMapping("{id}")
	public Products update(@PathVariable("id") Long id, @RequestBody Products products) {
		return productsService.update(products);
	}

	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") Long id) {
		productsService.delete(id);
	}
}
