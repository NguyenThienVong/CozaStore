package com.poly.dto;

import javax.persistence.Id;

import com.poly.entity.Products;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TopProductDTO {

	@Id
	private Products products;
	private Long sum;
	private double sold;

}
