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

import com.revature.enums.Status;

import com.revature.models.Reimbursement;
import com.revature.service.EmployeeService;
import com.revature.service.ManagerService;
import com.revature.service.ReimbursementService;

public class ReimbursementServiceTest {

	private ManagerService mserv;
	private EmployeeService rserv;
	private EmployeeDao mmockdao;
	private ReimbursementDao rmockdao;
	private ReimbursementService rrserv;

	@Before
	public void setup() {
		mmockdao = mock(EmployeeDao.class);
		rmockdao = mock(ReimbursementDao.class);
		mserv = new ManagerService(mmockdao,rmockdao);
		rserv = new EmployeeService(rmockdao);
		rrserv = new ReimbursementService(rmockdao);

	}

	@After
	public void teardown() {
		mmockdao = null;
		rmockdao = null;
		mserv = null;
		rserv = null;
	}
	@Test
	public void testGetAll_WhenTwoRequestInDB_return2Emp() {
		Reimbursement r1 = new Reimbursement(1, 1000, null, null, "test", 20, 10, Status.Pending, ReimbType.Travel,
				null);
		Reimbursement r2 = new Reimbursement(2, 1000, null, null, "test", 20, 10, Status.Approved, ReimbType.Travel,
				null);
		List<Reimbursement> rts = new ArrayList<Reimbursement>();
		rts.add(r1);
		rts.add(r2);
		when(rmockdao.findAll()).thenReturn(rts);
		List<Reimbursement> actual = rrserv.getAll();
		List<Reimbursement> expected = rts;
		assertEquals(expected, actual);

	}
	
	@Test
	public void testinset_success() {
		Reimbursement r1 = new Reimbursement(1, 1000, null, null, "test", 20, 10, Status.Pending, ReimbType.Travel,
				null);
	
	
	
		when(rmockdao.insert(r1)).thenReturn(r1.getId());
		when(rrserv.insert(r1)).thenReturn(r1.getId());
		int actual = rrserv.insert(r1);
		int expected = r1.getId();
		assertEquals(expected, actual);

	}
	
	@Test
	public void testgetByAuthorId_success() {
		Reimbursement r1 = new Reimbursement(1, 1000, null, null, "test", 20, 10, Status.Pending, ReimbType.Travel,
				null);
		Reimbursement r2 = new Reimbursement(2, 1000, null, null, "test", 10, 10, Status.Approved, ReimbType.Travel,
				null);
		List<Reimbursement> rts = new ArrayList<Reimbursement>();
		rts.add(r1);
		rts.add(r2);
		List<Reimbursement> rts2 = new ArrayList<Reimbursement>();
		rts2.add(r1);
		when(rmockdao.findReimbursementbyAuthorId(20)).thenReturn(rts2);
		when(rrserv.getbyAuthorId(20)).thenReturn(rts2);
		List<Reimbursement> actual = rrserv.getbyAuthorId(20);
		List<Reimbursement> expected = rts;
		assertNotEquals(expected, actual);

	}
	
	
}
