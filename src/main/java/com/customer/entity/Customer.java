package com.customer.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Customer {

	@Id
	@Column(name="phone_no")
	private Long phoneNumber;
	
	private String username;
	
	private String password;
	private String email;
	
	private String planId;
	
}
