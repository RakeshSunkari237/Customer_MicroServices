package com.customer.dto;


import lombok.Data;

@Data
public class CustomerRegisterDTO {


	private Long phoneNumber;
	private String username;
	private String password;
	private String email;
	private String planId;	
}
