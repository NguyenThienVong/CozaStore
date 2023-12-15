package com.poly.controller.server;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poly.entity.Authorities;
import com.poly.service.AuthoritiesService;



@CrossOrigin("*")
@RestController
@RequestMapping("/rest/authorities")
public class AuthoritiesRestController {

	@Autowired
	AuthoritiesService authoritiesService;

	@GetMapping
	public List<Authorities> findAll(@RequestParam("admin") Optional<Boolean> admin) {
		if (admin.orElse(false)) {
			return authoritiesService.findAuthoritiesOfAdministrators();
		}
		return authoritiesService.findAll();
	}

	@PostMapping
	public Authorities post(@RequestBody Authorities authorities) {
		return authoritiesService.create(authorities);
	}

	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") Long id) {
		authoritiesService.delete(id);
	}
}
