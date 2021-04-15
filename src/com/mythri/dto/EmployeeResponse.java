package com.mythri.dto;
import java.util.ArrayList;
import java.util.List;

public class EmployeeResponse {

	private String status;
	private List<Employee> employees= new ArrayList<Employee>();

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<Employee> getEmployees() {
		return employees;
	}
	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}}
