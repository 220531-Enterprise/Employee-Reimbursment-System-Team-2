package com.revature.models;

import java.util.Objects;

import com.revature.enums.ReimbType;
import com.revature.enums.Status;

public class Reimbursement { //TODO set up Hibernate for this class

	private int id;
	
	private double amount;
	
	private String timestamp;
	
	private String resolved;
	
	private String description;
	
	private int authorId;
	
	private int resolverId;
	
	private Status status;
	
	private ReimbType type;
	
	public Reimbursement(int id, double amount, String timestamp, String resolved, String description, int authorId,
			int resolverId, Status status, ReimbType type) {
		super();
		this.id = id;
		this.amount = amount;
		this.timestamp = timestamp;
		this.resolved = resolved;
		this.description = description;
		this.authorId = authorId;
		this.resolverId = resolverId;
		this.status = status;
		this.type = type;
	}
	public Reimbursement(double amount, String timestamp, String resolved, String description, int authorId,
			int resolverId, Status status, ReimbType type) {
		super();
		this.amount = amount;
		this.timestamp = timestamp;
		this.resolved = resolved;
		this.description = description;
		this.authorId = authorId;
		this.resolverId = resolverId;
		this.status = status;
		this.type = type;
	}
	public Reimbursement() {
		super();
	}
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
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getResolved() {
		return resolved;
	}
	public void setResolved(String resolved) {
		this.resolved = resolved;
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
		return Objects.hash(amount, authorId, description, id, resolved, resolverId, status, timestamp, type);
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
				&& Objects.equals(description, other.description) && id == other.id
				&& Objects.equals(resolved, other.resolved) && resolverId == other.resolverId && status == other.status
				&& Objects.equals(timestamp, other.timestamp) && type == other.type;
	}
	@Override
	public String toString() {
		return "Reimbursement [id=" + id + ", amount=" + amount + ", timestamp=" + timestamp + ", resolved=" + resolved
				+ ", description=" + description + ", authorId=" + authorId + ", resolverId=" + resolverId + ", status="
				+ status + ", type=" + type + "]";
	}
	
	
	
}
