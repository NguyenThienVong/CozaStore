package com.poly.dto;

import com.poly.entity.Order_status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RevenueOrderDTO {
	
	Order_status order_status;
	double sum;
	long count;
	
}
