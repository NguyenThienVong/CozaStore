
package com.poly.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "Orders")
public class Orders {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "username")
	private Accounts accounts;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "status_id")
	private Order_status order_status;
	
	@ManyToOne
	@JoinColumn(name = "address_id")
	private Account_address account_address;	
	
	private String ship_phone;

	private String ship_address;

	private String ship_notes;
	
	private String ship_ward;
	
	private String ship_district;
	
	private String ship_province;
	
	private Double ship_fee;
	
	private String ship_delivery;
	
	private String ship_pay;
	
	@Temporal(TemporalType.DATE)
	private Date create_date;

	private Double total;
	
	@JsonIgnore
	@OneToMany(mappedBy = "orders",  cascade = CascadeType.ALL)
	List<Order_detail> orderDetails;
	
	

}
