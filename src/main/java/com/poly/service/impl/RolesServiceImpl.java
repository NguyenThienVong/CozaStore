package com.poly.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.dao.RolesDAO;
import com.poly.entity.Roles;
import com.poly.service.RolesService;

@Service
public class RolesServiceImpl implements RolesService {
	
	@Autowired
	RolesDAO rolesDAO;

	@Override
	public List<Roles> findAll() {
		// TODO Auto-generated method stub
		return rolesDAO.findAll();
	}

}
