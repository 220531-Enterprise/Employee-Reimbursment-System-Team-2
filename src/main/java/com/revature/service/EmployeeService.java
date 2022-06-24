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
	
	public int sumbitReimbursementRequest(Reimbursement r) {
		return rdao.insert(r);
		
	}
	public List<Reimbursement> getPendingReimbursementRequest(int authorId){
		List<Reimbursement> rts = rdao.findReimbursementbyAuthorId(authorId);
		rts = rts.stream().filter(rt-> rt.getStatus().toString().equals("Pending"))
		.collect(Collectors.toList());		
		return rts;
		
	}
	public List<Reimbursement> getResolvedReimbursementRequest(int authorId){
		List<Reimbursement> rts = rdao.findReimbursementbyAuthorId(authorId);
		rts = rts.stream().filter(rt-> (rt.getStatus().toString().equals("Approved")) 
				|| (rt.getStatus().toString().equals("Denied")) )
		.collect(Collectors.toList());		
		return rts;
		
	}
	public void uploadReceiptImage() {
		// can't come out a solution for now
	}
	public Employee getInfo(int id) {
		
		return edao.findEmployeeById(id);
	}
	
	public boolean updateInfo(Employee e) {
		return edao.updateEmployee(e);
		
	}
	public void getEmailWhenReimbursementRequestsResolved() {
		// can't come out a solution for now
	}
	
	
	
	

}
