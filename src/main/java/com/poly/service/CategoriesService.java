package com.poly.service;

import java.util.List;

import com.poly.entity.Categories;

public interface CategoriesService {
	
	List<Categories> findAll();

	Categories create(Categories categories);

	Categories update(Categories categories);

	void delete(Long id);

}
