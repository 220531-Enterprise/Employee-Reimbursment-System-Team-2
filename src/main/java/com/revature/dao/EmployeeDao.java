package com.revature.dao;

import java.util.ArrayList;
import java.util.List;

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

		} finally {
			ses.close();
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

	public Employee findEmployeeById(int ID) {
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		Employee emp = new Employee();

		try {
			tx = session.beginTransaction();
			emp = session.get(Employee.class, ID);
			emp = (emp == null) ? new Employee(): emp ;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
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
			} finally {
				session.close();
			}
		}

	}

	public boolean deleteEmployee(Employee emp) {
		Session session = HibernateUtil.getSession();
		Transaction tx = null;

		if (emp.getId() < 0) {
			System.out.println("Employee not found in database");
			return false;
		} else {
			try {
				tx = session.beginTransaction();
				session.delete(emp);
				tx.commit();
				return true;
			} catch (HibernateException e) {
				if (tx != null)
					tx.rollback();
				e.printStackTrace();
				return false;
			} finally {
				session.close();
			}
		}
	}

}
