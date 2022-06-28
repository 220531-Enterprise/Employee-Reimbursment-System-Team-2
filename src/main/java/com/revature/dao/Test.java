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
//		System.out.println(rdao.findAll());
//		System.out.println(rdao.findReimbursementbyId(1));
//		System.out.println(rdao.findReimbursementbyAuthorId(5));
		EmployeeDao edao = new EmployeeDao();
		Employee e1 = new Employee(2, "Bruce", "Banner", "thehulk", "green", "bigguy@avengers.net", Role.Employee);
		
		Reimbursement r1 = new Reimbursement( 5,5555, "change", 30, 10, Status.Approved ,ReimbType.Travel);
		Reimbursement r2 = new Reimbursement( 6666, "change", 30 ,ReimbType.Travel);
//		System.out.println(rdao.deleteReimbursement(1));	
		
//		System.out.println(edao.deleteEmployee(2));
//		rdao.insert(r2);
		System.out.println(rdao.updateReimbursement(r1));
		
	}

}
