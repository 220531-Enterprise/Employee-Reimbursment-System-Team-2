package com.revature.dao;

import com.revature.enums.ReimbType;
import com.revature.enums.Role;
import com.revature.enums.Status;
import com.revature.models.Employee;
import com.revature.models.Reimbursement;
import com.revature.service.ManagerService;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ReimbursementDao rdao = new ReimbursementDao();
		EmployeeDao edao = new EmployeeDao();
		ManagerService mserv = new ManagerService(edao,rdao);
		
//		mserv.getAllEmployeeResolvedReimbursementRequest();
		
//		System.out.println(rdao.findAll());
//		System.out.println(rdao.findReimbursementbyId(1));
//		System.out.println(rdao.findReimbursementbyAuthorId(5));

		Employee e1 = new Employee(5, "Bruce", "Banner", "thehulk", "green", "bigguy@avengers.net", Role.Manager);
		
		Reimbursement r1 = new Reimbursement( 7,5555, "change", 4, 10, Status.Approved ,ReimbType.Travel);
		Reimbursement r2 = new Reimbursement( 6666, "change", 5 ,ReimbType.Travel);
//		edao.updateEmployee(e1);
//		System.out.println(rdao.deleteReimbursement(1));	
//		rdao.updateReimbursement(r1);
//		System.out.println(edao.deleteEmployee(2));
		rdao.updateReimbursement(r1);
//		rdao.insert(r2);
//		System.out.println(rdao.updateReimbursement(r1));
	
//		System.out.println(rdao.findReimbursementbyId(2));
		
		System.out.println(mserv.getAllEmployeeResolvedReimbursementRequest());
//		System.out.println(rdao.findAll());
		
	}

}
