package com.revature.models;

import java.util.Objects;

import com.revature.enums.ReimbType;
import com.revature.enums.Status;

public class ReimbResponseJSON_Mngr {
private int id;
	
	
	private double amount;

	private String dateSubmitted;

    private String dateResolved;

	private String description;

	private String authorUsername;
	private int authorId;
	


	private String resolverUsername;
	
	private String email;
	
	private Status status= Status.Pending;
	
	private ReimbType type;

	public ReimbResponseJSON_Mngr() {
		super();
	}

	public ReimbResponseJSON_Mngr(int id, double amount, String dateSubmitted, String dateResolved, String description,
			String authorUsername, int authorId, String resolverUsername, String email, Status status, ReimbType type) {
		super();
		this.id = id;
		this.amount = amount;
		this.dateSubmitted = dateSubmitted;
		this.dateResolved = dateResolved;
		this.description = description;
		this.authorUsername = authorUsername;
		this.authorId = authorId;
		this.resolverUsername = resolverUsername;
		this.email = email;
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

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	public String getResolverUsername() {
		return resolverUsername;
	}

	public void setResolverUsername(String resolverUsername) {
		this.resolverUsername = resolverUsername;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
		return Objects.hash(amount, authorId, authorUsername, dateResolved, dateSubmitted, description, email, id,
				resolverUsername, status, type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReimbResponseJSON_Mngr other = (ReimbResponseJSON_Mngr) obj;
		return Double.doubleToLongBits(amount) == Double.doubleToLongBits(other.amount) && authorId == other.authorId
				&& Objects.equals(authorUsername, other.authorUsername)
				&& Objects.equals(dateResolved, other.dateResolved)
				&& Objects.equals(dateSubmitted, other.dateSubmitted) && Objects.equals(description, other.description)
				&& Objects.equals(email, other.email) && id == other.id
				&& Objects.equals(resolverUsername, other.resolverUsername) && status == other.status
				&& type == other.type;
	}

	@Override
	public String toString() {
		return "ReimbResponseJSON_Mngr [id=" + id + ", amount=" + amount + ", dateSubmitted=" + dateSubmitted
				+ ", dateResolved=" + dateResolved + ", description=" + description + ", authorUsername="
				+ authorUsername + ", authorId=" + authorId + ", resolverUsername=" + resolverUsername + ", email="
				+ email + ", status=" + status + ", type=" + type + "]";
	}
	
}
