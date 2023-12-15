package com.poly.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.dao.BlogsDAO;
import com.poly.entity.Blogs;
import com.poly.service.BlogService;

@Service
public class BlogServiceImpl implements BlogService {
	
	@Autowired
	BlogsDAO blogsDAO;
	

	@Override
	public List<Blogs> findAll() {
		// TODO Auto-generated method stub
		return blogsDAO.findAll();
	}
	
	@Override
	public Blogs findById(Long id) {
		// TODO Auto-generated method stub
		return blogsDAO.findById(id).get();
	}

	@Override
	public Blogs create(Blogs blogs) {
		// TODO Auto-generated method stub
		return blogsDAO.save(blogs);
	}

	@Override
	public Blogs update(Blogs blogs) {
		// TODO Auto-generated method stub
		return blogsDAO.save(blogs);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		blogsDAO.deleteById(id);		
	}

}
