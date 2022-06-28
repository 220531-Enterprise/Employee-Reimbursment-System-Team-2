package com.revature.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.revature.dao.EmployeeDao;
import com.revature.dao.ReimbursementDao;
import com.revature.enums.Status;
import com.revature.models.Employee;
import com.revature.models.Reimbursement;

public class ManagerService {

	private EmployeeDao mdao;
	private ReimbursementDao rdao;

	public ManagerService(EmployeeDao mdao) {

		this.mdao = mdao;

	}

	public ManagerService(ReimbursementDao rdao) {
		this.rdao = rdao;

	}

	public Employee confirmLogin(String username, String password) {

		// let's stream through all the employees that are returned
		Optional<Employee> possibleEmp = mdao.findAll().stream()
				.filter(e -> (e.getUsername().equals(username) && e.getPassword().equals(password))).findFirst();

		// IF the employee is present, return it, otherwise return empty Emp object
		// (with id of 0)
		return (possibleEmp.isPresent() ? possibleEmp.get() : new Employee());
		// ideally you should optimize this and set up a custom exception to be returned
	}

	public List<Employee> getAll() {

		return mdao.findAll();

	}

	public int register(Employee manager) {
		return mdao.insert(manager);
	}


	// Use Case: A Manager can approve/deny pending reimbursement requests
	public boolean updateReimbursementStatus(int id ,Status status ) {
	
		Reimbursement r = rdao.findReimbursementbyId(id);
		if (r == null) return false;
		r.setStatus(status);
		
		return true;
		
	}
	// Use Case: A Manager can view all pending requests from all employees
	public List<Reimbursement> getAllEmployeesPendingReimbursementRequest() {
		
		List<Reimbursement> rts = rdao.findAll();
		rts = rts.stream().filter(rt -> (rt.getStatus().toString().equals("Pending")) && 
				((mdao.findEmployeeById(rt.getAuthorId())).getRole().toString().equals("Employee")))
				.collect(Collectors.toList());
		return rts;

	}

	// Use Case: A Manager can view images of the receipts from reimbursement requests (extra credit)
	public boolean getImagesOfTheReceipts() {
		return false;
	}
	// Use Case: A Manager can view all resolved requests from all employees and see which manager resolved it
	public List<Reimbursement> getAllEmployeeResolvedReimbursementRequest(){
		List<Reimbursement> rts = rdao.findAll();
		rts = rts.stream().filter(rt -> (mdao.findEmployeeById(rt.getAuthorId()).getRole().toString().equals("Employee")) )
				.filter(rt-> (rt.getStatus().toString().equals("Approved")) || (rt.getStatus().toString().equals("Denied")) )
				.collect(Collectors.toList());
		
		return rts;
		
	}

	// Use Case: A Manager can view all Employees
	public List<Employee> getAllEmployeesInfo() {
		
		List<Employee> emps = mdao.findAll();
		
		return emps.stream().filter(emp -> emp.getRole().toString().equals("Employee")).collect(Collectors.toList());
	}
	// Use Case: A Manager can view reimbursement requests from a single Employee
	public Reimbursement getReimbursementRequestByEmployee(int id) {
		return rdao.findReimbursementbyId(id);

	}



}
