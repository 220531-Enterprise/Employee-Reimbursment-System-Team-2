package com.revature.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.enums.Status;
import com.revature.models.Employee;
import com.revature.models.Reimbursement;
import com.revature.util.HibernateUtil;

// servlet -> calls service --> calls dao
public class ReimbursementDao {
	
	
	
	public int insert(Reimbursement r) {
		
		Session ses = HibernateUtil.getSession();
		Transaction tx = ses.beginTransaction();
		int pk = (int) ses.save(r);
		tx.commit();
		return pk;
		
	}
	
	public List<Reimbursement> findAll() {
		
		// grab the session
		Session ses = HibernateUtil.getSession();
		
		// make an HQL -- Hibernate Query Language: odd mix of OOP & native SQL
		 List<Reimbursement> reImbs = ses.createQuery("from Reimbursement", Reimbursement.class).list();
		
		 // return the list of employees
		return reImbs;
		
	}
	
	
	   public void deleteReimbursement(Integer ID){
		      Session session = HibernateUtil.getSession();
		      Transaction tx = null;
		      
		      try {
		         tx = session.beginTransaction();
		         Reimbursement r = session.get(Reimbursement.class, ID); 
		         session.delete(r); 
		         tx.commit();
		      } catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      } finally {
		         session.close(); 
		      }
		   }
	
	   
	   public void updateStatus(Integer ID, Status status, int resolverId ){
		      Session session = HibernateUtil.getSession();
		      Transaction tx = null;
		      
		      try {
		         tx = session.beginTransaction();
		         Reimbursement r = session.get(Reimbursement.class, ID); 
		         r.setStatus( status );
		         r.setResolverId(0);
				 session.update(resolverId); 
		         tx.commit();
		      } catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      } finally {
		         session.close(); 
		      }
		   }

//	public List<Reimbursement> getReimbursmentsByID (int ID)  
//	{  
//	    Session ses = HibernateUtil.getSession(); 
//	  
//	    List<Reimbursement> list = ses.get(Reimbursement.class, ID).list(); 
//	     
//	      
//	    return list;  
//	}
//		
	
	
	// Read
//	public List<Employee> findAll() {
//		
//		// grab the session
//		Session ses = HibernateUtil.getSession();
//		
//		// make an HQL -- Hibernate Query Language: odd mix of OOP & native SQL
//		 List<Employee> emps = ses.createQuery("from Employee", Employee.class).list();
//		
//		 // return the list of employees
//		return emps;
//		
//	}
	
	
	
	public boolean update(Employee e) {
		return false;
	}
	

}
