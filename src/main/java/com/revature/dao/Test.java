package com.revature.dao;

import com.revature.enums.ReimbType;
import com.revature.enums.Role;
import com.revature.enums.Status;
import com.revature.models.Employee;
import com.revature.models.Reimbursement;
import com.revature.service.EmployeeService;
import com.revature.service.ManagerService;
import com.revature.service.ReimbursementService;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ReimbursementDao rdao = new ReimbursementDao();
		EmployeeDao edao = new EmployeeDao();
		ManagerService mserv = new ManagerService(edao,rdao);
		EmployeeService eserv = new EmployeeService(edao,rdao);
		ReimbursementService rserv = new ReimbursementService(rdao);
//		mserv.getAllEmployeeResolvedReimbursementRequest();
		
//		System.out.println(rdao.findAll());
//		System.out.println(rdao.findReimbursementbyId(1));
//		System.out.println(rdao.findReimbursementbyAuthorId(5));

//		Employee e1 = new Employee(5, "Bruce", "Banner", "thehulk", "green", "bigguy@avengers.net", Role.Manager);
//		Employee e2 = new Employee(100, "t", "e", "s", "t", "test@avengers.net", Role.Employee);
//		Reimbursement r1 = new Reimbursement( 7,5555, "change", 4, 10, Status.Approved ,ReimbType.Travel);
//		Reimbursement r2 = new Reimbursement( 10000, "change", 5 ,ReimbType.Travel);
//		System.out.println(mserv.getAll().size());
//		edao.updateEmployee(e1);
//		System.out.println(rdao.deleteReimbursement(1));	
//		rdao.updateReimbursement(r1);
//		System.out.println(edao.deleteEmployee(2));
//		rdao.updateReimbursement(r1);
//		rdao.insert(r2);

		
//	
//		System.out.println(rdao.findReimbursementbyId(2));
//		System.out.println(rdao.updateReimbursement(r1));
//		System.out.println(rdao.findReimbursementbyId(2));
//		System.out.println(rdao.findReimbursementbyAuthorId(edao.findEmployeeById(5).getId()));	
//		System.out.println(mserv.updateReimbursementStatus(15, Status.Approved,"chenx23333@gmail.com"));
//		System.out.println(mserv.sendEmail("chenx23333@gmail.com"));
//		System.out.println(mserv.getReimbursementRequestByEmployee(6));
//		System.out.println(rdao.updateReimbursement(r1));
		
//		System.out.println(mserv.confirmLogin(e1.getUsername(), e1.getPassword()));
//		System.out.println(mserv.getAll());
//	
//
//		System.out.println(mserv.updateReimbursementStatus(3, Status.Approved,"chenx23333@gmail.com"));
//		System.out.println(mserv.sendEmail("chenx23333@gmail.com"));
//		
//		System.out.println(mserv.getAllEmployeesPendingReimbursementRequest());
//		System.out.println(mserv.getAllEmployeeResolvedReimbursementRequest());
//		System.out.println(mserv.getAllEmployeesInfo());
//		
//	
//		System.out.println(eserv.confirmLogin(e1.getUsername(), e1.getPassword()));
//		System.out.println(eserv.getAll());
//		System.out.println(eserv.register(e2));
//		System.out.println(eserv.sumbitReimbursementRequest(r2));
//		System.out.println(eserv.getPendingReimbursementRequest(6));
//		System.out.println(eserv.getResolvedReimbursementRequest(6));
//		System.out.println(eserv.getInfo(6));
		
	}

}
