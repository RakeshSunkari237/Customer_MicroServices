package com.customer.service.impl;


import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.customer.dto.CustomerRegisterDTO;
import com.customer.entity.Customer;
import com.customer.repository.CustomerRepository;
import com.customer.service.ICustomerService;

@Service
public class CustomerServiceImpl implements ICustomerService {

	@Autowired
	private CustomerRepository repository;
	
	public String addCustomer(CustomerRegisterDTO customerRegisterDTO) {
	Customer customer=new Customer();
	BeanUtils.copyProperties(customerRegisterDTO, customer);
	repository.save(customer);
	
		return " customer: "+ customerRegisterDTO.getUsername() +" saved successfully";
	}


	@Override
	public CustomerRegisterDTO getCustomerProfile(Long phoneNumber) {
			Optional<Customer> optional = repository.findById(phoneNumber);
			Customer customer = optional.get();
			CustomerRegisterDTO customerrDTO=new CustomerRegisterDTO();
			BeanUtils.copyProperties(customer, customerrDTO);
		return customerrDTO;
	}

	@Override
	public boolean isCustomerExist(Long phoneNumber) {
		return repository.existsById(phoneNumber);
	}

	@Override
	public String getPasswordByPhoneNumber(Long phoneNumber) {
		return repository.getPasswordByPhoneNumber(phoneNumber);
	}

}
