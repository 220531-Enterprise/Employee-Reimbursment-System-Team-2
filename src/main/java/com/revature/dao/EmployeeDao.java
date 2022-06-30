package com.revature.dao;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.models.Employee;

import com.revature.util.HibernateUtil;

// servlet -> calls service --> calls dao
public class EmployeeDao {

	// CRUD methods

	// Create (think that in the service layer we'll have a REGISTER()
	public int insert(Employee emp) {

		// grab the session object
		Session ses = HibernateUtil.getSession();
		// begin a tx
		Transaction tx = null;
		// capture the pk returned when the session method save() is called
		int pk = 0;
		try {
			tx = ses.beginTransaction();
			pk = (int) ses.save(emp);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();

		} 
		

		// return the pk

		return pk;

	}

	// Read
	public List<Employee> findAll() {

		Session ses = HibernateUtil.getSession();
		List<Employee> emps = new ArrayList<Employee>();
		try {
			emps = ses.createQuery("from Employee", Employee.class).list();
		} catch (HibernateException e) {
			e.printStackTrace();
			return emps;
		} 
		return emps;
	}
	@Transactional
	public Employee findEmployeeById(int ID) {
		
		
	
		Employee emp = new Employee();

		try(Session session = HibernateUtil.getSession()) {
		
			
			emp = session.get(Employee.class, ID);
			emp = (emp == null) ? new Employee(): emp ;
		} catch (HibernateException e) {
		
			e.printStackTrace();
		} 
		
		return emp;
	}

	public boolean updateEmployee(Employee emp) {
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		if (emp.getId() < 0) {
			System.out.println("Employee not found in database");
			return false;
		} else {

			try {
				tx = session.beginTransaction();
				session.saveOrUpdate(emp);
				tx.commit();
				return true;
			} catch (HibernateException e) {
				if (tx != null)
					tx.rollback();
				e.printStackTrace();
				return false;
			} 
		}

	}

	public boolean deleteEmployee(int id) {
		Session session = HibernateUtil.getSession();
		Transaction tx = null;

		if (id < 0) {
			System.out.println("Employee not found in database");
			return false;
		} else {
			try {
				tx = session.beginTransaction();
				Employee target = session.get(Employee.class, id);
				session.delete(target);
				tx.commit();
				return true;
			} catch (HibernateException e) {
				if (tx != null)
					tx.rollback();
				e.printStackTrace();
				return false;
			} 
		}
	}

}
