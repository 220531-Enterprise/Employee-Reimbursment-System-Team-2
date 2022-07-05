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
import com.revature.enums.ReimbType;
import com.revature.enums.Role;
import com.revature.enums.Status;
import com.revature.models.Employee;
import com.revature.models.Reimbursement;
import com.revature.service.EmployeeService;
import com.revature.service.ManagerService;

public class ManagerServiceTests {

	private ManagerService mserv;
	private EmployeeService rserv;
	private EmployeeDao mmockdao;
	private ReimbursementDao rmockdao;

	@Before
	public void setup() {
		mmockdao = mock(EmployeeDao.class);
		rmockdao = mock(ReimbursementDao.class);
		mserv = new ManagerService(mmockdao);
		mserv = new ManagerService(rmockdao);
		mserv = new ManagerService(mmockdao,rmockdao);
		rserv = new EmployeeService(rmockdao);

	}

	@After
	public void teardown() {
		mmockdao = null;
		rmockdao = null;
		mserv = null;
		rserv = null;
	}

	@Test
	public void testConfirmLogin_WhenUsernameAndPasswordMathch_returnMngr() {
		Employee e1 = new Employee(20, "Bruce", "Banner", "thehulk", "green", "bigguy@avengers.net", Role.Employee);
		Employee e2 = new Employee(21, "Clint", "Barton", "hawkeye", "arrows", "bowsareviableweapson@avengers.net",
				Role.Employee);
		List<Employee> emps = new ArrayList<Employee>();
		emps.add(e1);
		emps.add(e2);
		when(mmockdao.findAll()).thenReturn(emps);
		Employee actual = mserv.confirmLogin("thehulk", "green");
		Employee expected = e1;

		assertEquals(expected, actual);
	}

	@Test
	public void testConfirmLogin_WhenPasswordIsIncorrect_returnEmptyObj() {
		Employee e1 = new Employee(20, "Bruce", "Banner", "thehulk", "green", "bigguy@avengers.net", Role.Employee);
		Employee e2 = new Employee(21, "Clint", "Barton", "hawkeye", "arrows", "bowsareviableweapson@avengers.net",
				Role.Employee);
		List<Employee> emps = new ArrayList<Employee>();
		emps.add(e1);
		emps.add(e2);
		when(mmockdao.findAll()).thenReturn(emps);
		Employee actual = mserv.confirmLogin("thehulk", "blue");
		Employee expected = new Employee();

		assertEquals(expected, actual);

	}

	@Test
	public void testGetAll_WhenTwoEmpInDB_return2Emp() {
		Employee e1 = new Employee(20, "Bruce", "Banner", "thehulk", "green", "bigguy@avengers.net", Role.Employee);
		Employee e2 = new Employee(21, "Clint", "Barton", "hawkeye", "arrows", "bowsareviableweapson@avengers.net",
				Role.Employee);
		List<Employee> emps = new ArrayList<Employee>();
		emps.add(e1);
		emps.add(e2);
		when(mmockdao.findAll()).thenReturn(emps);
		List<Employee> actual = mserv.getAll();
		List<Employee> expected = emps;
		assertEquals(expected, actual);

	}

	@Test
	public void testGetAll_WhenNoUserInDB_returnEmptyObj() {

		List<Employee> emps = new ArrayList<Employee>();

		when(mmockdao.findAll()).thenReturn(emps);
		List<Employee> actual = mserv.getAll();
		List<Employee> expected = emps;
		assertEquals(expected, actual);

	}

	@Test
	public void testRegister_WhenValidEmpTryToRegister_returnId() {
		Employee e1 = new Employee(20, "Bruce", "Banner", "thehulk", "green", "bigguy@avengers.net", Role.Manager);

		when(mmockdao.insert(e1)).thenReturn(e1.getId());
		int actual = mserv.register(e1);
		int expected = e1.getId();
		assertEquals(expected, actual);

	}

