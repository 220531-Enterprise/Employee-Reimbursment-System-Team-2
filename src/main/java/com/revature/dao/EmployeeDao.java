package com.revature.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.models.Employee;
import com.revature.models.Reimbursement;
import com.revature.util.HibernateUtil;

// servlet -> calls service --> calls dao
public class EmployeeDao {
	
	// CRUD methods
	
	// Create (think that in the service layer we'll have a REGISTER()
	public int insert(Employee e) {
		
		// grab the session object
		Session ses = HibernateUtil.getSession();
		// begin a tx
		Transaction tx = ses.beginTransaction();
		// capture the pk returned when the session method save() is called
		int pk = (int) ses.save(e);
		// return the pk
		tx.commit();
		return pk;
		
	}
	
	// Read
	public List<Employee> findAll() {
		
		Session ses = HibernateUtil.getSession();
		List<Employee> emps = ses.createQuery("from Employee", Employee.class).list(); 
		return emps;
	}
	
	
	public Employee findEmployeeById(int ID){
	      Session session = HibernateUtil.getSession();
	      Transaction tx = null;
	      Employee emp = null;
	      
	      try {
	         tx = session.beginTransaction();
	         emp = session.get(Employee.class, ID);   
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	      }
	      return emp;
	   }
	
	   
	   public boolean updateEmployee(Employee emp ){
	      Session session = HibernateUtil.getSession();
	      Transaction tx = null;
	      if (emp.getId()<0) {
	    	  System.out.println("Employee not found in database");
	    	  return false;
	      } else {
	      
	    	  try {
	    		  tx = session.beginTransaction();
	    		  session.update(emp); 
	    		  tx.commit();
	    		  return true;
	    	  } catch (HibernateException e) {
	    		  if (tx!=null) tx.rollback();
	    		  e.printStackTrace();
	    		  return false;
	    	  } finally {
	    		  session.close(); 
	    	  }
	      }
	      
	   }
	   
	   
	 public boolean deleteEmployee(Employee emp){
	      Session session = HibernateUtil.getSession();
	      Transaction tx = null;
	      
	      if (emp.getId()<0) {
	    	  System.out.println("Employee not found in database");
	    	  return false;
	      } else {
	    	  try {
	    		  tx = session.beginTransaction(); 
	    		  session.delete(emp); 
	    		  tx.commit();
	    		  return true;
	    	  } catch (HibernateException e) {
	    		  if (tx!=null) tx.rollback();
	    		  e.printStackTrace(); 
	    		  return false;
	    	  } finally {
	    		  session.close(); 
	    	  }
	      }
	   }
	 
	 


}