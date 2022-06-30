package com.revature.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.revature.dao.EmployeeDao;
import com.revature.dao.ReimbursementDao;
import com.revature.models.Employee;
import com.revature.models.Reimbursement;

public class EmployeeService {
	
	private EmployeeDao edao;
	private ReimbursementDao rdao;
	
	/**
	 * Dependency Injection via Constructor Injection
	 * 
	 * Constructor Injection is a sophisticated way of ensuring 
	 * that the EmployeeService object ALWAYS has an EmployeeDao object
	 * 
	 */
	public EmployeeService(EmployeeDao edao) {
		this.edao = edao;
		
		
		
	}
	public EmployeeService( ReimbursementDao rdao) {
		this.rdao = rdao;
		
	}
	public EmployeeService(EmployeeDao edao, ReimbursementDao rdao) {
		this.edao = edao;
		this.rdao = rdao;
		
	}
	
	/**
	 * Our Servlet will pass the username and the password to this method invocation
	 * @param username
	 * @param password
	 * @return
	 */
	public Employee confirmLogin(String username, String password) {
		
		// let's stream through all the employees that are returned
		Optional<Employee> possibleEmp = edao.findAll().stream()
				.filter(e -> (e.getUsername().equals(username) && e.getPassword().equals(password)))
				.findFirst();
		
		// IF the employee is present, return it, otherwise return empty Emp object (with id of 0)
		return (possibleEmp.isPresent() ? possibleEmp.get() : new Employee());
		// ideally you should optimize this and set up a custom exception to be returned
	}
	
	public List<Employee> getAll() {
		
		return edao.findAll();
		
	}
	public int register(Employee e) {
		return edao.insert(e);
	}
	// Use Case: An Employee can submit a reimbursement request
	public int sumbitReimbursementRequest(Reimbursement r) {
		return rdao.insert(r);
		
	}
	// An Employee can view their pending reimbursement requests
	public List<Reimbursement> getPendingReimbursementRequest(int id){
		List<Reimbursement> rts = rdao.findReimbursementbyAuthorId(id);
		rts = rts.stream().filter(rt-> rt.getStatus().toString().equals("Pending"))
		.collect(Collectors.toList());		
		return rts;
		
	}
	// An Employee can view their resolved reimbursement requests
	public List<Reimbursement> getResolvedReimbursementRequest(int id){
		List<Reimbursement> rts = rdao.findReimbursementbyAuthorId(id);
		rts = rts.stream().filter(rt-> (rt.getStatus().toString().equals("Approved")) 
				|| (rt.getStatus().toString().equals("Denied")) )
		.collect(Collectors.toList());		
		return rts;
		
	}
	// Use Case: An Employee can upload an image of his/her receipt as part of the reimbursement request (stretch goal)
	public void uploadReceiptImage() {
		// can't come out a solution for now
	}
	// Use Case: An Employee can view their information
	public Employee getInfo(int id) {
		
		return edao.findEmployeeById(id);
	}
	// Use Case: An Employee can update their information
	public boolean updateInfo(Employee e) {
		return edao.updateEmployee(e);
		
	}
	

	
	
	
	

}
