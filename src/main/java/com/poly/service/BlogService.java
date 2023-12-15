package com.poly.service;

import java.util.List;

import com.poly.entity.Blogs;

public interface BlogService {
	
	
	List<Blogs> findAll();
	
	Blogs findById(Long id);

	Blogs create(Blogs blogs);

	Blogs update(Blogs blogs);

	void delete(Long id);

}
