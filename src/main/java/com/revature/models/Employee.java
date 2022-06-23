package com.revature.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.revature.enums.Role;

/**
 * This is a persistent class meaning we need to provide a no-args constructor
 * a PK, getters & setters, hashCode & equals
 */

@Entity
@Table(name="employees") // these annotations come from the JPA (that's the specification)
public class Employee {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="first_name")
	private String firstName;  
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(unique=true)
	private String username; // this column will just be "username" since we didn't provide a name attribute
	
	@Column(name="pwd")
	private String password;
	
	@Column(name="email")
	private String email;

	@Enumerated(EnumType.STRING)
	private Role role;
	
	@OneToMany(mappedBy="employeeHolder", fetch=FetchType.LAZY)
	List<Reimbursement> reImbList = new ArrayList<Reimbursement>();

	
	/**
	 * no args constructor, all args constructor, all args except ID constructor
	 * getters/setters
	 * hashCode & equals
	 * toString();
	 */
	
	public Employee() {
		super();
	}


	public Employee(int id, String firstName, String lastName, String username, String password, String email,
			Role role, List<Reimbursement> reImbList) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = role;
		this.reImbList = reImbList;
	}


	public Employee(String firstName, String lastName, String username, String password, String email, Role role) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = role;
	}


	public Employee(int id, String firstName, String lastName, String username, String password, String email,
			Role role) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = role;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public Role getRole() {
		return role;
	}


	public void setRole(Role role) {
		this.role = role;
	}

	public void setReImbList(List<Reimbursement> reImbList) {
		this.reImbList = reImbList;
	}
	
	public List<Reimbursement> getReImbList() {
		return reImbList;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", username=" + username
				+ ", password=" + password + ", email=" + email + ", role=" + role + "]";
	}


	@Override
	public int hashCode() {
		return Objects.hash(email, firstName, id, lastName, password, reImbList, role, username);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		return Objects.equals(email, other.email) && Objects.equals(firstName, other.firstName) && id == other.id
				&& Objects.equals(lastName, other.lastName) && Objects.equals(password, other.password)
				&& Objects.equals(reImbList, other.reImbList) && role == other.role
				&& Objects.equals(username, other.username);
	}

	

}
