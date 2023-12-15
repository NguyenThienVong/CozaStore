
package com.poly.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
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

@Table(name = "Vouchers")
public class Vouchers {

	@Id
	private String id;
	
	private Double price;
	
	private String decribe;
	
	@Temporal(TemporalType.DATE)
	private Date start_date;
	
	@Temporal(TemporalType.DATE)
	private Date end_date;
	
	private Integer quantity;

	private boolean active = true;
	
	@JsonIgnore
	@OneToMany(mappedBy = "vouchers")
	List<Account_voucher> account_vouchers;
		
}
