
package com.poly.entity;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Accounts")
public class Accounts {

	@Id
	@NotBlank(message = "Không để trống tên tài khoản !")
	private String username;

	@NotBlank(message = "Không để trống mật khẩu !")
	private String password;

	private String first_name;

	private String last_name;

	private boolean gender;

	private String phone;

	@NotBlank(message = "Không để trống email !")
	@Email(message = "Định dạng email không chính xác !")
	private String email;

	private String birthday;

	private String nationality;

	private String photo;
	
	private boolean active = true;

	@JsonIgnore
	@OneToMany(mappedBy = "accounts")
	List<Orders> orders;

	@JsonIgnore
	@OneToMany(mappedBy = "accounts", fetch = FetchType.EAGER)
	List<Authorities> authorities;
	
	@JsonIgnore
	@OneToMany(mappedBy = "accounts")
	List<Reviews> review;
	
	@JsonIgnore
	@OneToMany(mappedBy = "accounts")
	List<Account_address> acccount_address;

}
