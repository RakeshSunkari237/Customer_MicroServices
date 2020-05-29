package com.customer.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.customer.dto.CustomerDTO;
import com.customer.dto.CustomerLoginDTO;
import com.customer.dto.CustomerRegisterDTO;
import com.customer.service.ICustomerService;
import com.plans.dto.PlansDTO;

@RestController
public class CustomerRestController {

	@Autowired
	private ICustomerService service;
	
	@Autowired
	private RestTemplate restTemplate;
	
	//Plans-microservice url
	private static String PLAN_URL="http://localhost:6677/PlansMicro/plans";
	
	//Friend-microservice url
	private static String FRIEND_URL="http://localhost:5555/FriendMicro/friend";
	
	@PostMapping("/register")
	public ResponseEntity<String> addCustomer(@RequestBody CustomerRegisterDTO customerRegisterDTO){
		ResponseEntity<String> resp=null;
		try {
		boolean flag = service.isCustomerExist(customerRegisterDTO.getPhoneNumber());
		if(flag) {
			resp=new ResponseEntity<String>("Already Customer is Existed on this phoneNumber try with another number",HttpStatus.BAD_REQUEST);
			return resp;
		}else {
			String message = service.addCustomer(customerRegisterDTO);
			resp=new ResponseEntity<String>(message, HttpStatus.OK);
		}
		}catch (Exception e) {
			e.printStackTrace();
			resp=new ResponseEntity<String>("some thing wrong", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resp;
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> loginCustomer(@RequestBody CustomerLoginDTO customerLoginDto){
		ResponseEntity<String> resp=null;
		try {
			boolean flag = service.isCustomerExist(customerLoginDto.getPhoneNumber());
			if(flag) {
				String password = service.getPasswordByPhoneNumber(customerLoginDto.getPhoneNumber());
				if(customerLoginDto.getPassword().equals(password)) {
					resp=new ResponseEntity<String>("Your logined successfully...!",HttpStatus.OK);
				}else {
					resp=new ResponseEntity<String>("Enter Your password correctly",HttpStatus.BAD_REQUEST);
				}
			}else {
				resp=new ResponseEntity<String>("this Customer doesn't existed, please Register  ",HttpStatus.BAD_REQUEST);
			}
		}catch (Exception e) {
			e.printStackTrace();
			resp=new ResponseEntity<String>("some thing worng please try again ",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resp;
	}
	
	@GetMapping("/viewProfile/{phoneNumber}")
	public ResponseEntity<?> getCustomerProfile(@PathVariable Long phoneNumber){
		ResponseEntity<?> resp=null;
		try {
			CustomerRegisterDTO customerRegDto = service.getCustomerProfile(phoneNumber);
			System.out.println("cust  dto obj: "+customerRegDto);
			//copy values to CustomerDTO
			CustomerDTO customerDto=new CustomerDTO();
			BeanUtils.copyProperties(customerRegDto, customerDto);
			
			
			/* 
			 * call plan micro service to get plan based on planId 
			 * */
			ResponseEntity<PlansDTO> responseEntity= restTemplate.exchange(PLAN_URL+"/"+customerRegDto.getPlanId(), HttpMethod.GET, null, PlansDTO.class );
			PlansDTO planDto = responseEntity.getBody();
			customerDto.setCurrentPlan(planDto);
			
			/*
			 * call Friends-micro service for get contact Numbers
			 */
			ParameterizedTypeReference<List<Long>> typeRef=new ParameterizedTypeReference<List<Long>>() {
			};
			ResponseEntity<List<Long>> entity = restTemplate.exchange(FRIEND_URL+"/"+phoneNumber, HttpMethod.GET, null, typeRef);
			List<Long> contacts = entity.getBody();
			customerDto.setContactNumbers(contacts);
			System.out.println("customer dto obj :"+customerDto);
			resp=new ResponseEntity<CustomerDTO>(customerDto, HttpStatus.OK);
			return resp;
			
		}catch (Exception e) {
			e.printStackTrace();
			resp=new ResponseEntity<String>("some thing wrong please try again",HttpStatus.INTERNAL_SERVER_ERROR);
			return resp;
		}
	}
}
