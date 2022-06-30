package com.revature.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
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

public class EmployeeServiceTests {

	private EmployeeService eserv;
	private EmployeeService rserv;
	private EmployeeDao emockdao;
	private ReimbursementDao rmockdao;

	@Before
	public void setup() {
		emockdao = mock(EmployeeDao.class);
		rmockdao = mock(ReimbursementDao.class);
		eserv = new EmployeeService(emockdao,rmockdao);
		eserv = new EmployeeService(emockdao);
		rserv = new EmployeeService(rmockdao);

	}

	@After
	public void teardown() {
		emockdao = null;
		rmockdao = null;
		eserv = null;
	}

	@Test
	public void testConfirmLogin_WhenUsernameAndPasswordMathch_returnEmp() {
		Employee e1 = new Employee(20, "Bruce", "Banner", "thehulk", "green", "bigguy@avengers.net", Role.Employee);
		Employee e2 = new Employee(21, "Clint", "Barton", "hawkeye", "arrows", "bowsareviableweapson@avengers.net",
				Role.Employee);
		List<Employee> emps = new ArrayList<Employee>();
		emps.add(e1);
		emps.add(e2);
		when(emockdao.findAll()).thenReturn(emps);
		Employee actual = eserv.confirmLogin("thehulk", "green");
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
		when(emockdao.findAll()).thenReturn(emps);
		Employee actual = eserv.confirmLogin("thehulk", "blue");
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
		when(emockdao.findAll()).thenReturn(emps);
		List<Employee> actual = eserv.getAll();
		List<Employee> expected = emps;
		assertEquals(expected, actual);

	}

	@Test
	public void testGetAll_WhenNoUserInDB_returnEmptyObj() {

		List<Employee> emps = new ArrayList<Employee>();

		when(emockdao.findAll()).thenReturn(emps);
		List<Employee> actual = eserv.getAll();
		List<Employee> expected = emps;
		assertEquals(expected, actual);

	}

	@Test
	public void testRegister_WhenValidEmpTryToRegister_returnId() {
		Employee e1 = new Employee(20, "Bruce", "Banner", "thehulk", "green", "bigguy@avengers.net", Role.Employee);

		when(emockdao.insert(e1)).thenReturn(e1.getId());
		int actual = eserv.register(e1);
		int expected = e1.getId();
		assertEquals(expected, actual);

	}

	@Test
	public void testRegister_WhenNullEmpTryToRegister_return0() {
		Employee e1 = new Employee(20, "Bruce", "Banner", "thehulk", "green", "bigguy@avengers.net", Role.Employee);

		when(emockdao.insert(e1)).thenReturn(0);
		int actual = eserv.register(null);
		int expected = 0;
		assertEquals(expected, actual);

	}

	@Test
	public void testgetPendingReimbursementRequest_When2RtWithStatusPending_return2Rt() {
		Employee e1 = new Employee(20, "Bruce", "Banner", "thehulk", "green", "bigguy@avengers.net", Role.Employee);
		Reimbursement r1 = new Reimbursement(1, 1000, null, null, "test", 20, 10, Status.Pending, ReimbType.Travel,
				null);
		Reimbursement r2 = new Reimbursement(2, 1000, null, null, "test", 20, 10, Status.Approved, ReimbType.Travel,
				null);
		List<Reimbursement> rts = new ArrayList<Reimbursement>();
		rts.add(r1);

		when(emockdao.insert(e1)).thenReturn(20);
		when(rmockdao.insert(r1)).thenReturn(1);
		when(rmockdao.insert(r2)).thenReturn(2);
		when(rmockdao.findReimbursementbyAuthorId(e1.getId())).thenReturn(rts);
		int pk = eserv.register(e1);
		rserv.sumbitReimbursementRequest(r1);
		rserv.sumbitReimbursementRequest(r2);
		List<Reimbursement> actual = rserv.getPendingReimbursementRequest(pk);

		List<Reimbursement> expected = rts;
		assertEquals(expected, actual);

	}

	@Test
	public void testgetPendingReimbursementRequest_When1RtWithStatusPending_return1Rt() {
		Employee e1 = new Employee(20, "Bruce", "Banner", "thehulk", "green", "bigguy@avengers.net", Role.Employee);
		Reimbursement r1 = new Reimbursement(1, 1000, null, null, "test", 20, 10, Status.Pending, ReimbType.Travel,
				null);
		Reimbursement r2 = new Reimbursement(2, 1000, null, null, "test", 20, 10, Status.Approved, ReimbType.Travel,
				null);
		List<Reimbursement> rts = new ArrayList<Reimbursement>();
		rts.add(r1);
		rts.add(r2);
		when(emockdao.insert(e1)).thenReturn(20);
		when(rmockdao.insert(r1)).thenReturn(1);
		when(rmockdao.insert(r2)).thenReturn(2);
		when(rmockdao.findReimbursementbyAuthorId(e1.getId())).thenReturn(rts);
		int pk = eserv.register(e1);
		rserv.sumbitReimbursementRequest(r1);
		rserv.sumbitReimbursementRequest(r2);
		List<Reimbursement> actual = rserv.getPendingReimbursementRequest(pk);

		List<Reimbursement> expected = rts;

		assertNotEquals(expected, actual);

	}

