package com.mythri.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mythri.dto.Employee;
import com.mythri.dto.EmployeeResponse;
import com.mythri.service.EmployeeService;


@Controller
	@RequestMapping("/rest")
	public class RestController {
 
		@Autowired
		private EmployeeService employeeService;
		//URL: <BASE_URL>/rest/employees 
		  //http method : GET
		 @RequestMapping(value = "/employees", method = RequestMethod.GET)
			public @ResponseBody EmployeeResponse getEmps() {
				EmployeeResponse empResponse = new EmployeeResponse();
				empResponse.setStatus("SUCESSS");
				List<Employee> employees = employeeService.getEmployees();
				empResponse.setEmployees(employees);
				return empResponse ;
			}
		  
	  
	  }
		


