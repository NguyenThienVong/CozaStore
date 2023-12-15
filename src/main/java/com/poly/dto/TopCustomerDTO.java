package com.poly.dto;


import com.poly.entity.Accounts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TopCustomerDTO {

	private Accounts accounts;
    private long count;
    private double sum;

    
}