package com.revature.dao;

import java.util.ArrayList;
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
		Transaction tx = null;
		int pk = 0;
		try {
			tx = ses.beginTransaction();

			pk = (int) ses.save(r);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();

		} finally {
			ses.close();
		}
		return pk;

	}

	public List<Reimbursement> findAll() {

		Session ses = HibernateUtil.getSession();
		List<Reimbursement> reImbs = new ArrayList<Reimbursement>();

		try {
			reImbs = ses.createQuery("from Reimbursement", Reimbursement.class).list();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			return reImbs;
		}

		return reImbs;

	}

	public Reimbursement findReimbursementbyId(int ID) {
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		Reimbursement r = new Reimbursement();

		try {
			tx = session.beginTransaction();
			r = session.get(Reimbursement.class, ID);

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return r;
	}

	@SuppressWarnings("unchecked")
	public List<Reimbursement> findReimbursementbyAuthorId(int authorId) {

		Session ses = HibernateUtil.getSession();
		Transaction tx = null;
		List<Reimbursement> rts = new ArrayList<Reimbursement>();

		try {
			tx = ses.beginTransaction();
			rts = (List<Reimbursement>) ses.createQuery("from Reimbursement where authorId = '" + authorId + "'",
					Reimbursement.class);

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			ses.close();
		}
		return rts;
	}

	public boolean updateReimbursement(Reimbursement r) {
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		if (r.getId() < 0) {
			System.out.println("Reibursement not found in database");
			return false;
		} else {

			try {
				tx = session.beginTransaction();
				session.update(r);
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

	public boolean deleteReimbursement(Reimbursement r) {
		Session session = HibernateUtil.getSession();
		Transaction tx = null;

		if (r.getId() < 0) {
			System.out.println("Employee not found in database");
			return false;
		} else {
			try {
				tx = session.beginTransaction();
				session.delete(r);
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