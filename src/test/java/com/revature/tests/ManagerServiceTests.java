package com.revature.tests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.revature.dao.EmployeeDao;
import com.revature.dao.ReimbursementDao;
import com.revature.enums.Role;
import com.revature.models.Employee;
import com.revature.service.EmployeeService;

public class ManagerServiceTests {

	private EmployeeService mserv;
	private EmployeeService rserv;
	private EmployeeDao emockdao;
	private ReimbursementDao rmockdao;

	@Before
	public void setup() {
		emockdao = mock(EmployeeDao.class);
		rmockdao = mock(ReimbursementDao.class);
		mserv = new EmployeeService(emockdao);
		rserv = new EmployeeService(rmockdao);
		
		
	}
	
	@After
	public void teardown() {
		emockdao = null;
		rmockdao = null;
		mserv = null;
	}
	
	@Test
	public void testConfirmLogin_WhenUsernameAndPasswordMathch_returnMngr() {
		Employee e1 = new Employee(20,"Bruce","Banner","thehulk","green", "bigguy@avengers.net", Role.Employee);
		Employee e2 = new Employee(21,"Clint","Barton","hawkeye","arrows", "bowsareviableweapson@avengers.net", Role.Employee);
		List<Employee> emps = new ArrayList<Employee>();
		emps.add(e1);
		emps.add(e2);
		when(emockdao.findAll()).thenReturn(emps);
		Employee actual = mserv.confirmLogin("thehulk","green");
		Employee expected = e1;
		
		assertEquals(expected,actual);
	}
	
	@Test
	public void testConfirmLogin_WhenPasswordIsIncorrect_returnEmptyObj() {
		Employee e1 = new Employee(20,"Bruce","Banner","thehulk","green", "bigguy@avengers.net", Role.Employee);
		Employee e2 = new Employee(21,"Clint","Barton","hawkeye","arrows", "bowsareviableweapson@avengers.net", Role.Employee);
		List<Employee> emps = new ArrayList<Employee>();
		emps.add(e1);
		emps.add(e2);
		when(emockdao.findAll()).thenReturn(emps);
		Employee actual = mserv.confirmLogin("thehulk","blue");
		Employee expected = new Employee();
		
		assertEquals(expected,actual);
		
	}
	
}
