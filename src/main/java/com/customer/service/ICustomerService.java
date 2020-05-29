package com.customer.service;


import com.customer.dto.CustomerDTO;
import com.customer.dto.CustomerRegisterDTO;

public interface ICustomerService {

	public String addCustomer(CustomerRegisterDTO customerRegisterDTO);
	public boolean isCustomerExist(Long phoneNumber);
	public CustomerRegisterDTO getCustomerProfile(Long phoneNumber);
	
	public String getPasswordByPhoneNumber(Long phoneNumber);
}
