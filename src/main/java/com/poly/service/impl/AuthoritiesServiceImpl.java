package com.poly.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.dao.AuthoritiesDAO;
import com.poly.dao.AccountsDAO;
import com.poly.entity.Authorities;
import com.poly.entity.Accounts;
import com.poly.service.AuthoritiesService;

@Service
public class AuthoritiesServiceImpl implements AuthoritiesService {
	
	@Autowired
	AuthoritiesDAO authoritiesDAO;
	
	@Autowired
	AccountsDAO usersDAO;

	@Override
	public List<Authorities> findAll() {
		// TODO Auto-generated method stub
		return authoritiesDAO.findAll();
	}

	@Override
	public Authorities create(Authorities auth) {
		// TODO Auto-generated method stub
		return authoritiesDAO.save(auth);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		authoritiesDAO.deleteById(id);
	}

	@Override
	public List<Authorities> findAuthoritiesOfAdministrators() {
		// TODO Auto-generated method stub
		List<Accounts> users = usersDAO.getAdmin();
		return authoritiesDAO.authoritiesOf(users);
	}

}