	@Test
	public void testgetResolvedReimbursementRequest_When2RtWithResolvedRequest_return2Rt() {
		Employee e1 = new Employee(20, "Bruce", "Banner", "thehulk", "green", "bigguy@avengers.net", Role.Employee);
		Reimbursement r1 = new Reimbursement(1, 1000, null, null, "test", 20, 10, Status.Denied, ReimbType.Travel,
				null);
		Reimbursement r2 = new Reimbursement(2, 1000, null, null, "test", 20, 10, Status.Approved, ReimbType.Travel,
				null);
		List<Reimbursement> rts = new ArrayList<Reimbursement>();
		rts.add(r1);
		rts.add(r2);
		when(emockdao.insert(e1)).thenReturn(20);
		when(rmockdao.insert(r1)).thenReturn(1);
		when(rmockdao.insert(r2)).thenReturn(2);
		when(rmockdao.findReimbursementbyAuthorId(e1.getId())).thenReturn(rts);
		int pk = eserv.register(e1);
		rserv.sumbitReimbursementRequest(r1);
		rserv.sumbitReimbursementRequest(r2);
		List<Reimbursement> actual = rserv.getResolvedReimbursementRequest(pk);

		List<Reimbursement> expected = rts;

		assertEquals(expected, actual);

	}

	@Test
	public void testgetResolvedReimbursementRequest_WhenOnlyOneRtWithStatusApproved_returnRtWithId2() {

		Employee e1 = new Employee(20, "Bruce", "Banner", "thehulk", "green", "bigguy@avengers.net", Role.Employee);
		Reimbursement r1 = new Reimbursement(1, 1000, null, null, "test", 20, 10, Status.Pending, ReimbType.Travel,
				null);
		Reimbursement r2 = new Reimbursement(2, 1000, null, null, "test", 20, 10, Status.Approved, ReimbType.Travel,
				null);
		List<Reimbursement> rts = new ArrayList<Reimbursement>();

		rts.add(r2);
		when(emockdao.insert(e1)).thenReturn(20);
		when(rmockdao.insert(r1)).thenReturn(1);
		when(rmockdao.insert(r2)).thenReturn(2);
		when(rmockdao.findReimbursementbyAuthorId(e1.getId())).thenReturn(rts);
		int pk = eserv.register(e1);
		rserv.sumbitReimbursementRequest(r1);
		rserv.sumbitReimbursementRequest(r2);
		List<Reimbursement> actual = rserv.getResolvedReimbursementRequest(pk);

		List<Reimbursement> expected = rts;
		assertEquals(expected, actual);

	}

	@Test
	public void testgetInfo_WhenEmpWithId20inDB_returnEmpWithId20() {
		Employee e1 = new Employee(20, "Bruce", "Banner", "thehulk", "green", "bigguy@avengers.net", Role.Employee);
		when(emockdao.findEmployeeById(e1.getId())).thenReturn(e1);
		Employee expected = e1;
		eserv.register(e1);
		Employee actual = eserv.getInfo(e1.getId());

		assertEquals(expected, actual);

	}

	@Test
	public void testgetInfo_WhenNoOneInDB_returnEmptyEmpObj() {

		when(emockdao.findEmployeeById(20)).thenReturn(new Employee());
		Employee expected = new Employee();
		Employee actual = eserv.getInfo(20);
		assertEquals(expected, actual);

	}

	@Test
	public void testupdateInfo_WhenEmpWithId20InDB_returnTrue() {
		Employee e1 = new Employee(20, "Bruce", "Banner", "thehulk", "green", "bigguy@avengers.net", Role.Employee);
		Employee e2 = new Employee();
		e2 = new Employee(e1.getFirstName(),e1.getLastName(),e1.getUsername(),e1.getPassword(),e1.getEmail(),e1.getRole());
		e2 = new Employee(e1.getId(),e1.getFirstName(),e1.getLastName(),e1.getUsername(),e1.getPassword(),e1.getEmail(),e1.getRole());
		e2.setId(e1.getId());
		e2.setFirstName(e1.getFirstName());
		e2.setLastName(e1.getLastName());
		e2.setUsername(e2.getUsername());
		e2.setPassword(e1.getPassword());
		e2.setEmail(e1.getEmail());
		e2.setRole(e1.getRole());
		when(emockdao.updateEmployee(e2)).thenReturn(true);
		boolean expected = true;
		boolean actual = eserv.updateInfo(new Employee(e1.getId(),e1.getFirstName(),e1.getLastName(),e1.getUsername(),e1.getPassword(),e1.getEmail(),e1.getRole()));
		assertEquals(expected, actual);
	}

	public void testupdateInfo_WhenEmpWithIdLessThan0InDB_returnFalse() {
		Employee e1 = new Employee(-1, "Bruce", "Banner", "thehulk", "green", "bigguy@avengers.net", Role.Employee);
		
		when(emockdao.updateEmployee(e1)).thenReturn(false);
		boolean expected = false;
		boolean actual = eserv.updateInfo(e1);
		assertEquals(expected, actual);
	}
	

}
