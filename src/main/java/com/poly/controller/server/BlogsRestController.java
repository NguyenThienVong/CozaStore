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

import com.poly.entity.Blogs;
import com.poly.service.BlogService;


@CrossOrigin("*")
@RestController
@RequestMapping("/rest/blogs")
public class BlogsRestController {
	
	@Autowired
	BlogService blogService;
	

	@GetMapping("{id}")
	public Blogs getOne(@PathVariable("id") Long id) {
		return blogService.findById(id);
	}

	@GetMapping()
	public List<Blogs> getAll() {
		return blogService.findAll();
	}
	
	@PostMapping()
	public Blogs create(@RequestBody Blogs blogs) {
		return blogService.create(blogs);
	}

	@PutMapping("{id}")
	public Blogs update(@PathVariable("id") Long id, @RequestBody Blogs blogs) {
		return blogService.update(blogs);
	}

	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") Long id) {
		blogService.delete(id);
	}

}