	@Test
	public void testRegister_WhenNullEmpTryToRegister_return0() {
		Employee e1 = new Employee(20, "Bruce", "Banner", "thehulk", "green", "bigguy@avengers.net", Role.Employee);

		when(mmockdao.insert(e1)).thenReturn(0);
		int actual = mserv.register(null);
		int expected = 0;
		assertEquals(expected, actual);

	}
	
	@Test
	public void testgetAllEmployeesInfo_WhenOnly1EmpinDb_return1Emp() {
		Employee e1 = new Employee(20, "Bruce", "Banner", "thehulk", "green", "bigguy@avengers.net", Role.Employee);
		Employee e2 = new Employee(21, "Clint", "Barton", "hawkeye", "arrows", "bowsareviableweapson@avengers.net",
				Role.Employee);
		List<Employee> emps = new ArrayList<Employee>();
		emps.add(e1);
		emps.add(e2);
		when(mmockdao.findAll()).thenReturn(emps);
		List<Employee> actual = mserv.getAllEmployeesInfo();
		List<Employee> expected = emps;
		assertEquals(expected, actual);
		
	}
	@Test
	public void testupdateReimbursementStatus_success() {
		Reimbursement r1 = new Reimbursement(1, 1000, null, null, "test", 20, 10, Status.Approved, ReimbType.Travel,
		null);
		String email = "chenx23333@gmail.com";
		Reimbursement r2 =  new Reimbursement();
		r2.setId(r1.getId());
		r2.setAmount(r1.getAmount());
		r2.setDate_submitted(r1.getDate_submitted());
		r2.setDate_resolved(r2.getDate_resolved());
		r2.setDescription(r1.getDescription());
		r2.setAuthorId(r1.getAuthorId());
		r2.setResolverId(r1.getResolverId());
		r2.setStatus(r1.getStatus());
		r2.setType(r1.getType());
		when(rmockdao.findReimbursementbyId(r1.getId())).thenReturn(r1);
		when(rmockdao.updateReimbursement(r1)).thenReturn(true);
		boolean actual = true;
		boolean expected = mserv.updateReimbursementStatus(r1.getId(), 5,Status.Approved,email);
	
		assertEquals(expected, actual);
	}



	@Test
	public void testgetAllEmployeesPendingReimbursementRequest_When1EmpWithPendingReimbursementRequestInDB_returnListObj() {

		
		Reimbursement r1 = new Reimbursement(1, 1000, null, null, "test", 20, 10, Status.Pending, ReimbType.Travel,
				null);
		Reimbursement r2 = new Reimbursement(2, 1000, null, null, "test", 20, 10, Status.Approved, ReimbType.Travel,
				null);
		Employee e1 = new Employee(20, "Bruce", "Banner", "thehulk", "green", "bigguy@avengers.net", Role.Employee);

		List<Reimbursement> rts = new ArrayList<Reimbursement>();
		rts.add(r1);
		List<Reimbursement> rts2 = new ArrayList<Reimbursement>();
		rts2.add(r1);
		rts2.add(r2);
		List<Employee> emps = new ArrayList<Employee>();
		emps.add(e1);
	
		List<Reimbursement> expected = rts;
		when(rmockdao.findAll()).thenReturn(rts2);
		when(mmockdao.findAll()).thenReturn(emps);
		List<Reimbursement> actual = mserv.getAllEmployeesPendingReimbursementRequest();

		
		assertEquals(expected, actual);
	}
	@Test
	public void testgetAllEmployeesPendingReimbursementRequest_When1MngrWithPendingReimbursementRequestInDB_returnListObj() {

		
		Reimbursement r1 = new Reimbursement(1, 1000, null, null, "test", 20, 10, Status.Pending, ReimbType.Travel,
				null);
		Reimbursement r2 = new Reimbursement(2, 1000, null, null, "test", 20, 10, Status.Approved, ReimbType.Travel,
				null);
		Employee e1 = new Employee(20, "Bruce", "Banner", "thehulk", "green", "bigguy@avengers.net", Role.Manager);

		List<Reimbursement> rts = new ArrayList<Reimbursement>();
		rts.add(r1);
		List<Reimbursement> rts2 = new ArrayList<Reimbursement>();
		rts2.add(r1);
		rts2.add(r2);
		List<Employee> emps = new ArrayList<Employee>();
		emps.add(e1);
	
		List<Reimbursement> expected = new ArrayList<Reimbursement>();;
		when(rmockdao.findAll()).thenReturn(rts2);
		when(mmockdao.findAll()).thenReturn(emps);
		List<Reimbursement> actual = mserv.getAllEmployeesPendingReimbursementRequest();

		
		assertEquals(expected, actual);
	}
	
	
	
