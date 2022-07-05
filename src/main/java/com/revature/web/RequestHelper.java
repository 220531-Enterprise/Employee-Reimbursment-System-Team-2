package com.revature.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.revature.dao.EmployeeDao;
import com.revature.dao.ReimbursementDao;
import com.revature.enums.ReimbType;
import com.revature.enums.Role;
import com.revature.enums.Status;
import com.revature.models.Employee;
import com.revature.models.ReimbResponseJSON;
import com.revature.models.ReimbResponseJSON_Mngr;
import com.revature.models.Reimbursement;
import com.revature.service.EmployeeService;
import com.revature.service.ManagerService;
import com.revature.service.ReimbursementService;

public class RequestHelper {

	// employeeservice
	private static EmployeeService eserv = new EmployeeService(new EmployeeDao());
	private static ReimbursementService rserv = new ReimbursementService(new ReimbursementDao());
	private static ManagerService mserv = new ManagerService(new EmployeeDao(), new ReimbursementDao());
	// object mapper (for frontend)
	private static ObjectMapper om = new ObjectMapper();

	// reuserable strings
	private static String htmlPage = "text/html";
	private static String currentUser = "the-user";
	private static String notApplicable = "N/A";

	private static SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");

	private static void setResponseJSON(HttpServletResponse response) {
		response.setContentType("application/json");
		response.addHeader("Access-Control-Allow-Origin", "*");
	}

	/**
	 * What does this method do?
	 * 
	 * It extracts the parameters from a request (username and password) from the UI
	 * It will call the confirmLogin() method from the EmployeeService and see if a
	 * user with that username and password exists
	 * 
	 * Who will provide the method with the HttpRequest? The UI We need to build an
	 * html doc with a form that will send these prameters to the method
	 */
	public static void processRegistration(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		String email = request.getParameter("email");
		Employee e = new Employee(firstname, lastname, username, password, email, Role.Employee);

		int pk = eserv.register(e);

		if (pk > 0) {

			HttpSession session = request.getSession();
			session.setAttribute(currentUser, e);
			request.getRequestDispatcher("welcome.html").forward(request, response);
		} else {

			PrintWriter out = response.getWriter();
			response.setContentType(htmlPage);
			out.println("<h1>Registration failed. User already exists</h1>");
			out.println("<a href=\"index.html\">Back</a>");
		}

	}

	public static void processLogin(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		// 1. Extract the parameters from the request (username & password)
		String username = request.getParameter("username");
		String password = request.getParameter("password"); // use fn + arrow key < or > to get to the beginning or end
															// of a line of code
		// use ctrl + arrow key to go from word to word

		// 2. call the confirm login(0 method from the employeeService and see what it
		// returns
		Employee e = eserv.confirmLogin(username, password);
		PrintWriter out = response.getWriter();
		// 3. If the user exists, lets print their info to the screen
		if (e.getId() > 0) {

			// grab the session
			HttpSession session = request.getSession();

			// add the user to the session
			session.setAttribute(currentUser, e);

			// alternatively you can redirect to another resource instead of printing out
			// dynamically
			if (e.getRole() == Role.Employee) {
				request.getRequestDispatcher("welcome.html").forward(request, response);
			} else if (e.getRole() == Role.Manager) {
				request.getRequestDispatcher("manager-home.html").forward(request, response);
			}
		} else {

			response.setContentType(htmlPage);
			out.println("No user found, sorry");
			out.println("<a href=\"index.html\">Back</a>");

			// Shout out to Gavin for figuring this out -- 204 doesn't return a response
			// body
//			response.setStatus(204); // 204 meants successful connection to the server, but no content found
		}

	}

	public static void processEmployees(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
//		response.setContentType("application/json");
		response.setContentType(htmlPage);
		List<Employee> emps = eserv.getAll();
		String jsonstring = om.writeValueAsString(emps);
		PrintWriter out = response.getWriter();
		out.write(jsonstring);

	}

