package com.revature.models;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import com.revature.enums.ReimbType;
import com.revature.enums.Status;

@Entity
@Table(name="reimbursement")
public class Reimbursement { //TODO set up Hibernate for this class
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	
	private double amount;
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", nullable=false, updatable=false)
	private java.util.Date dateSubmitted;
	
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column (name = "modified_date")
    private java.util.Date dateResolved;
	
	
	@Column(length=250)
	private String description;
	
	@Column(name="author_id")
	private int authorId;
	
	@Column(name="resolver_id")
	private int resolverId;
	
	
	private Status status= Status.Pending;
	
	
	private ReimbType type;
	
	
//	 This is for the photo but if it actually works I'll be amazed
//	@Lob
//    @Column(name = "reciept_img", columnDefinition="BLOB")
//    private byte[] reciept_img;
	
	
	//CONSTRUCTORS//////////
	
	public Reimbursement(int id, double amount, Date date_submitted, Date date_resolved, String description,
			int authorId, int resolverId, Status status, ReimbType type, byte[] reciept_img) {
		super();
		this.id = id;
		this.amount = amount;
		this.dateSubmitted = date_submitted;
		
		this.description = description;
		this.authorId = authorId;
		this.resolverId = resolverId;
		this.status = status;
		this.type = type;
		
	}
	
	// update status
	public Reimbursement( int id , double amount, String description, int authorId,
			int resolverId, Status status ,ReimbType type) {
		super();
		this.id = id;
		this.amount = amount;
		this.description = description;
		this.authorId = authorId;
		this.resolverId = resolverId;
		this.status = status;
		this.type = type;
		
		
	}
	// creation constructor
	public Reimbursement(double amount, String description, int authorId,
			 ReimbType type) {
		super();
		this.amount = amount;
		
		this.dateResolved = null;
		this.description = description;
		this.authorId = authorId;
		this.status = Status.Pending;
		this.type = type;
		
		
	}
	public Reimbursement() {
		super();
	}
	
	//GETTERS AND SETTERS/////////////////////
	

	


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	
	public java.util.Date getDate_submitted() {
		return dateSubmitted;
	}
	public void setDate_submitted(java.util.Date date_submitted) {
		this.dateSubmitted = date_submitted;
	}
	public java.util.Date getDate_resolved() {
		return dateResolved;
	}
	public void setDate_resolved(java.util.Date date_resolved) {
		this.dateResolved = date_resolved;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getAuthorId() {
		return authorId;
	}
	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}
	public int getResolverId() {
		return resolverId;
	}
	public void setResolverId(int resolverId) {
		this.resolverId = resolverId;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public ReimbType getType() {
		return type;
	}
	public void setType(ReimbType type) {
		this.type = type;
	}
	
	
	
	
	
	
	
	@Override
	public int hashCode() {
		return Objects.hash(amount, authorId, dateResolved, dateSubmitted, description, id, resolverId, status, type);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reimbursement other = (Reimbursement) obj;
		return Double.doubleToLongBits(amount) == Double.doubleToLongBits(other.amount) && authorId == other.authorId
				&& Objects.equals(dateResolved, other.dateResolved)
				&& Objects.equals(dateSubmitted, other.dateSubmitted) && Objects.equals(description, other.description)
				&& id == other.id && resolverId == other.resolverId && status == other.status && type == other.type;
	}
	@Override
	public String toString() {
		return "Reimbursement [id=" + id + ", amount=" + amount + ", dateSubmitted=" + dateSubmitted + ", dateResolved="
				+ dateResolved + ", description=" + description + ", authorId=" + authorId + ", resolverId="
				+ resolverId + ", status=" + status + ", type=" + type + "]";
	}
	
	
	
}
