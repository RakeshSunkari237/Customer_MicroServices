package com.customer.dto;

import java.util.ArrayList;
import java.util.List;

import com.plans.dto.PlansDTO;

import lombok.Data;

@Data
public class CustomerDTO {

	
	private Long phoneNumber;
	private String username;
	private String email;
	private String planId;
	private PlansDTO currentPlan;
	private List<Long> contactNumbers=new ArrayList<>();
}