	public static void processReimbursementRequest(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		setResponseJSON(response);

		System.out.println("in the processReimbursementRequest method within request helper");

		/**
		 * We're using GSON here because it's easier to use for parsing a payload.
		 */
		Gson gson = new Gson();
		gson = new GsonBuilder().create();

		JsonParser jsonParser = new JsonParser();
		// parse the payload of the HTTP request
		JsonElement root = jsonParser.parse(new InputStreamReader((InputStream) request.getInputStream()));
		// Transform payload string to json object
		JsonObject rootobj = root.getAsJsonObject();

		System.out.println(rootobj);

		// extract properties of JSON object
		double amount = rootobj.get("amount").getAsDouble();
		String typeString = rootobj.get("type").getAsString();
		String description = rootobj.get("description").getAsString();
		ReimbType type = null;
		System.out.println(typeString);

		HttpSession session = request.getSession();
		Employee user = (Employee) session.getAttribute(currentUser);
		int authorId = user.getId();
		if (typeString.equals("Food")) {
			type = ReimbType.Food;
		} else if (typeString.equals("Travel")) {
			type = ReimbType.Travel;
		} else if (typeString.equals("Supplies")) {
			type = ReimbType.Supplies;
		}

		Reimbursement newRequest = new Reimbursement(amount, description, authorId, type);

		// persist the new request
		int pk = rserv.insert(newRequest);
		PrintWriter pw = response.getWriter();

		if (pk > 0) {
			// return the request movie object to the client
			String json = gson.toJson(newRequest); // this is what we would use Jackson Object Mapper for
			pw.write(json);
		} else {
			String json = gson.toJson(new Reimbursement());
		}

	}

	public static void findEmployeesReimbursements(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// get current user
		HttpSession session = request.getSession();
		Employee user = (Employee) session.getAttribute(currentUser);

		setResponseJSON(response);

		List<Reimbursement> hirbernateReimbs = rserv.getbyAuthorId(user.getId());
		List<Employee> employees = eserv.getAll();

		List<ReimbResponseJSON> reimbs = new LinkedList<ReimbResponseJSON>();

		for (Reimbursement r : hirbernateReimbs) {

			ReimbResponseJSON tempReimb = new ReimbResponseJSON();
			tempReimb.setId(r.getId());
			tempReimb.setAmount(r.getAmount());
			if (r.getDate_submitted() != null) {
				tempReimb.setDateSubmitted(dateFormatter.format(r.getDate_submitted()));
			} else {
				tempReimb.setDateSubmitted(notApplicable);
			}
			if (r.getDate_resolved() != null) {
				tempReimb.setDateResolved(dateFormatter.format(r.getDate_resolved()));
			} else {
				tempReimb.setDateResolved(notApplicable);
			}
			tempReimb.setDescription(r.getDescription());
			tempReimb.setAuthorUsername(employees.stream().filter(e -> e.getId() == r.getAuthorId())
					.map(Employee::getUsername).collect(Collectors.toList()).get(0));
			if (r.getResolverId() > 0) {
				tempReimb.setResolverUsername(employees.stream().filter(e -> e.getId() == r.getResolverId())
						.map(Employee::getUsername).collect(Collectors.toList()).get(0));
			} else {
				tempReimb.setResolverUsername(notApplicable);
			}
			tempReimb.setStatus(r.getStatus());
			tempReimb.setType(r.getType());

			reimbs.add(tempReimb);

		}
		System.out.println(reimbs.get(0));
		String jsonString = om.writeValueAsString(reimbs);
		PrintWriter out = response.getWriter();
		out.write(jsonString);
	}

	public static void getUserFromSession(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Employee user = (Employee) session.getAttribute(currentUser);
		String jsonString = om.writeValueAsString(user);
		PrintWriter out = response.getWriter();
		out.write(jsonString);
	}

	public static void updateUserInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		setResponseJSON(response);

		System.out.println("in the updateUserInfo method within request helper");

		JsonParser jsonParser = new JsonParser();
		// parse the payload of the HTTP request
		JsonElement root = jsonParser.parse(new InputStreamReader(request.getInputStream()));
		// Transform payload string to json object
		JsonObject rootobj = root.getAsJsonObject();

		System.out.println(rootobj);

		// extract properties of JSON object
		String username = rootobj.get("username").getAsString();
		String firstName = rootobj.get("firstName").getAsString();
		String lastName = rootobj.get("lastName").getAsString();
		String password = rootobj.get("password").getAsString();
		String email = rootobj.get("email").getAsString();

		// get the session employee
		HttpSession session = request.getSession();
		Employee user = (Employee) session.getAttribute(currentUser);

		// set values to update
		user.setUsername(username);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setPassword(password);
		user.setEmail(email);

