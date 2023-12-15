package com.poly.service;

import java.util.List;

import com.poly.entity.Authorities;

public interface AuthoritiesService {
	
	public List<Authorities> findAll();

	public Authorities create(Authorities auth);

	public void delete(Long id);

	public List<Authorities> findAuthoritiesOfAdministrators();
	

}
