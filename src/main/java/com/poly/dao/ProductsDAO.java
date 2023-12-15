package com.poly.dao;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import com.poly.entity.Products;

public interface ProductsDAO extends JpaRepository<Products, Long> {

	@Query(value = "select * from Products where price >= ?1 and price <=?2 order by name;", nativeQuery = true)
	Page<Products> Price2nameup(Double min,Double max,Pageable pageable);
	
	@Query(value = "select * from Products where price >= ?1 and price <=?2 order by name desc;", nativeQuery = true)
	Page<Products> Price2namedown(Double min,Double max, Pageable pageable);
	
	@Query(value = "select * from Products where price >= ?1 and price <=?2 order by price;", nativeQuery = true)
	Page<Products> Price2priceup(Double min,Double max,Pageable pageable);
	
	@Query(value = "select * from Products where price >= ?1 and price <=?2 order by price desc;", nativeQuery = true)
	Page<Products> Price2pricedown(Double min,Double max, Pageable pageable);
	
	@Query(value = "select * from Products where id in(select product_id  from Order_detail group by product_id)", nativeQuery = true)
	Page<Products> SanPhamBanChay(Pageable pageable);
	
	@Query(value = "select * from Products where id in(select product_id  from Order_detail group by product_id) and category_id =?1", nativeQuery = true)
	Page<Products> SanPhamBanChaybyid(String cid,Pageable pageable);
		
	@Query(value = "select * from Products where category_id = ?1 and price>= ?2 and price<= ?3 order by price", nativeQuery = true)
	Page<Products> priceidminmaxasc(String cid,Double min,Double max,Pageable pageable);
	
	@Query(value = "select * from Products where category_id = ?1 and price>= ?2 and price<= ?3 order by price desc", nativeQuery = true)
	Page<Products> priceidminmaxdesc(String cid,Double min,Double max,Pageable pageable);
	
	@Query(value = "select * from Products where category_id = ?1 and price>= ?2 and price<= ?3 order by name", nativeQuery = true)
	Page<Products> nameidminmaxasc(String cid,Double min,Double max,Pageable pageable);
	
	@Query(value = "select * from Products where category_id = ?1 and price>= ?2 and price<= ?3 order by name desc", nativeQuery = true)
	Page<Products> nameidminmaxdesc(String cid,Double min,Double max,Pageable pageable);	
	
	@Query(value = "select * from Products where category_id = ?1", nativeQuery = true)
	Page<Products> findByCategoryId(String cid,Pageable pageable);
	
	@Query(value = "select * from Products where category_id = ?1 order by Name desc", nativeQuery = true)
	Page<Products> sortNameDesc(String cid,Pageable pageable);
	
	Page<Products> findAllByNameLike(String keywords, Pageable pageable);
	
	@Query(value = "select * from Products where price>= ?1 and price<= ?2", nativeQuery = true)
	Page<Products> SortByPrice(Double min,Double max, Pageable pageable);
	
	@Query(value = "select * from Products where category_id = ?1 and price>= ?2 and price<= ?3", nativeQuery = true)
	Page<Products> SortbyPriceCategory(String cid,Double min,Double max, Pageable pageable);
			
	@Query(value = "select * from Products where category_id = ?1 order by Name", nativeQuery = true)
	Page<Products> HsortByField(String cid,Pageable pageable);
	
	@Query(value = "select * from Products order by Price", nativeQuery = true)
	Page<Products> DsortByPrice(Pageable pageable);
	
	@Query(value = "select * from Products order by Price desc", nativeQuery = true)
	Page<Products> UsortByPrice(Pageable pageable);
	
	@Query(value = "select * from Products  order by Name desc", nativeQuery = true)
	Page<Products> HsortByNameup(Pageable pageable);
	
	@Query(value = "select * from Products  order by Name", nativeQuery = true)
	Page<Products> HsortByNamedown(Pageable pageable);
	
	@Query(value = "select * from Products where category_id = ?1  order by Price asc;", nativeQuery = true)
	Page<Products> PsortByField(String cid,Pageable pageable);
	
	@Query(value = "select * from Products where category_id = ?1 order by Price desc;", nativeQuery = true)
	Page<Products> PDsortByField(String cid,Pageable pageable);
	
	@Query(value = "select * from Products order by CreateDate;", nativeQuery = true)
	Page<Products> CsortByField(Pageable pageable);
	
	@Query(value = "select * from Products order by ?1;", nativeQuery = true)
	Page<Products> DsortByField(String field, Pageable pageable);
	
	
	/////////////////////////////////////////////////////

	@Query(value = "select * from Products where category_id = ?1", nativeQuery = true)
	List<Products> findByCategoryId(String cid);

	@Query(value = "SELECT top 4 * FROM Products where Products.category_id = ?1 ORDER BY Products.create_date DESC;", nativeQuery = true)
	List<Products> findProductNew(String categoriesId);

	@Query(value = "SELECT top 4 * FROM Products where Products.category_id = ?1 ORDER BY Products.price DESC;", nativeQuery = true)
	List<Products> findProductDESC(String categoriesId);

	@Query(value = "SELECT top 4 * FROM Products where Products.category_id = ?1 ORDER BY Products.price ASC;", nativeQuery = true)
	List<Products> findProductASC(String categoriesId);
	
	@Query(value = "select * from Products where sale = 0;", nativeQuery = true)
	List<Products> loadAllNoSale();
	
	@Query(value = "select * from Products where sale > 0;", nativeQuery = true)
	List<Products> loadAllOnSale();
	
	@Query(value = "select * from Products where available = 'false';", nativeQuery = true)
	List<Products> loadAllNoSell();
	
	@Query(value = "select * from Products where available = 'true';", nativeQuery = true)
	List<Products> loadAllOnSell();

}
