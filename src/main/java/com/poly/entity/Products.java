
package com.poly.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Products")
public class Products {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private Double price;
	
	private Double sale;
	
	private Integer quantity;

	@Temporal(TemporalType.DATE)
	private Date create_date;

	private String describe;
	
	private String image;
	
	private String image1;
	
	private String image2;
	
	private String image3;
	
	private String image4;

	private boolean available;
		

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Categories categories;
	
	@JsonIgnore
	@OneToMany(mappedBy = "products")
	private List<Order_detail> order_detail;
	
	@JsonIgnore
	@OneToMany(mappedBy = "products")
	private List<Reviews> reviews;

}
