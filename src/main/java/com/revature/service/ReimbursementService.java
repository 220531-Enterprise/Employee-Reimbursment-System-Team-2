package com.revature.service;

import java.util.List;

import com.revature.dao.ReimbursementDao;
import com.revature.models.Reimbursement;

public class ReimbursementService {
	
	private ReimbursementDao rdao;
	
	public ReimbursementService( ReimbursementDao rdao) {
		this.rdao = rdao;
		
	}
	public List<Reimbursement> getbyAuthorId(int authorId){
		return rdao.findReimbursementbyAuthorId(authorId);
	}

	public List<Reimbursement> getAll(){
		return rdao.findAll();
	}
	
	public int insert(Reimbursement r) {
		return rdao.insert(r);
	}
	
}
