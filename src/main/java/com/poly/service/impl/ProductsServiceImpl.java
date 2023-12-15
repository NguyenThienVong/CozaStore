package com.poly.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.dao.ProductsDAO;
import com.poly.entity.Products;
import com.poly.service.ProductsService;

@Service
public class ProductsServiceImpl implements ProductsService {
	
	@Autowired
	ProductsDAO productsDAO;

	@Override
	public List<Products> findAll() {
		// TODO Auto-generated method stub
		return productsDAO.findAll();
	}

	@Override
	public Products findById(Long id) {
		// TODO Auto-generated method stub
		return productsDAO.findById(id).get();
	}

	@Override
	public Products create(Products products) {
		// TODO Auto-generated method stub
		return productsDAO.save(products);
	}

	@Override
	public Products update(Products products) {
		// TODO Auto-generated method stub
		return productsDAO.save(products);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		productsDAO.deleteById(id);
	}

	@Override
	public List<Products> findByCategoryId(String cid) {
		// TODO Auto-generated method stub
		return productsDAO.findByCategoryId(cid);
	}
	
	@Override
	public List<Products> findProductNew(String categoriesId) {
		// TODO Auto-generated method stub
		return productsDAO.findProductNew(categoriesId);
	}


	@Override
	public List<Products> findProductDESC(String categoriesId) {
		// TODO Auto-generated method stub
		return productsDAO.findProductDESC(categoriesId);
	}


	@Override
	public List<Products> findProductASC(String categoriesId) {
		// TODO Auto-generated method stub
		return productsDAO.findProductASC(categoriesId);
	}

	@Override
	public List<Products> loadAllNoSale() {
		// TODO Auto-generated method stub
		return productsDAO.loadAllNoSale();
	}

	@Override
	public List<Products> loadAllOnSale() {
		// TODO Auto-generated method stub
		return productsDAO.loadAllOnSale();
	}

	@Override
	public List<Products> loadAllNoSell() {
		// TODO Auto-generated method stub
		return productsDAO.loadAllNoSell();
	}

	@Override
	public List<Products> loadAllOnSell() {
		// TODO Auto-generated method stub
		return productsDAO.loadAllOnSell();
	}

}
