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

import com.poly.entity.Categories;
import com.poly.service.CategoriesService;


@CrossOrigin("*")
@RestController
@RequestMapping("/rest/categories")
public class CategoriesRestController {
	
	@Autowired
	CategoriesService categoriesService;

	@GetMapping()
	public List<Categories> getAll() {
		return categoriesService.findAll();
	}
	
	@PostMapping()
	public Categories create(@RequestBody Categories categories) {
		return categoriesService.create(categories);
	}

	@PutMapping("{id}")
	public Categories update(@PathVariable("id") Long id, @RequestBody Categories categories) {
		return categoriesService.update(categories);
	}

	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") Long id) {
		categoriesService.delete(id);
	}

}