		// persist the changes and set the updated employee as the session user
		eserv.updateInfo(user);
		session.setAttribute(currentUser, user);
	}

	public static void processLogout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		try {
			session.invalidate();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} finally {
			request.getRequestDispatcher("index.html").forward(request, response);
		}

	}

	public static void updateRembsStatus(HttpServletRequest request, HttpServletResponse response) throws IOException {
		setResponseJSON(response);

		System.out.println("in the updateRembsStatus method within request helper");

		JsonParser jsonParser = new JsonParser();
		// parse the payload of the HTTP request
		JsonElement root = jsonParser.parse(new InputStreamReader(request.getInputStream()));
		// Transform payload string to json object
		JsonObject rootobj = root.getAsJsonObject();

		System.out.println(rootobj);

		// extract properties of JSON object
		
		String decision_input = rootobj.get("decision").getAsString();
		int id = rootobj.get("id").getAsInt();
	
		String email = rootobj.get("email").getAsString();
		Status decision = null;
		if (decision_input.toString().equals("approve") ) {
			decision = Status.Approved;
		} else if (decision_input.toString().equals("deny")){
			decision = Status.Denied;
		}
	

		// get the session employee
		
		HttpSession session = request.getSession();
		Employee user = (Employee) session.getAttribute(currentUser);

		// set values to update
//		user.setUsername(username);
	
		System.out.println( user.getUsername()+ " " + user.getId());
		
		// persist the changes and set the updated employee as the session user
		mserv.updateReimbursementStatus(id, user.getId(),decision,email);
		session.setAttribute(currentUser, user);
	}
	

	public static void getEmployeesReimbursements(HttpServletRequest request, HttpServletResponse response,List<Reimbursement> reimbList)
			throws IOException, ServletException{
		// get current user
		HttpSession session = request.getSession();
		Employee user = (Employee) session.getAttribute(currentUser);
		
		setResponseJSON(response);
		
		List<Reimbursement> hirbernateReimbs = reimbList;
		List<Employee> employees = eserv.getAll();

		List<ReimbResponseJSON_Mngr> reimbs = new LinkedList<ReimbResponseJSON_Mngr>();
		
		for (Reimbursement r : hirbernateReimbs) {

			ReimbResponseJSON_Mngr tempReimb = new ReimbResponseJSON_Mngr();
			tempReimb.setId(r.getId());
			tempReimb.setAmount(r.getAmount());
			if (r.getDate_submitted() != null) {
				tempReimb.setDateSubmitted(dateFormatter.format(r.getDate_submitted()));
			} else {
				tempReimb.setDateSubmitted(notApplicable);
			}
			if (r.getDate_resolved() != null) {
				tempReimb.setDateResolved(dateFormatter.format(r.getDate_resolved()));
			} else {
				tempReimb.setDateResolved(notApplicable);
			}
			tempReimb.setDescription(r.getDescription());
			if(employees.stream().filter(e -> e.getId() == r.getAuthorId()).findFirst().isPresent()) {
				tempReimb.setAuthorUsername(employees.stream().filter(e -> e.getId() == r.getAuthorId())
						.map(Employee::getUsername).collect(Collectors.toList()).get(0));
				
			}
		
			if (r.getResolverId() > 0) {
				tempReimb.setResolverUsername(employees.stream().filter(e -> e.getId() == r.getResolverId())
						.map(Employee::getUsername).collect(Collectors.toList()).get(0));
			} else {
				tempReimb.setResolverUsername(notApplicable);
			}
			tempReimb.setStatus(r.getStatus());
			tempReimb.setType(r.getType());
			tempReimb.setAuthorId(r.getAuthorId());
			if(employees.stream().filter(e -> e.getId() == r.getAuthorId()).findFirst().isPresent()) {
			tempReimb.setEmail(employees.stream().filter(e -> e.getId() == r.getAuthorId())
					.map(Employee::getEmail).collect(Collectors.toList()).get(0));
			}
			reimbs.add(tempReimb);

		}
		session.setAttribute(currentUser, user);
		System.out.println(reimbs.get(0));
		String jsonString = om.writeValueAsString(reimbs);
		PrintWriter out = response.getWriter();
		out.write(jsonString);
		
		
	}
	public static void findEmployeesReimbursementsMngr(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		getEmployeesReimbursements( request,  response,rserv.getAll());
//		getEmployeesReimbursements( request,  response,mserv.getAllEmployeesPendingReimbursementRequest());	
		
		
	}
	public static void findEmployeesPendingReimbursementsMngr(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
			getEmployeesReimbursements( request,  response,mserv.getAllEmployeesPendingReimbursementRequest());
		
	}
	public static void findEmployeesResolvedReimbursementsMngr(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		getEmployeesReimbursements( request,  response,mserv.getAllEmployeeResolvedReimbursementRequest());
		
	}
	public static void getAllEmp(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException{
		// get current user
		HttpSession session = request.getSession();
		Employee user = (Employee) session.getAttribute(currentUser);
		
		setResponseJSON(response);
		
		
		List<Employee> employees = eserv.getAll();

		
		
		
		session.setAttribute(currentUser, user);
		
		String jsonString = om.writeValueAsString(employees);
		PrintWriter out = response.getWriter();
		out.write(jsonString);
		
		
	}

}