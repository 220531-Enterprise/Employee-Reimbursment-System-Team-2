package com.revature.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dao.EmployeeDao;
import com.revature.enums.ReimbType;
import com.revature.enums.Role;
import com.revature.enums.Status;
import com.revature.models.Employee;
import com.revature.models.Reimbursement;
import com.revature.service.EmployeeService;

public class RequestHelper {
	
	// employeeservice
	private static EmployeeService eserv = new EmployeeService(new EmployeeDao());
	// object mapper (for frontend)
	private static ObjectMapper om = new ObjectMapper();
	
	private static String htmlPage = "text/html";
	
	/**
	 * What does this method do?
	 * 
	 * It extracts the parameters from a request (username and password) from the UI
	 * It will call the confirmLogin() method from the EmployeeService and 
	 * see if a user with that username and password exists
	 * 
	 * Who will provide the method with the HttpRequest? The UI
	 * We need to build an html doc with a form that will send these prameters to the method
	 */
	public static void processRegistration(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		String email = request.getParameter("email");
		Employee e = new Employee(firstname,lastname,username,password, email, Role.Employee);

		
		int pk = eserv.register(e);
		
		if (pk > 0) {
			
			HttpSession session = request.getSession();
			session.setAttribute("the-user", e);
			request.getRequestDispatcher("welcome.html").forward(request, response);
		} else {
			
			PrintWriter out = response.getWriter();
			response.setContentType(htmlPage);
			out.println("<h1>Registration failed. User already exists</h1>");
			out.println("<a href=\"index.html\">Back</a>");
		}
		
		
		
	}
	public static void processLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		// 1. Extract the parameters from the request (username & password)
		String username = request.getParameter("username");
		String password = request.getParameter("password"); // use fn + arrow key < or > to get to the beginning or end of a line of code
		// use ctrl + arrow key to go from word to word
		
		// 2. call the confirm login(0 method from the employeeService and see what it returns
		Employee e = eserv.confirmLogin(username, password);
		PrintWriter out = response.getWriter();
		// 3. If the user exists, lets print their info to the screen
		if (e.getId() > 0) {
			
			// grab the session
			HttpSession session = request.getSession();
			
			// add the user to the session
			session.setAttribute("the-user", e);
			
			// alternatively you can redirect to another resource instead of printing out dynamically
			request.getRequestDispatcher("welcome.html").forward(request, response);
		} else {
			
			response.setContentType(htmlPage);
			out.println("No user found, sorry");
			out.println("<a href=\"index.html\">Back</a>");
			
			// Shout out to Gavin for figuring this out -- 204 doesn't return a response body
//			response.setStatus(204); // 204 meants successful connection to the server, but no content found
		}
		
	}
	public static void processEmployees(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException {
//		response.setContentType("application/json");
		response.setContentType(htmlPage);
		List<Employee> emps = eserv.getAll();
		String jsonstring = om.writeValueAsString(emps);
		PrintWriter out = response.getWriter();
		out.write(jsonstring);	
		
		
		
	}
	
	public static void processReimbursementRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		double amount = Double.parseDouble(request.getParameter("amount"));
		String reimbType = request.getParameter("type");
		String description = request.getParameter("description");
		
		ReimbType type = null;
		switch (reimbType) {
			case "Travel":
				type = ReimbType.Travel;
				break;
			case "Supplies":
				type = ReimbType.Supplies;
				break;
			case "Food":
				type = ReimbType.Food;
				break;
		}
		HttpSession session = request.getSession();
		Employee user = (Employee) session.getAttribute("the-user");
		
		
		
		Reimbursement reimb = new Reimbursement(amount, description, user.getId(), type);
		
		int pk = 1; //TODO make this call the rdao once merged
		
		PrintWriter out = response.getWriter();
		if (pk > 0) {
			response.setContentType(htmlPage);
			out.println("Submitted!");
			out.println("<a href=\"welcome.html\">Back</a>");
		} else {
			response.setContentType("text/html");
			out.println("Hmmm... something when wrong, request was not submitted");
			out.println("<a href=\"welcome.html\">Back</a>");
		}
		
	}
	public static void processUsersRequests(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		Employee user = (Employee) session.getAttribute("the-user");
		response.setContentType("application/json");
		response.addHeader("Access-Control-Allow-Origin", "*");
		//List<Reimbursement> reimbs = rserv.getByAuthorId(user.getId()); //TODO Need rserv and method
		//String jsonString = om.writeValueAsString(reimbs);
		PrintWriter out = response.getWriter();
		//out.write(jsonString);
	}
	
	
	

}
