package com.poly.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.dao.CategoriesDAO;
import com.poly.entity.Categories;
import com.poly.service.CategoriesService;

@Service
public class CategoriesServiceImpl implements CategoriesService {
	
	@Autowired
	CategoriesDAO categoriesDAO;

	@Override
	public List<Categories> findAll() {
		// TODO Auto-generated method stub
		return categoriesDAO.findAll();
	}

	@Override
	public Categories create(Categories categories) {
		// TODO Auto-generated method stub
		return categoriesDAO.save(categories);
	}

	@Override
	public Categories update(Categories categories) {
		// TODO Auto-generated method stub
		return categoriesDAO.save(categories);
	}

	@Override
	public void delete(Long id) {	
		categoriesDAO.deleteById(id);
	}

}