	@Test
	public void testgetAllEmployeeResolvedReimbursementRequest_When1EmpWithApprovedReimbursementRequestInDB_returnListObj() {

		
		Reimbursement r1 = new Reimbursement(1, 1000, null, null, "test", 20, 10, Status.Pending, ReimbType.Travel,
				null);
		Reimbursement r2 = new Reimbursement(2, 1000, null, null, "test", 20, 10, Status.Approved, ReimbType.Travel,
				null);
		Employee e1 = new Employee(20, "Bruce", "Banner", "thehulk", "green", "bigguy@avengers.net", Role.Employee);

		List<Reimbursement> rts = new ArrayList<Reimbursement>();
		rts.add(r2);
		List<Reimbursement> rts2 = new ArrayList<Reimbursement>();
		rts2.add(r1);
		rts2.add(r2);
		List<Employee> emps = new ArrayList<Employee>();
		emps.add(e1);
	
		List<Reimbursement> expected = rts;
		when(rmockdao.findAll()).thenReturn(rts2);
		when(mmockdao.findAll()).thenReturn(emps);
		List<Reimbursement> actual = mserv.getAllEmployeeResolvedReimbursementRequest();
	
		
		assertEquals(expected, actual);
	}
	@Test
	public void testgetAllEmployeeResolvedReimbursementRequest_When1MngrWithApprovedReimbursementRequestInDB_returnListObj() {

		
		Reimbursement r1 = new Reimbursement(1, 1000, null, null, "test", 20, 10, Status.Pending, ReimbType.Travel,
				null);
		Reimbursement r2 = new Reimbursement(2, 1000, null, null, "test", 20, 10, Status.Approved, ReimbType.Travel,
				null);
		Employee e1 = new Employee(20, "Bruce", "Banner", "thehulk", "green", "bigguy@avengers.net", Role.Manager);

		List<Reimbursement> rts = new ArrayList<Reimbursement>();
		rts.add(r2);
		List<Reimbursement> rts2 = new ArrayList<Reimbursement>();
		rts2.add(r1);
		rts2.add(r2);
		List<Employee> emps = new ArrayList<Employee>();
		emps.add(e1);
	
		List<Reimbursement> expected = new ArrayList<Reimbursement>();
		when(rmockdao.findAll()).thenReturn(rts2);
		when(mmockdao.findAll()).thenReturn(emps);
		List<Reimbursement> actual = mserv.getAllEmployeeResolvedReimbursementRequest();
	
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testgetReimbursementRequestByEmployee_success() {
		Reimbursement r1 = new Reimbursement(1, 1000, null, null, "test", 20, 10, Status.Pending, ReimbType.Travel,
				null);
		List<Reimbursement> rts = new ArrayList<Reimbursement>();
		rts.add(r1);
		when(mserv.getReimbursementRequestByEmployee(20)).thenReturn(rts);
		List<Reimbursement> actual = rts;
		List<Reimbursement> expected = mserv.getReimbursementRequestByEmployee(20);
		assertEquals(expected, actual);
	}


}
