package com.revature.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.revature.dao.EmployeeDao;
import com.revature.dao.ReimbursementDao;
import com.revature.enums.Status;
import com.revature.models.Employee;
import com.revature.models.Reimbursement;

public class ManagerService {

	private EmployeeDao mdao;
	private ReimbursementDao rdao;
	public ManagerService(EmployeeDao mdao) {

		this.mdao = mdao;
	

	}
	public ManagerService(ReimbursementDao rdao) {

	
		this.rdao = rdao;

	}
	
	public ManagerService(EmployeeDao mdao,ReimbursementDao rdao) {

		this.mdao = mdao;
		this.rdao = rdao;

	}



	public Employee confirmLogin(String username, String password) {

		// let's stream through all the employees that are returned
		Optional<Employee> possibleEmp = mdao.findAll().stream()
				.filter(e -> (e.getUsername().equals(username) && e.getPassword().equals(password))).findFirst();

		// IF the employee is present, return it, otherwise return empty Emp object
		// (with id of 0)
		return (possibleEmp.isPresent() ? possibleEmp.get() : new Employee());
		// ideally you should optimize this and set up a custom exception to be returned
	}

	public List<Employee> getAll() {

		return mdao.findAll();

	}

	public int register(Employee manager) {
		return mdao.insert(manager);
	}

	// Use Case: A Manager can approve/deny pending reimbursement requests
	// Use Case: An Employee receives an email when one of their reimbursement requests is resolved (optional)
	public boolean updateReimbursementStatus(int id, int resolverId,Status status, String email) {

		Reimbursement r = rdao.findReimbursementbyId(id);
		if (r == null)
			return false;
		r.setStatus(status);
		r.setResolverId(resolverId);
		if (!status.toString().equals("Pending")) {
			sendEmail(email);
		}
		
		
		return rdao.updateReimbursement(r);

	}

	// Use Case: A Manager can view all pending requests from all employees
	public List<Reimbursement> getAllEmployeesPendingReimbursementRequest() {

		List<Reimbursement> rts = rdao.findAll();
		rts = rts.stream().filter(rt -> rt.getStatus().toString().equals("Pending")).collect(Collectors.toList());
		
		List<Integer> emps = mdao.findAll().stream().filter(emp -> emp.getRole().toString().equals("Employee"))
				.map(emp -> emp.getId()).collect(Collectors.toList());
		List<Reimbursement> res = new ArrayList<Reimbursement>();
		for(Reimbursement rt: rts) {
			if(emps.contains(rt.getAuthorId())) {
				res.add(rt);
		}}
		return res;

	}

	// Use Case: A Manager can view images of the receipts from reimbursement
	// requests (extra credit)
	public boolean getImagesOfTheReceipts() {
		return false;
	}

	// Use Case: A Manager can view all resolved requests from all employees and see
	// which manager resolved it
	public List<Reimbursement> getAllEmployeeResolvedReimbursementRequest() {
		List<Reimbursement> rts = rdao.findAll();
		
		rts = rts.stream().filter(rt -> !rt.getStatus().toString().equals("Pending")).collect(Collectors.toList());
		
		List<Integer> emps = mdao.findAll().stream().filter(emp -> emp.getRole().toString().equals("Employee"))
				.map(emp -> emp.getId()).collect(Collectors.toList());
		List<Reimbursement> res = new ArrayList<Reimbursement>();
		for(Reimbursement rt: rts) {
			if(emps.contains(rt.getAuthorId())) {
				res.add(rt);
		}}
		return res;

	

	}

	// Use Case: A Manager can view all Employees
	public List<Employee> getAllEmployeesInfo() {

		List<Employee> emps = mdao.findAll();

		return emps.stream().filter(emp -> emp.getRole().toString().equals("Employee")).collect(Collectors.toList());
	}

	// Use Case: A Manager can view reimbursement requests from a single Employee
	public List<Reimbursement> getReimbursementRequestByEmployee(int id) {
		return rdao.findReimbursementbyAuthorId(id);

	}
	
	public boolean sendEmail(String receiver) {
		 // Recipient's email ID needs to be mentioned.
        String to = receiver;

        // Sender's email ID needs to be mentioned
        String from = "ahhaahhahaha233@gmail.com";

        // Assuming you are sending email from through gmails smtp
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication("ahhaahhahaha233@gmail.com", "xjigivladkawavqo");

            }

        });

        // Used to debug SMTP issues
        session.setDebug(true);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("Status of reimbursement requests");

            // Now set the actual message
            message.setText("Your reimbursement requests is resolved");

            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
            return true;
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
		return false;
	}

}
