package com.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.customer.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
	@Query(value = "select  password from customer where phone_no=?", nativeQuery = true)
	public String getPasswordByPhoneNumber(Long phoneNumber);

}
