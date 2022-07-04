package com.revature.models;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.revature.enums.ReimbType;
import com.revature.enums.Status;

/**
 * This is a Dummy model to reformat Reimbersement objects to be what we need to display dates and names correctly on the HTML tables we are using.
 * @author Josh
 *
 */

public class ReimbResponseJSON {
	
private int id;
	
	
	private double amount;

	private String dateSubmitted;

    private String dateResolved;

	private String description;

	private String authorUsername;
	
	private String resolverUsername;
	
	private Status status= Status.Pending;
	
	private ReimbType type;

	public ReimbResponseJSON() {
		super();
	}

	public ReimbResponseJSON(int id, double amount, String dateSubmitted, String dateResolved, String description,
			String authorUsername, String resolverUsername, Status status, ReimbType type) {
		super();
		this.id = id;
		this.amount = amount;
		this.dateSubmitted = dateSubmitted;
		this.dateResolved = dateResolved;
		this.description = description;
		this.authorUsername = authorUsername;
		this.resolverUsername = resolverUsername;
		this.status = status;
		this.type = type;
	}

	public ReimbResponseJSON(double amount, String dateSubmitted, String dateResolved, String description,
			String authorUsername, String resolverUsername, Status status, ReimbType type) {
		super();
		this.amount = amount;
		this.dateSubmitted = dateSubmitted;
		this.dateResolved = dateResolved;
		this.description = description;
		this.authorUsername = authorUsername;
		this.resolverUsername = resolverUsername;
		this.status = status;
		this.type = type;
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

	public String getDateSubmitted() {
		return dateSubmitted;
	}

	public void setDateSubmitted(String dateSubmitted) {
		this.dateSubmitted = dateSubmitted;
	}

	public String getDateResolved() {
		return dateResolved;
	}

	public void setDateResolved(String dateResolved) {
		this.dateResolved = dateResolved;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAuthorUsername() {
		return authorUsername;
	}

	public void setAuthorUsername(String authorUsername) {
		this.authorUsername = authorUsername;
	}

	public String getResolverUsername() {
		return resolverUsername;
	}

	public void setResolverUsername(String resolverUsername) {
		this.resolverUsername = resolverUsername;
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
		return Objects.hash(amount, authorUsername, dateResolved, dateSubmitted, description, id, resolverUsername,
				status, type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReimbResponseJSON other = (ReimbResponseJSON) obj;
		return Double.doubleToLongBits(amount) == Double.doubleToLongBits(other.amount)
				&& Objects.equals(authorUsername, other.authorUsername)
				&& Objects.equals(dateResolved, other.dateResolved)
				&& Objects.equals(dateSubmitted, other.dateSubmitted) && Objects.equals(description, other.description)
				&& id == other.id && Objects.equals(resolverUsername, other.resolverUsername) && status == other.status
				&& type == other.type;
	}

	@Override
	public String toString() {
		return "ReimbResponseJSON [id=" + id + ", amount=" + amount + ", dateSubmitted=" + dateSubmitted
				+ ", dateResolved=" + dateResolved + ", description=" + description + ", authorUsername="
				+ authorUsername + ", resolverUsername=" + resolverUsername + ", status=" + status + ", type=" + type
				+ "]";
	}
	
	

}