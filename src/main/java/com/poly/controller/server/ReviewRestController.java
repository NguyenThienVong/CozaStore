package com.poly.controller.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.dao.ReviewsDAO;
import com.poly.entity.Reviews;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/reviews")
public class ReviewRestController {
	
	@Autowired
	ReviewsDAO reviewsDAO;
	
	
	@PostMapping()
	public Reviews create(@RequestBody Reviews reviews) {
		return reviewsDAO.save(reviews);
	}


}
