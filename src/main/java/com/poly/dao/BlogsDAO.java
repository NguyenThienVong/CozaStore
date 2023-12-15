package com.poly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.entity.Blogs;

public interface BlogsDAO extends JpaRepository<Blogs, Long> {
	
	@Query(value = "select TOP 3 * from Blogs order by create_date desc;", nativeQuery = true)
	List<Blogs> load3Blogs();

}
