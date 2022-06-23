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
		
		Session ses = HibernateUtil.getSession();
		List<Reimbursement> reImbs = ses.createQuery("from Reimbursement", Reimbursement.class).list();
		
		return reImbs;
		
	}
		
	
	   public Reimbursement findReimbursementbyId(int ID){
		      Session session = HibernateUtil.getSession();
		      Transaction tx = null;
		      Reimbursement r = null;
		      
		      try {
		         tx = session.beginTransaction();
		         r = session.get(Reimbursement.class, ID); 
		         
		      } catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      } finally {
		         session.close(); 
		      }
		      return r;
		   }
	
	   
	   public boolean updateReimbursement(Reimbursement r ){
		      Session session = HibernateUtil.getSession();
		      Transaction tx = null;
		      if (r.getId()<0) {
		    	  System.out.println("Reibursement not found in database");
		    	  return false;
		      } else {
		      
		    	  try {
		    		  tx = session.beginTransaction();
		    		  session.update(r); 
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
	   
	   
	   public boolean deleteReimbursement(Reimbursement r){
		      Session session = HibernateUtil.getSession();
		      Transaction tx = null;
		      
		      
		      if (r.getId()<0) {
		    	  System.out.println("Employee not found in database");
		    	  return false;
		      } else {
		    	  try {
		    		  tx = session.beginTransaction(); 
		    		  session.delete(r); 
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
