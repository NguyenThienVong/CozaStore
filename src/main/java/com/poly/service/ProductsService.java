package com.poly.service;

import java.util.List;

import com.poly.entity.Products;

public interface ProductsService {
	
	List<Products> findAll();
	
	List<Products> loadAllNoSell();
	
	List<Products> loadAllOnSell();
	
	List<Products> loadAllNoSale();
	
	List<Products> loadAllOnSale();

	Products findById(Long id);
	
	public List<Products> findByCategoryId(String cid);
	
	public Products create(Products products);

	public Products update(Products products);

	public void delete(Long id);

	List<Products> findProductNew(String categoriesId);

	List<Products> findProductDESC(String categoriesId);

	List<Products> findProductASC(String categoriesId);

}
